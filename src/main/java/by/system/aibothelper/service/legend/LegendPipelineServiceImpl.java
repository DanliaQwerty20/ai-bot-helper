package by.system.aibothelper.service.legend;

import by.system.aibothelper.client.gpt.LegendLlmClient;
import by.system.aibothelper.dto.LegendReviewResult;
import by.system.aibothelper.entity.LegendEntity;
import by.system.aibothelper.repository.LegendRepository;
import by.system.aibothelper.service.rank.RerankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegendPipelineServiceImpl implements LegendPipelineService {

    private final LegendLlmClient llmClient;
    private final VectorStore vectorStore;
    private final LegendRepository repository;
    private final RerankService rerankService;

    public LegendReviewResult process(String text) {

        // 1. REVIEW
        var review = llmClient.reviewAndParse(text);

        log.info("Score: {}", review.score());

        if (review.score() < 6) {
            return review;
        }

        // 2. SECOND PASS
        var improved = llmClient.reviewAndParse(review.rewritten());

        if (improved.score() < 7) {
            return review;
        }

        // 3. DEDUPLICATION
        var similarDocs = vectorStore.similaritySearch(improved.rewritten());

        // 4. RERANK
        var filtered = rerankService.filterRelevant(
                improved.rewritten(),
                similarDocs
        );

        if (!filtered.isEmpty()) {
            log.info("Too similar to existing knowledge, skipping");
            return improved;
        }

        var entity = LegendEntity.builder()
                .originalText(text)
                .rewrittenText(improved.rewritten())
                .score(improved.score())
                .strong(improved.isStrong())
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(entity);

        vectorStore.add(List.of(
                new Document(improved.rewritten())
        ));

        return improved;
    }
}
