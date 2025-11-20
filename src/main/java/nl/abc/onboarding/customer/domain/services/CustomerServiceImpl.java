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
