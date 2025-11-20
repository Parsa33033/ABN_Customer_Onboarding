package nl.abc.onboarding.customer.domain.services;

import lombok.Getter;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.entities.CustomerEntity;

@Getter
public class CustomerServiceData {

    CustomerEntity customerEntity;

    CustomerData readCustomerResult;

    CustomerData writeCustomerResult;

    public CustomerServiceData(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public CustomerServiceData setReadCustomerResult(CustomerData readCustomerResult) {
        this.readCustomerResult = readCustomerResult;
        return this;
    }

    public CustomerServiceData setWriteCustomerResult(CustomerData writeCustomerResult) {
        this.writeCustomerResult = writeCustomerResult;
        return this;
    }
}
