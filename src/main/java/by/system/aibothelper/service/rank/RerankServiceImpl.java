package by.system.aibothelper.service.rank;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RerankServiceImpl implements RerankService {

    private final EmbeddingModel embeddingModel;

    public List<Document> filterRelevant(String query, List<Document> docs) {
        var queryEmbedding = embeddingModel.embed(prefixQuery(query));
        List<Document> result = new ArrayList<>();
        for (Document doc : docs) {
            var docEmbedding = embeddingModel.embed(prefixPassage(doc.getContent()));
            double similarity = cosine(queryEmbedding, docEmbedding);
            if (similarity > 0.85) {
                result.add(doc);
            }
        }

        return result;
    }

    private String prefixQuery(String text) {
        return "query: " + text;
    }

    private String prefixPassage(String text) {
        return "passage: " + text;
    }

    private double cosine(float[] a, float[] b) {
        double dot = 0;
        double normA = 0;
        double normB = 0;

        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }

        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
