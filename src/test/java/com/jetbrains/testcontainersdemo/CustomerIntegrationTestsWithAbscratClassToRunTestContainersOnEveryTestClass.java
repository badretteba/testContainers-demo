package com.jetbrains.testcontainersdemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//All Test classes that need to use the testContainer instance need to extend this class
public abstract class CustomerIntegrationTestsWithAbscratClassToRunTestContainersOnEveryTestClass {
    private static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:latest")
            .withReuse(true); // static is used to run the container for all the class tests
    @BeforeAll
    public static void setup(){
        container.start();
    }
    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.username",container::getUsername);
        registry.add("spring.datasource.password",container::getPassword);

    }
}
