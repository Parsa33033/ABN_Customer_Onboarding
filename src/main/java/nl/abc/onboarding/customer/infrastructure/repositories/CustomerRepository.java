package nl.abc.onboarding.customer.infrastructure.repositories;

import nl.abc.onboarding.customer.infrastructure.repositories.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByExternalIdentifier(String externalIdentifier);
}

