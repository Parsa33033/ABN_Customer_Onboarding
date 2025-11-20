package nl.abc.onboarding.customer.domain.services;

import lombok.Getter;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;

@Getter
public class CustomerServiceData {

    CustomerData customerData;

    public CustomerServiceData (CustomerData customerData) {
        this.customerData = customerData;
    }
}
