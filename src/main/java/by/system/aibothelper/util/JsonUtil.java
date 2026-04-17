package by.system.aibothelper.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class JsonUtil {

    public static List<String> toList(JsonNode node) {
        if (node == null || !node.isArray()) return List.of();

        var list = new ArrayList<String>();

        node.forEach(n -> list.add(n.asText()));

        return list;
    }
}
