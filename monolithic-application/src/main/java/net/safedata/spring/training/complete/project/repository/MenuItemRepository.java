package net.safedata.spring.training.complete.project.repository;

import net.safedata.spring.training.complete.project.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByRestaurantId(int restaurantId);
}
