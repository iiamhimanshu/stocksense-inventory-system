package com.stocksense.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello from StockSense!";
    }

    @GetMapping("/api/status")
    public Map<String , String> status(){
        return Map.of(
                "app", "StockSense",
                "status", "running",
                "version", "0.1.0"
        );
    }



}
