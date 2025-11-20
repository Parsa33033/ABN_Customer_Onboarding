package nl.abc.onboarding.customer.infrastructure.repositories;

import nl.abc.onboarding.customer.infrastructure.repositories.entities.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository("customerJpaRepository")
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByExternalIdentifier(String externalIdentifier);
}

