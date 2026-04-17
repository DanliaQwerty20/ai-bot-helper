package by.system.aibothelper.dto;

import java.util.List;

public record LegendResponseDto(
        List<String> problems,
        List<String> improvements,
        String rewritten
) {}
