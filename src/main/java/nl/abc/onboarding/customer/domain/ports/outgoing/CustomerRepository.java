package nl.abc.onboarding.customer.domain.ports.outgoing;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;

import java.util.concurrent.CompletableFuture;

public interface CustomerRepository {

    /**
     * read customer data by external identifier from the database
     * @return the customer data as a CompletableFuture
     */
    CompletableFuture<CustomerData> readByExternalIdentifier();

    /**
     * write customer data to the database
     * @param customerData dto to write
     * @return the written customer data as a CompletableFuture
     */
    CompletableFuture<CustomerData> write(CustomerData customerData);
}
