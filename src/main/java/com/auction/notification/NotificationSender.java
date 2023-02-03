package com.auction.notification;

import org.springframework.stereotype.Component;

import com.auction.model.auction.Auction;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import java.util.*;

@Component
public class NotificationSender {
    RestTemplate restTemplate = new RestTemplate();
    private static String URL = "https://onesignal.com/api/v1/notifications";
    private static String API_KEY = "9db88a0a-5927-4f81-817d-05d55d0d8a37";
    private static String REST_KEY = "NmIzMjg5YjUtYmY4OS00NWUzLTg4NjUtNGVlZTI0MTU1NzA0";

    private String getInformation(Auction auction) {
        return "Auction n" + auction.getId() + " about "+ auction.getProductName()+" is ending";
    }

    public void push(Auction auction) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic "+REST_KEY);
        headers.set("accept", "application/json");
        headers.set("content-type", "application/json");

        Map<String, Object> data = new HashMap<>();
        data.put("app_id", API_KEY);
        data.put("included_segments", Arrays.asList("Subscribed Users"));

        Map<String, String> headings = new HashMap<>();
        headings.put("en", "Auction is ending");

        Map<String, String> contents = new HashMap<>();
        contents.put("en", getInformation(auction));

        data.put("contents", contents);
        data.put("headings", headings);
        data.put("name", "INTERNAL_CAMPAIGN_NAME");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(data, headers);

        ResponseEntity<Object> response = restTemplate.postForEntity(
                URL, request, Object.class);
    }
}
