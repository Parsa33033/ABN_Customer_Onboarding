package nl.abc.onboarding.customer.framework;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;

public interface DomainEntity<DTO extends DataTransferObject> {

    DTO toDataTransferObject();

    DomainEntity<DTO> fromDataTransferObject(DTO dataTransferObject);
}
