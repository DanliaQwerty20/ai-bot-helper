package by.system.aibothelper.client.gpt;

import by.system.aibothelper.dto.LegendReviewResult;

public interface LegendLlmClient {

    String review(String text, String context);

    LegendReviewResult reviewAndParse(String text);

    LegendReviewResult parse(String raw);
}
