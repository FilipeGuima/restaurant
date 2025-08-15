package kdg.programming3.RestaurantApp.service.Jpa;

import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.repository.MenuItemRepository;
import kdg.programming3.RestaurantApp.service.MenuItemService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile({"jpa-h2", "jpa-postgres"})
public class MenuItemServiceJpaImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceJpaImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public Optional<MenuItem> getMenuItemsById(Long id) {
        return menuItemRepository.findById(id);
    }
}
