package by.system.aibothelper.service.review;

import by.system.aibothelper.client.gpt.LegendLlmClient;
import by.system.aibothelper.dto.LegendResponseDto;
import by.system.aibothelper.service.rag.RagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class LegendServiceImpl implements LegendService {

    RagService ragService;
    LegendLlmClient llmClient;

    @Override
    public LegendResponseDto review(String text) {
        var context = this.ragService.getContext(text);
        var raw = this.llmClient.review(text, context);
        return this.llmClient.parse(raw);
    }
}
