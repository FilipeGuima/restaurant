package kdg.programming3.RestaurantApp.service;

import kdg.programming3.RestaurantApp.domain.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    List<MenuItem> getAllMenuItems();
    Optional<MenuItem> getMenuItemsById(Long Id);
}
