package ru.dst.analyze.realtime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.dst.analyze.realtime.response.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class JsonHelper {
    private static final Logger logger = LogManager.getLogger(JsonHelper.class);

    private static final String USER_CREDENTIALS = "dst_ural:dst_ural";
    private String url;
    private ObjectMapper mapper;

    public JsonHelper(String locarusNum, String fromDate, String toDate) {

        this.url = String.format(
//                "http://lserver3.ru:8091/do.locator?q=track&imei=%s&mode=full&filter=false&from=%sT00:00:00Z&to=%sT23:59:59Z",
                "http://lserver3.ru:8091/do.locator?q=track&imei=%s&mode=full&filter=false&from=%s&to=%s",
                locarusNum, fromDate, toDate);
//        System.out.println(url);

        mapper = new ObjectMapper();
    }

    private String basicAuth = "Basic " + new String(Base64.getEncoder().encode(USER_CREDENTIALS.getBytes()));


    public String getJson() {
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", basicAuth);

//            System.out.print("Getting connection... ");
//            System.out.print("_");
            logger.debug("Getting connection...");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            logger.debug("Connection established. ");
//            System.out.println(" done.");
//            System.out.print(".");

            String inputLine;
            StringBuilder response = new StringBuilder();

//            System.out.print("Collecting data... ");
//            System.out.print("_");
            logger.debug("Collecting data...");
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.debug("Data collected.");
//            System.out.println(" done.");
//            System.out.print(". ");
//            System.out.println(response.toString());

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Message getMessage() {
        String response = getJson();
        if (response != null && !response.equals("")) {
            try {
                return mapper.readValue(response, Message.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}