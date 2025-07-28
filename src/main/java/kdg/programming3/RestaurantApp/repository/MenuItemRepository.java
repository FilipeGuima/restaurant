package kdg.programming3.RestaurantApp.repository;

import kdg.programming3.RestaurantApp.domain.MenuItem;
import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {
    List<MenuItem> findAll();
    Optional<MenuItem> findById(Long id);
    MenuItem save(MenuItem item);
    void deleteById(Long id);
}
