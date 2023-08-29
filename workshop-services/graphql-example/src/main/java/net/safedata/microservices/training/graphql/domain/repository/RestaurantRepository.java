package net.safedata.microservices.training.graphql.domain.repository;

import net.safedata.microservices.training.graphql.domain.model.Restaurant;

import java.util.List;

public class RestaurantRepository {

    private final List<Restaurant> restaurants;

    public RestaurantRepository(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Restaurant getRestaurantById(String id) {
        return restaurants.stream()
                          .filter(restaurant -> id.equals(restaurant.getId()))
                          .findFirst()
                          .orElseThrow(RuntimeException::new);
    }
}
