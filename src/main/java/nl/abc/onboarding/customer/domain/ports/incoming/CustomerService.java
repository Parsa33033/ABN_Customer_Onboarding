package nl.abc.onboarding.customer.domain.ports.incoming;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;

import java.util.concurrent.CompletableFuture;

public interface CustomerService {

    /**
     * to onboard a customer in the database.
     *
     * if the customer exists with the external identifier, then return the
     * customer
     *
     * otherwise onboard the customer by creating a new UUID identifier
     * @param customerData
     * @return
     */
    CompletableFuture<CustomerData> onboard(CustomerData customerData);
}
