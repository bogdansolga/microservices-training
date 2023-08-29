package net.safedata.microservices.training.graphql.domain.repository;

import net.safedata.microservices.training.graphql.domain.model.Food;

import java.util.List;
import java.util.stream.Collectors;

public class FoodRepository {

    private final List<Food> foods;

    public FoodRepository(List<Food> foods) {
        this.foods = foods;
    }

    public List<Food> getRecentFood(int count, int offset) {
        return foods.stream()
                    .skip(offset)
                    .limit(count)
                    .collect(Collectors.toList());
    }

    public void addFood(Food food) {
        foods.add(food);
    }
}
