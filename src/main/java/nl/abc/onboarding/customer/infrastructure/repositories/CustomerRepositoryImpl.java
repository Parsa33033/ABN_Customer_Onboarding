package nl.abc.onboarding.customer.infrastructure.repositories;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.outgoing.CustomerRepository;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CustomerRepositoryImpl implements CustomerRepository {

    @Override
    public CompletableFuture<Optional<CustomerData>> readByExternalIdentifier(String externalIdentifier) {
        throw new NotImplementedException();
    }

    @Override
    public CompletableFuture<CustomerData> write(CustomerData customerData) {
        throw new NotImplementedException();
    }

}
