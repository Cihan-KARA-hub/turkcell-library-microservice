package org.turkcell.loanservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.turkcell.loanservice.application.service.LoanService;
import org.turkcell.loanservice.domain.repository.LoanRepository;


@Configuration
public class LoanServiceConfig {


    @Bean
    public LoanService loanService(LoanRepository loanRepository) {
        // Yeni bir LoanService oluştur ve repository'yi ver
        return new LoanService(loanRepository);
    }
}

