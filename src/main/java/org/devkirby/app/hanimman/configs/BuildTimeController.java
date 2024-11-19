package org.devkirby.app.hanimman.configs;

import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@PropertySource("classpath:version.properties")
public class BuildTimeController {
    private final Environment env;

    @GetMapping("/buildTime")
    public ResponseEntity<Map<String,String>> getAppVersion() {
        return ResponseEntity.ok(Map.of("buildTime",env.getProperty("app.buildTime")));
    }
}