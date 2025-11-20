package nl.abc.onboarding.customer.infrastructure.repositories;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.outgoing.CustomerRepository;
import nl.abc.onboarding.customer.infrastructure.repositories.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private nl.abc.onboarding.customer.infrastructure.repositories.CustomerRepository
            customerJpaRepository;

    /**
     * read customer data by external identifier from the database
     *
     * @param externalIdentifier
     * @return
     */
    @Override
    public CompletableFuture<Optional<CustomerData>> readByExternalIdentifier(
            String externalIdentifier) {
        Optional<Customer> customer = customerJpaRepository.findByExternalIdentifier(
                externalIdentifier);
        if (customer.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.empty());
        }
        CustomerData customerData =
                Customer.toDataTransferObject(customer.get());
        return CompletableFuture.completedFuture(Optional.of(customerData));
    }

    /**
     * write customer data to the database
     *
     * @param customerData dto to write
     * @return the written customer data as a CompletableFuture
     */
    @Override
    public CompletableFuture<CustomerData> write(CustomerData customerData) {
        Customer customerEntity =
                Customer.fromDataTransferObject(customerData);
        Customer savedCustomer = customerJpaRepository.save(customerEntity);
        CustomerData savedCustomerData =
                Customer.toDataTransferObject(savedCustomer);
        return CompletableFuture.completedFuture(savedCustomerData);
    }
}
