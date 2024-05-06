package com.nelioalves.cursomc.resources.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static String extractFieldFromJson1(String json) {
        String fieldPath = "main.temp";
        try {
            return extractFieldFromJson(json, fieldPath);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing JSON"; // Consider how you want to handle errors more robustly.
        }
    }

    public static String extractFieldFromJson2(String json) {
        String fieldPath = "current.temp_c";
        try {
            return extractFieldFromJson(json, fieldPath);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing JSON"; // Consistent error handling.
        }
    }

    protected static String extractFieldFromJson(String json, String fieldPath) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode targetNode = rootNode;
        for (String part : fieldPath.split("\\.")) {
            targetNode = targetNode.get(part);
            if (targetNode == null) {
                return "Field not found";
            }
        }
        return targetNode.isTextual() ? targetNode.asText() : targetNode.toString();
    }
}
