package by.system.aibothelper.dto;

import java.util.List;

public record LegendReviewResult(
        List<String> problems,
        List<String> improvements,
        String rewritten,
        int score,
        boolean isStrong,
        String seniorComment
) {}