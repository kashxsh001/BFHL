package com.chitkara.bfhl.controller;


import com.chitkara.bfhl.model.ResponseDTO;
import com.chitkara.bfhl.service.BFHLService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
public class BFHLController {
    private final BFHLService service;

    @Value("${official.email}")
    private String email;

    public BFHLController(BFHLService service) {
        this.service = service;
    }

    @GetMapping("/health")
    public ResponseEntity<ResponseDTO> health() {
        return ResponseEntity.ok(
                new ResponseDTO(true, email, null, null)
        );
    }

    @PostMapping("/bfhl")
    public ResponseEntity<ResponseDTO> process(@RequestBody Map<String, Object> body) {

        try {

            if (body.size() != 1)
                throw new RuntimeException("Exactly one key required");

            String key = body.keySet().iterator().next();
            Object value = body.get(key);

            Object result;

            switch (key) {
                case "fibonacci":
                    result = service.fibonacci((Integer) value);
                    break;

                case "prime":
                    result = service.filterPrimes((List<Integer>) value);
                    break;

                case "lcm":
                    result = service.lcm((List<Integer>) value);
                    break;

                case "hcf":
                    result = service.hcf((List<Integer>) value);
                    break;

                case "AI":
                    result = service.getAIResponse((String) value);
                    break;

                default:
                    throw new RuntimeException("Invalid key");
            }

            return ResponseEntity.ok(
                    new ResponseDTO(true, email, result, null)
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ResponseDTO(false, email, null, e.getMessage())
            );
        }
    }
}
