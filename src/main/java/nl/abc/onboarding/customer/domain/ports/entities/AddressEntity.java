package nl.abc.onboarding.customer.domain.ports.entities;

import nl.abc.onboarding.customer.domain.ports.dtos.AddressData;
import nl.abc.onboarding.customer.domain.ports.entities.exceptions.ExceptionUtil;
import nl.abc.onboarding.customer.framework.DomainEntity;

public class AddressEntity implements DomainEntity<AddressData> {

    private String address;
    private String zipCode;
    private String city;
    private String country;

    private AddressEntity() {}

    private static Builder builder() {
        return new Builder();
    }

    public static AddressEntity build() {
        return new AddressEntity();
    }

    @Override
    public AddressData toDataTransferObject() {
        return new AddressData(
                this.address,
                this.zipCode,
                this.city,
                this.country
        );
    }

    @Override
    public AddressEntity fromDataTransferObject(AddressData dataTransferObject) {
        AddressEntity built = AddressEntity.builder()
                .address(dataTransferObject.address())
                .zipCode(dataTransferObject.zipCode())
                .city(dataTransferObject.city())
                .country(dataTransferObject.Country()) // accessor uses the record component name
                .build();

        // copy built values into this instance
        this.address = built.address;
        this.zipCode = built.zipCode;
        this.city = built.city;
        this.country = built.country;

        return this;
    }

    public static class Builder {
        private String address;
        private String zipCode;
        private String city;
        private String country;

        public Builder address(String address) {
            ExceptionUtil.throwIfNull(AddressEntity.class, "address", address);
            this.address = address;
            return this;
        }

        public Builder zipCode(String zipCode) {
            ExceptionUtil.throwIfNull(AddressEntity.class, "zipCode", zipCode);
            this.zipCode = zipCode;
            return this;
        }

        public Builder city(String city) {
            ExceptionUtil.throwIfNull(AddressEntity.class, "city", city);
            this.city = city;
            return this;
        }

        public Builder country(String country) {
            ExceptionUtil.throwIfNull(AddressEntity.class, "country", country);
            this.country = country;
            return this;
        }

        public AddressEntity build() {
            AddressEntity e = new AddressEntity();
            e.address = this.address;
            e.zipCode = this.zipCode;
            e.city = this.city;
            e.country = this.country;
            return e;
        }
    }
}

