package net.safedata.microservices.training.dto.product;

import net.safedata.microservices.training.dto.AbstractDTO;

import java.util.Objects;

public class ProductDTO extends AbstractDTO {

    private final long id;
    private final String name;
    private final double price;

    public ProductDTO(final long id, final String name, final double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
