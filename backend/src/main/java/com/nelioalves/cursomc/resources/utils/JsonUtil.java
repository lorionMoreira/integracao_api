package com.nelioalves.cursomc.resources.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String extractFieldFromJson(String json, String fieldName) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode fieldNode = jsonNode.get(fieldName);
            return fieldNode != null ? fieldNode.asText() : "Field not found";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing JSON";
        }
    }

    public static String extractFieldFromJson1(String json) {

        try {
            String fieldPath="main.temp";

            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode targetNode = rootNode;
            for (String part : fieldPath.split("\\.")) {
                targetNode = targetNode.get(part);
                if (targetNode == null) {
                    return "Field not found";
                }
            }
            return targetNode.isTextual() ? targetNode.asText() : targetNode.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing JSON";
        }
    }

    public static String extractFieldFromJson2(String json) {
     
        try {
            String fieldPath="current.temp_c";

            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode targetNode = rootNode;
            for (String part : fieldPath.split("\\.")) {
                targetNode = targetNode.get(part);
                if (targetNode == null) {
                    return "Field not found";
                }
            }
            return targetNode.isTextual() ? targetNode.asText() : targetNode.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing JSON";
        }
    }
}
