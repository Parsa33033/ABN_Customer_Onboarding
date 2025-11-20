package nl.abc.onboarding.customer.domain.services;

import lombok.extern.slf4j.Slf4j;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.entities.CustomerEntity;
import nl.abc.onboarding.customer.domain.ports.incoming.CustomerService;
import nl.abc.onboarding.customer.domain.ports.outgoing.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(
            CustomerServiceImpl.class);
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CompletableFuture<CustomerData> onboard(CustomerData customerData) {
        return validate(customerData)
                .thenCompose(this::tryToFindCustomerByExternalIdentifier)
                .thenCompose(this::tryToCreateCustomerIfDoesNotAlreadyExist)
                .thenCompose(this::toCustomerData);
    }

    public CompletableFuture<CustomerServiceData> validate(
            CustomerData customerData) {
        // auto validation through value objects and domain entitiy
        CustomerEntity entity =
                CustomerEntity.build().fromDataTransferObject(customerData);
        CustomerServiceData data = new CustomerServiceData(entity);
        return CompletableFuture.completedFuture(data);
    }

    public CompletableFuture<CustomerServiceData> tryToFindCustomerByExternalIdentifier(
            CustomerServiceData data) {
        String externalIdentifier =
                data.customerEntity.toDataTransferObject().externalIdentifier();
        return customerRepository.readByExternalIdentifier(externalIdentifier)
                .thenApply(optionalCustomer -> {
                    if (optionalCustomer.isPresent()) {
                        return data.setReadCustomerResult(
                                optionalCustomer.get());
                    }
                    return data;
                });
    }

    public CompletableFuture<CustomerServiceData> tryToCreateCustomerIfDoesNotAlreadyExist(
            CustomerServiceData data) {
        if (data.readCustomerResult != null) {
            // tracing the behaviour of the onboarding to make sure we don't create duplicates
            // also good for debugging purposes
            log.info(
                    "Customer with external identifier {} already exists. Skipping creation.",
                    data.readCustomerResult.externalIdentifier());
            return CompletableFuture.completedFuture(data);
        }
        return customerRepository.write(
                        data.customerEntity.toDataTransferObject())
                .thenApply(data::setWriteCustomerResult);
    }

    public CompletableFuture<CustomerData> toCustomerData(
            CustomerServiceData data) {
        return CompletableFuture.completedFuture(data.customerEntity.toDataTransferObject());
    }
}
