package edu.northeastern.cs5520groupproject.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MovieRequest {

    public static List<Map<String, String>> searchMovieTitle(String title) {
        List<Map<String, String>> res = new ArrayList<>();
        try {
            // URL object to search for the movie
            URL url = new URL("https://www.omdbapi.com/?apikey=e03c49db&s=" + title);
            // HTTP connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Read the response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            // Read until input line is null
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            in.close();
            con.disconnect();

            // Parse the response as JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(stringBuilder.toString());
            // The json object is nested, we create a map for each of the movies returned
            jsonNode.get("Search").elements().forEachRemaining(e -> {
                Map<String, String> newMap = new HashMap<>();
                e.fieldNames().forEachRemaining(name -> newMap.put(name, String.valueOf(e.get(name))));
                res.add(newMap);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    // testing if I've got the correct values
    public static void main(String[] args) {
        List<Map<String, String>> temp = searchMovieTitle("harry potter");
        temp.forEach(m -> {
            for (String k: m.keySet()) {
                System.out.println(k + ": " + m.get(k));
            }
            System.out.println();
        });
    }
}
