package com.sneo.bdd.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;

public class TestDataUtil {
    private static final ThreadLocal<JsonNode> testDataThreadLocal = new ThreadLocal<>();

    private TestDataUtil() {
        throw new IllegalStateException("TestDataUtil class cannot be instantiated.");
    }

    public static JsonNode getTestData(String tagName, String testDataFilePath) {
        String jsonFilePath = System.getProperty("user.dir") + "/src/testdata/" + testDataFilePath+".json";
        JsonNode testData = testDataThreadLocal.get();
        if (testData == null) {
            testData = loadTestData(jsonFilePath);
            testDataThreadLocal.set(testData);
        }
        return testData.get(tagName);
    }

    private static JsonNode loadTestData(String testDataFilePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(new File(testDataFilePath));
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }

    public static JsonNode getJsonNode(Scenario scenario){
        String tagValue = scenario.getSourceTagNames().stream()
                .filter(tag -> tag.startsWith("@SampleData"))
                .findFirst()
                .map(tag -> tag.split(":")[1])
                .orElse(null);
        String fileName = scenario.getSourceTagNames().stream()
                .filter(tag -> tag.startsWith("@SampleData"))
                .findFirst()
                .map(tag -> tag.split(":")[0])
                .orElse(null);
        if (fileName != null)
            return TestDataUtil.getTestData(tagValue, fileName.replace("@",""));
        else
            return null;
    }

    public static JsonNode getJsonNode(String body)  {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = null;
        try {
            actualObj = mapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return  actualObj;
    }

    public static void removeDataNode(){
        testDataThreadLocal.remove();
    }
}
