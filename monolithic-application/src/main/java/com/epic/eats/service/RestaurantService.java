package com.epic.eats.service;

import com.epic.eats.model.Restaurant;
import com.epic.eats.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public Restaurant findById(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Transactional
    public Restaurant update(Restaurant restaurant) {
        if (!restaurantRepository.existsById(restaurant.getId())) {
            throw new RuntimeException("Restaurant not found with id: " + restaurant.getId());
        }
        return restaurantRepository.save(restaurant);
    }
}
