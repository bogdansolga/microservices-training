package net.safedata.microservices.training.graphql.controller;

import net.safedata.microservices.training.graphql.domain.model.Food;
import net.safedata.microservices.training.graphql.domain.model.Restaurant;
import net.safedata.microservices.training.graphql.domain.repository.FoodRepository;
import net.safedata.microservices.training.graphql.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class FoodController {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public FoodController(RestaurantRepository restaurantRepository, FoodRepository foodRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
    }

    @QueryMapping
    public List<Food> recentFood(@Argument int count, @Argument int offset) {
        return foodRepository.getRecentFood(count, offset);
    }

    @QueryMapping
    public List<Food> allFoods() {
        return foodRepository.getAll();
    }

    @SchemaMapping
    public Restaurant getRestaurant(Food food) {
        return restaurantRepository.getRestaurantById(food.getRestaurant().getId());
    }

    @SchemaMapping(typeName = "Food", field = "restaurant_id")
    public Restaurant getFirstRestaurant(Food food) {
        return restaurantRepository.getRestaurantById(food.getRestaurant().getId());
    }

    @MutationMapping
    public Food createFood(@Argument String title, @Argument String description,
                           @Argument String category, @Argument String restaurantId) {
        Food food = new Food();
        food.setId(UUID.randomUUID().toString());
        food.setTitle(title);
        food.setDescription(description);
        food.setCategory(category);
        foodRepository.addFood(food);

        return food;
    }
}