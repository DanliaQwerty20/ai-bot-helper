package by.system.aibothelper.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LegendReviewResult(
        @JsonSetter(nulls = Nulls.AS_EMPTY)
        List<String> problems,

        @JsonSetter(nulls = Nulls.AS_EMPTY)
        List<String> improvements,

        String rewritten,

        int score,

        boolean isStrong,

        String seniorComment
) {
    public LegendReviewResult {
        if (problems == null) problems = List.of();
        if (improvements == null) improvements = List.of();
        if (rewritten == null) rewritten = "";
        if (seniorComment == null) seniorComment = "";
    }
}