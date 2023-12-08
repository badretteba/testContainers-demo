package com.jetbrains.testcontainersdemo;
//we can also use TestContainers for local development as well as using it for testing with two
// different instances one for dev and the other for tests we only need to use another test class TestApplication

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class CustomerIntegrationTests {

    @Autowired
    private CustomerDao customerDao;

    @Container
    private static MySQLContainer container = new MySQLContainer("mysql:latest"); // static is used to run the container for all the class tests
    /*@Container
    private static GenericContainer container2 = new GenericContainer("myImageAtDockerHub:version"); // this generic container is for your custom images dockerhub
    we can use in the generic case the withExposedPort(portNumber) to tell testContainers that our image have an app that expose the portNumber and to wait for it to be ready
     there are other useful methods that we can use lik:
        container.withClasspathResourceMapping("application.properties","/tmp/application.properties", BindMode.READ_ONLY)
        container.withFileSystemBind("c:/files path/application.properties","/tmp/application.properties", BindMode.READ_ONLY)
        container.withLogConsumer(new Slf4jLogConsumer(Logger)); // this one is to assign a logger to the container
// we can add @ServiceConnection on the container if we don't want to use registery
    // to inject dynamic properties in this case spring boot will start and stop containers with @ServiceConnection
    so we will not need overrideProps function then it will be created with the properties specified under spring.datasource.
    */
    @DynamicPropertySource  public static void overrideProps(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.username",container::getUsername);
        registry.add("spring.datasource.password",container::getPassword);

    }
    @Test
    void when_using_a_clean_db_this_should_be_empty() {

        List<Customer> customers = customerDao.findAll();
        assertThat(customers).hasSize(2);
    }
}
