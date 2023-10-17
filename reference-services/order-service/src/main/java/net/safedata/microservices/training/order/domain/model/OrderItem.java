package net.safedata.microservices.training.order.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class OrderItem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "restaurant_id", length = 5)
    private long restaurantId;

    @Column(name = "food_id", length = 5)
    private long foodId;

    @Column(name = "price", length = 5)
    private long price;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Order.class, optional = false)
    @JoinColumn(
            name = "order_id",
            foreignKey = @ForeignKey(name = "order_fk"),
            referencedColumnName = "id",
            nullable = false
    )
    private Order order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getFoodId() {
        return foodId;
    }

    public void setFoodId(long foodId) {
        this.foodId = foodId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return id == orderItem.id && restaurantId == orderItem.restaurantId && foodId == orderItem.foodId &&
                Objects.equals(name, orderItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, restaurantId, foodId, name);
    }
}
