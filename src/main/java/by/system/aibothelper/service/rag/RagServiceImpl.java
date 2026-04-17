package by.system.aibothelper.service.rag;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class RagServiceImpl implements RagService {

    VectorStore vectorStore;

    @Override
    public String getContext(String text) {

        List<Document> docs = this.vectorStore.similaritySearch(text);

        return docs.stream()
                .limit(3)
                .map(Document::getText)
                .collect(Collectors.joining("\n---\n"));
    }
}
