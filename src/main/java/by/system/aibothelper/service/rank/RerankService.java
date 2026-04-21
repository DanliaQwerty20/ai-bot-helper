package by.system.aibothelper.service.rank;

import org.springframework.ai.document.Document;

import java.util.List;

public interface RerankService {
    List<Document> filterRelevant(String rewritten, List<Document> similarDocs);
}
