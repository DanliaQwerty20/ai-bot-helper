package by.system.aibothelper.service.legend;

import by.system.aibothelper.dto.LegendReviewResult;

public interface LegendPipelineService {
    LegendReviewResult process(String text);
}
