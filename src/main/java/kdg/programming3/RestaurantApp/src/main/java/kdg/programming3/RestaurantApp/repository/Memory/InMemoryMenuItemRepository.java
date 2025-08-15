package kdg.programming3.RestaurantApp.repository.Memory;

import kdg.programming3.RestaurantApp.Week1.DataFactory;
import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.repository.MenuItemRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("inmemory")
public class InMemoryMenuItemRepository implements MenuItemRepository {

    public List<MenuItem> findAll() {
        return InMemoryDataFactory.getMenuItems();
    }

    public Optional<MenuItem> findById(Long id) {
        return InMemoryDataFactory.getMenuItems().stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }

    public MenuItem save(MenuItem item) {
// just have it to have it but wont be used for InMemory
        return null;
    }

    public void deleteById(Long id) {
// just have it to have it but wont be used for InMemory
    }
}
