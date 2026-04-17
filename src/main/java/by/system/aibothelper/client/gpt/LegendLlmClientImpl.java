package by.system.aibothelper.client.gpt;

import by.system.aibothelper.config.properties.PromptProperties;
import by.system.aibothelper.dto.LegendResponseDto;
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
    public LegendResponseDto parse(String raw) {

        try {
            var cleaned = this.clean(raw);

            var json = this.objectMapper.readTree(cleaned);

            var problems = JsonUtil.toList(json.get("problems"));
            var improvements = JsonUtil.toList(json.get("improvements"));
            var rewritten = json.get("rewritten").asText();

            return new LegendResponseDto(problems, improvements, rewritten);

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
