package org.devkirby.app.hanimman.configs;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Value("${app.google-credentials}")
    private String googleCredentialsPath;

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        FileInputStream googleCredentialsInputStream = new FileInputStream(
                googleCredentialsPath);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(googleCredentialsInputStream))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
