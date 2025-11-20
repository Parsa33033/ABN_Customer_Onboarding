package nl.abc.onboarding.customer.domain.services;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.entities.CustomerEntity;
import nl.abc.onboarding.customer.domain.ports.incoming.CustomerService;
import nl.abc.onboarding.customer.domain.ports.outgoing.CustomerRepository;

import java.util.concurrent.CompletableFuture;

public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CompletableFuture<CustomerData> onboard(CustomerData customerData) {
        return null;
    }

    public CompletableFuture<CustomerServiceData> validate(CustomerData customerData) {
        return null;
    }

}
