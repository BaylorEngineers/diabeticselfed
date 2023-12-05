package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.dto.ChatGPTRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGPTService {

    private final WebClient webClient;

    @Value("${openai.api.url}")
    private String chatGPTApiUrl;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public ChatGPTService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(chatGPTApiUrl).build();
    }

//    public Mono<String> executeCurlAndReturnResponse(ChatGPTRequest request) {
//        return Mono.fromCallable(() -> {
//            String curlCommand = buildAndPrintCurlCommand(request);
//            StringBuilder responseBuilder = new StringBuilder();
//
//            try {
//                ProcessBuilder processBuilder = new ProcessBuilder(curlCommand.split(" "));
//                processBuilder.redirectErrorStream(true);
//
//                Process process = processBuilder.start();
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        responseBuilder.append(line).append("\n");
//                    }
//                }
//
//                int exitCode = process.waitFor();
//                if (exitCode != 0) {
//                    System.err.println("Curl command exited with code " + exitCode);
//                    return "Error executing cURL command";
//                }
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//                return "Error executing cURL command";
//            }
//
//            return parseContentFromResponse(responseBuilder.toString());
//        });
//    }

    public String executeCurlAndReturnResponseSync(ChatGPTRequest request) {
        String curlCommand = buildAndPrintCurlCommand(request);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(curlCommand.split(" "));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            StringBuilder responseBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Curl command exited with code " + exitCode);
            }

            return parseContentFromResponse(responseBuilder.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error executing cURL command";
        }
    }

    private String parseContentFromResponse(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Debug: Print the entire JSON response
            System.out.println("JSON Response: " + rootNode.toPrettyString());

            JsonNode choicesNode = rootNode.path("choices");
            if (!choicesNode.isEmpty() && choicesNode.isArray()) {
                JsonNode firstChoice = choicesNode.get(0);
                JsonNode messageNode = firstChoice.path("message");
                if (!messageNode.isMissingNode()) {
                    return messageNode.path("content").asText();
                } else {
                    return "Message node is missing";
                }
            } else {
                return "Choices node is missing or not an array";
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error parsing JSON response: " + e.getMessage();
        }
    }


//    private String buildPrompt(ChatGPTRequest request) {
//        return String.format(
//                "Here is a profile:\n- Name: %s\n- Age: %d\n- Education: %s\n- Occupation: %s\n- Interests: %s\n- Background: %s\n- Goal: %s\n\n"
//                        + "Based on this profile, provide a motivational message as the user did not adhere to a " +
//                        "healthy diet in the past few days. Don't write more than 30 words, be concise and always write " +
//                        "positive motivational message.",
//                request.getName(),
//                request.getAge(),
//                request.getEducation(),
//                request.getOccupation(),
//                request.getInterests(),
//                request.getBackground(),
//                request.getGoal()
//        );
//    }

    private String buildAndPrintCurlCommand(ChatGPTRequest request) {
//        String prompt = buildPrompt(request);

        String prompt = request.getPrompt();


        Map<String, Object> messageContent = new HashMap<>();
        messageContent.put("role", "user");
        messageContent.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", Collections.singletonList(messageContent));
        requestBody.put("max_tokens", 60);  // Limiting the output to approximately 30 words

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBodyJson = objectMapper.writeValueAsString(requestBody);
            String escapedJsonBody = requestBodyJson.replace("\"", "\\\"");

            String curlCommand = "curl -s -S -X POST " +
                    chatGPTApiUrl + "/v1/chat/completions " +
                    "-H \"Content-Type: application/json\" " +
                    "-H \"Authorization: Bearer " + openaiApiKey + "\" " +
                    "-d \"" + escapedJsonBody + "\"";

            System.out.println("cURL Command: " + curlCommand);
            return curlCommand;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error building cURL command";
        }
    }


}
