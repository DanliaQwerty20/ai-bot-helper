package by.system.aibothelper.service.knowledge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final VectorStore vectorStore;

    private static final int CHUNK_SIZE = 1000; // символов
    private static final int OVERLAP = 200;     // перекрытие

    @Override
    public int processPdf(byte[] fileContent, String filename) {
        try (InputStream inputStream = new ByteArrayInputStream(fileContent);
             PDDocument document = PDDocument.load(inputStream)) {

            var stripper = new PDFTextStripper();
            var fullText = stripper.getText(document);

            var chunks = splitIntoChunks(fullText);
            var documents = new ArrayList<Document>();

            for (int i = 0; i < chunks.size(); i++) {
                var doc = new Document(chunks.get(i));
                doc.getMetadata().put("source", filename);
                doc.getMetadata().put("chunk", i);
                doc.getMetadata().put("totalChunks", chunks.size());
                doc.getMetadata().put("type", "golden_knowledge");
                documents.add(doc);
            }

            vectorStore.add(documents);
            log.info("Processed {} chunks from {}", chunks.size(), filename);

            return chunks.size();

        } catch (Exception e) {
            log.error("Failed to process PDF", e);
            throw new RuntimeException("PDF processing failed: " + e.getMessage());
        }
    }

    private List<String> splitIntoChunks(String text) {
        var chunks = new ArrayList<String>();
        var length = text.length();

        for (int i = 0; i < length; i += CHUNK_SIZE - OVERLAP) {
            int end = Math.min(i + CHUNK_SIZE, length);
            var chunk = text.substring(i, end);
            chunks.add(chunk);

            if (end == length) break;
        }

        return chunks;
    }
}