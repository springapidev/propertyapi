package com.coderbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * @author Md. Rajaul Islam
 */
@EnableJpaRepositories("com.coderbd.repository")
@EntityScan("com.coderbd.entity")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PropertyapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyapiApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
