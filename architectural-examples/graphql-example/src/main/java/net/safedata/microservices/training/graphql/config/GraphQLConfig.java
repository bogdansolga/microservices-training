package net.safedata.microservices.training.graphql.config;

import jakarta.annotation.PostConstruct;
import net.safedata.microservices.training.graphql.domain.model.Food;
import net.safedata.microservices.training.graphql.domain.model.Restaurant;
import net.safedata.microservices.training.graphql.domain.repository.FoodRepository;
import net.safedata.microservices.training.graphql.domain.repository.RestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GraphQLConfig {

    private final List<Restaurant> restaurants = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();

    @PostConstruct
    public void init() {
        initializeRestaurants();
        initializeFoods();
    }

    @Bean
    public FoodRepository foodRepository() {
        return new FoodRepository(foods);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return new RestaurantRepository(restaurants);
    }

    private void initializeRestaurants() {
        for (int restaurantId = 0; restaurantId < 10; ++restaurantId) {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(restaurantId + "");
            restaurant.setName("Restaurant " + restaurantId);
            restaurant.setThumbnail("http://epiceats.com/restaurants/" + restaurantId);
            restaurants.add(restaurant);
        }
    }

    private void initializeFoods() {
        for (int foodId = 0; foodId < 10; ++foodId) {
            for (int restaurantId = 0; restaurantId < 10; ++restaurantId) {
                Food food = new Food();
                food.setId("Food "  + restaurantId + " " + foodId);
                food.setTitle("Food for the restaurant with the ID " + restaurantId + ": " + foodId);
                food.setCategory("Food category");
                food.setDescription("Food " + foodId + " + from the restaurant " + restaurantId);
                final String theRestaurantId = "Restaurant " + restaurantId;
                food.setRestaurant(restaurants.stream()
                                              .filter(restaurant -> restaurant.getName().equals(theRestaurantId))
                                              .findFirst()
                                              .orElseThrow());
                foods.add(food);
            }
        }
    }
}