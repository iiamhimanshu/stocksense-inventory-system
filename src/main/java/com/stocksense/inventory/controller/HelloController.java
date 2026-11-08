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

    @GetMapping("/about")
    public Map<String,String> about(){
        return Map.of(
                "project" , "Inventory management system",
                "frontend" , "React",
                "backend","Java",
                "developer","himanshu"
        );
    }

}
