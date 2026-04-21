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
            var json = objectMapper.readTree(cleaned);

            return new LegendReviewResult(
                    JsonUtil.toList(json.get("problems")),
                    JsonUtil.toList(json.get("improvements")),
                    json.get("rewritten").asText(),
                    json.get("score").asInt(),
                    json.get("isStrong").asBoolean(),
                    json.get("seniorComment").asText()
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse LLM response", e);
        }
    }

    private String clean(String response) {

        var cleaned = response.trim();

        if (cleaned.startsWith("```")) {
            cleaned = cleaned.replaceAll("```.*?\\n", "");
            cleaned = cleaned.replaceAll("```", "");
        }

        int start = cleaned.indexOf('{');
        int end = cleaned.lastIndexOf('}');

        return cleaned.substring(start, end + 1);
    }
}
