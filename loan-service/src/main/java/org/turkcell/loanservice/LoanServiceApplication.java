package org.turkcell.loanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.turkcell.loanservice")
@EnableJpaRepositories(basePackages = "org.turkcell.loanservice.infrastructure.persistence.repository")
@EntityScan(basePackages = "org.turkcell.loanservice.infrastructure.persistence.entity")
public class LoanServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanServiceApplication.class, args);
    }

}