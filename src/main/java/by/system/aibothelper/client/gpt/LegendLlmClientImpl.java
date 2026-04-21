package by.system.aibothelper.client.gpt;

import by.system.aibothelper.config.properties.PromptProperties;
import by.system.aibothelper.dto.LegendReviewResult;
import by.system.aibothelper.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
@Slf4j
public class LegendLlmClientImpl implements LegendLlmClient {

    OpenAiChatModel model;
    ObjectMapper objectMapper;
    PromptProperties promptProperties;

    @Override
    public String review(String text, String context) {

        var systemPrompt = this.promptProperties.getLegendReview();

        var userPrompt = """
                TEXT:
                %s
                
                RAG CONTEXT:
                %s
                """.formatted(text, context);

        var response = this.model.call(
                new SystemMessage(systemPrompt),
                new UserMessage(userPrompt)
        );

        log.info("LLM raw response: {}", response);

        return response;
    }

    @Override
    public LegendReviewResult reviewAndParse(String text) {
        var raw = review(text, "");
        return parse(raw);
    }
    @Override
    public LegendReviewResult parse(String raw) {
        try {
            var cleaned = clean(raw);

            return objectMapper.readValue(cleaned, LegendReviewResult.class);

        } catch (Exception e) {
            log.error("Failed to parse JSON: {}", raw);

            return new LegendReviewResult(
                    List.of("Failed to parse LLM response"),
                    List.of("Check logs for details"),
                    "Error processing legend",
                    0,
                    false,
                    "Parsing error: " + e.getMessage()
            );
        }
    }

    private String clean(String response) {
        String cleaned = response.trim();

        cleaned = cleaned.replaceAll("```json\\s*", "")
                .replaceAll("```\\s*", "");

        int start = cleaned.indexOf('{');
        int end = cleaned.lastIndexOf('}');

        if (start == -1 || end == -1) {
            throw new RuntimeException("No JSON found in: " + response);
        }

        String json = cleaned.substring(start, end + 1);

        try {
            objectMapper.readTree(json);
            return json;
        } catch (Exception e) {
            return fixIncompleteJson(json);
        }
    }

    private String fixIncompleteJson(String json) {
        if (!json.trim().endsWith("}")) {
            if (json.contains("seniorComment") && !json.contains("\"}}")) {
                json = json + "\"}";
            } else {
                json = json + "}";
            }
        }
        return json;
    }

    private boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String fixTruncatedJson(String json) {
        if (json.contains("seniorComment") && !json.endsWith("\"")) {
            json = json + "\"";
        }

        int openBraces = json.length() - json.replace("{", "").length();
        int closeBraces = json.length() - json.replace("}", "").length();

        if (openBraces > closeBraces) {
            json = json + "}".repeat(openBraces - closeBraces);
        }

        return json;
    }
}
