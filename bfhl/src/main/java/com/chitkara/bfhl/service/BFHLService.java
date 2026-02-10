package com.chitkara.bfhl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class BFHLService {
    @Value("${gemini.api.key}")
    private String apiKey;

    // Fibonacci
    public List<Integer> fibonacci(int n) {
        if (n < 0) throw new RuntimeException("Invalid fibonacci input");

        List<Integer> series = new ArrayList<>();
        if (n >= 1) series.add(0);
        if (n >= 2) series.add(1);

        for (int i = 2; i < n; i++) {
            series.add(series.get(i - 1) + series.get(i - 2));
        }
        return series;
    }

    // Prime
    public List<Integer> filterPrimes(List<Integer> numbers) {
        return numbers.stream().filter(this::isPrime).toList();
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0) return false;
        return true;
    }

    // GCD
    public int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // HCF
    public int hcf(List<Integer> numbers) {
        return numbers.stream().reduce(this::gcd).orElseThrow();
    }

    // LCM
    public int lcm(List<Integer> numbers) {
        return numbers.stream().reduce((a, b) -> (a * b) / gcd(a, b)).orElseThrow();
    }

    // AI Integration
    public String getAIResponse(String question) {

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", "Answer in one word only: " + question)
                        ))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        Map candidate = (Map) ((List) response.getBody().get("candidates")).get(0);
        Map content = (Map) candidate.get("content");
        List parts = (List) content.get("parts");
        Map textMap = (Map) parts.get(0);

        String answer = textMap.get("text").toString();

        return answer.split(" ")[0];
    }

}
