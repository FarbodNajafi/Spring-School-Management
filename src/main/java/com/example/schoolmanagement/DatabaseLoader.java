package com.example.schoolmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * This class logs the Database mock initiation process.
 **/
@Configuration
public class DatabaseLoader {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    /**
     * Todo: should initialize the database
     **/
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("Initializing the database");
            log.info("Database filled");
        };
    }
}
