package by.system.aibothelper.client.gpt;

import by.system.aibothelper.dto.LegendResponseDto;

public interface LegendLlmClient {

    String review(String text, String context);

    LegendResponseDto parse(String raw);
}
