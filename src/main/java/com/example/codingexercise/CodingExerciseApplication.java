package com.example.codingexercise;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Coding Exercise",
                version = "v1.0",
                description = "API for Coding Exercise",
                contact = @Contact(
                        name = "Ikechukwu Enumah",
                        email = "enumahinm@gmail.com",
                        url = "https://enumahin.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://enumahin.com/license"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Coding Exercise",
                url = "https://enumahin.com/coding-exercise"
        )
)
@SpringBootApplication
public class CodingExerciseApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodingExerciseApplication.class, args);
    }

}
