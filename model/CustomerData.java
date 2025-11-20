package onboarding.model;

import java.util.Objects;

public class CustomerData {
    private final String id;
    private final String externalId;
    private final String name;

    public CustomerData(String id, String externalId, String name) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerData)) return false;
        CustomerData that = (CustomerData) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(externalId, that.externalId) &&
               Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, externalId, name);
    }

    @Override
    public String toString() {
        return "CustomerData{" +
               "id='" + id + '\'' +
               ", externalId='" + externalId + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
