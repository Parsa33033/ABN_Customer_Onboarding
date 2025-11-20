package nl.abc.onboarding.customer.infrastructure.beans;

import nl.abc.onboarding.customer.domain.ports.outgoing.CustomerRepository;
import nl.abc.onboarding.customer.infrastructure.filestorage.FileStorage;
import nl.abc.onboarding.customer.infrastructure.repositories.CustomerRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerRepositoryConfig {

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepositoryImpl();
    }
}

