package nl.abc.onboarding.customer.infrastructure.beans;

import nl.abc.onboarding.customer.domain.ports.outgoing.CustomerInteractionRepository;
import nl.abc.onboarding.customer.infrastructure.repositories.CustomerInteractionRepositoryImpl;
import nl.abc.onboarding.customer.infrastructure.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerInteractionRepositoryConfig {

    @Bean
    public CustomerInteractionRepository customerRepository(
            @Autowired CustomerRepository customerRepository) {
        return new CustomerInteractionRepositoryImpl(customerRepository);
    }
}

