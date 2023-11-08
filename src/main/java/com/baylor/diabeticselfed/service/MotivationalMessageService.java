package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.entities.MotivationalMessage;
import com.baylor.diabeticselfed.entities.Patient;
import com.baylor.diabeticselfed.entities.Survey;
import com.baylor.diabeticselfed.repository.MotivationalMessageRepository;
import com.baylor.diabeticselfed.repository.SurveyRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotivationalMessageService {
    private final MotivationalMessageRepository motivationalMessageRepository;

    @Autowired
    private final SurveyRepository surveyRepository;

    public MotivationalMessage recordMotivationalMessage(Patient patient, Date dateT, String content) {
        MotivationalMessage m = new MotivationalMessage();
        m.setPatient(patient);
        m.setDateT(dateT);
        m.setMessage_content(content);

        return motivationalMessageRepository.save(m);
    }

    public List<MotivationalMessage> fetchMotivationalMessage(Patient patient) {
        return motivationalMessageRepository.findByPatient(patient);
    }

    public MotivationalMessage generateMotivationalMessage(Patient patient) {

        //check if answered no three times
        List<Survey> surveyList = surveyRepository.findByOrderByIdAsc();
        int CONSECUTIVE_RESPONSE = 1;

        List<Survey> lastThree = surveyList.subList(Math.max(surveyList.size() - CONSECUTIVE_RESPONSE, 0), surveyList.size());


        boolean getMotivationalMessage = true;
        MotivationalMessage message = new MotivationalMessage();

        System.out.println(lastThree.size());

// TODO: uncomment this to show generate motivational message if consicutive survey response is no three times
//        for (Survey response: lastThree) {
//            if (response.getAnswer()) {
//                getMotivationalMessage = false;
//            }
//        }

        if(getMotivationalMessage /*&& lastThree.size() == 3*/) {
            message.setPatient(patient);
            String prompt = "Give me a random motivational quote";
            String dummy = "";
            if (lastThree.get(0).getAnswer()) {
                dummy = "Congratulations on your remarkable commitment to a healthy diet! Your dedication and hard work are inspiring. Keep up the excellent work, and know that you're making a positive difference in your life.";
            } else {
                dummy = "I understand that maintaining a healthy diet can be challenging at times. Remember, setbacks are a natural part of any journey. Stay motivated, and know that you have the strength to get back on track. Your commitment to your well-being is truly admirable.";
            }


            message.setMessage_content(dummy);
            //getFromChatGPT(prompt)
        } else {
            message.setPatient(patient);
            message.setMessage_content(null);
        }
        return message;


    }

    public String getFromChatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }


}
