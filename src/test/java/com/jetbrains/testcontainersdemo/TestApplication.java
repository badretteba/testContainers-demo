package com.jetbrains.testcontainersdemo;
import org.springframework.boot.SpringApplication;
public class TestApplication
{
    //the .from need spring boot 3.1 version and up
    public static void main(String[] args) {
        SpringApplication
                .from(TestcontainersDemoApplication::main)
                .with(LocalContainerConfiguration.class)
                .run(args);
    }
}
