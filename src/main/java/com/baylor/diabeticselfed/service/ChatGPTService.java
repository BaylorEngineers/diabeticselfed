//package com.baylor.diabeticselfed.service;
//
//import com.baylor.diabeticselfed.dto.ChatGPTRequest;
//import com.baylor.diabeticselfed.dto.OpenAIRequest;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Service
//public class ChatGPTService {
//
//    private final WebClient webClient;
//
//    @Value("${openai.api.url}")
//    private String chatGPTApiUrl;
//
//    @Value("${openai.api.key}")
//    private String openaiApiKey;
//
//    public ChatGPTService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl(chatGPTApiUrl).build();
//    }
//
//    public Mono<String> getChatGPTResponse(ChatGPTRequest request) {
//
//        System.out.println(request.getName() + " " + request.getBackground() + " " + request.getOccupation());
//        System.out.println(request.getInterests() + " " + request.getAge());
//
//        String prompt = buildPrompt(request);
//        System.out.println(prompt);
//        OpenAIRequest openAIRequest = new OpenAIRequest("gpt-3.5-turbo", prompt, 150);
//
//        String curlCommand = buildCurlCommand(openAIRequest);
//
//        System.out.println(curlCommand);
//
//        return webClient.post()
//                .uri("/v1/engines/davinci-codex/completions")
//                .contentType(MediaType.APPLICATION_JSON)
//                .header("Authorization", "Bearer " + openaiApiKey)
//                .bodyValue(openAIRequest)
//                .retrieve()
//                .bodyToMono(String.class);
//    }
//
//    private String buildPrompt(ChatGPTRequest request) {
//        return String.format(
//                "Here is a profile:\n- Name: %s\n- Age: %d\n- Education: %s\n- Occupation: %s\n- Interests: %s\n- Background: %s\n- Goal: %s\n\n"
//                        + "Based on this profile, provide a motivational message as the user did not adhere to a healthy diet in the past few days.",
//                request.getName(),
//                request.getAge(),
//                request.getEducation(),
//                request.getOccupation(),
//                request.getInterests(),
//                request.getBackground(),
//                request.getGoal()
//        );
//    }
//
//    private String buildCurlCommand(OpenAIRequest openAIRequest) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonBody = objectMapper.writeValueAsString(openAIRequest);
//            String escapedJsonBody = jsonBody.replace("\"", "\\\"");
//
//            return "curl -X POST " +
//                    chatGPTApiUrl + "/v1/engines/davinci-codex/completions " +
//                    "-H \"Content-Type: application/json\" " +
//                    "-H \"Authorization: Bearer " + openaiApiKey + "\" " +
//                    "-d \"" + escapedJsonBody + "\"";
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return "Error building cURL command";
//        }
//    }
//}