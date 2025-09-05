package com.cupia.framework.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CUPIA Event-Driven Batch Framework Main Application
 * 
 * This is the main application class for the CUPIA Event-Driven Batch Framework.
 * It serves as a foundation for other services that want to use this framework
 * as a dependency without forcing specific configurations.
 * 
 * @author CUPIA Framework Team
 * @version 1.0.0
 */
@SpringBootApplication
public class BatchFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchFrameworkApplication.class, args);
    }
}