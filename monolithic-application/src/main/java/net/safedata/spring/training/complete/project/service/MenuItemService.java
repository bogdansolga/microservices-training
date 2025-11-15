package net.safedata.spring.training.complete.project.service;

import net.safedata.spring.training.complete.project.model.MenuItem;
import net.safedata.spring.training.complete.project.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    public MenuItem create(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Transactional(readOnly = true)
    public MenuItem findById(int id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<MenuItem> findByRestaurantId(int restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Transactional(readOnly = true)
    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    @Transactional
    public MenuItem update(MenuItem menuItem) {
        if (!menuItemRepository.existsById(menuItem.getId())) {
            throw new RuntimeException("MenuItem not found with id: " + menuItem.getId());
        }
        return menuItemRepository.save(menuItem);
    }

    @Transactional
    public void delete(int id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("MenuItem not found with id: " + id);
        }
        menuItemRepository.deleteById(id);
    }
}
