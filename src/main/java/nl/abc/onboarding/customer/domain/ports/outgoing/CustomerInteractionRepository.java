package nl.abc.onboarding.customer.domain.ports.outgoing;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CustomerInteractionRepository {

    /**
     * read customer data by external identifier from the database
     * @return an Optional customer data as a CompletableFuture. if not found, the Optional is empty
     */
    CompletableFuture<Optional<CustomerData>> readByExternalIdentifier(String externalIdentifier);

    /**
     * write customer data to the database
     * @param customerData dto to write
     * @return the written customer data as a CompletableFuture
     */
    CompletableFuture<CustomerData> write(CustomerData customerData);
}
