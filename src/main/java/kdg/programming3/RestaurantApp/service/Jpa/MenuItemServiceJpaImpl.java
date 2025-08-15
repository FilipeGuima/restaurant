package kdg.programming3.RestaurantApp.service.Jpa;

import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.repository.Jpa.CustomerRepositoryJpa;
import kdg.programming3.RestaurantApp.repository.Jpa.MenuItemRepositoryJpa;
import kdg.programming3.RestaurantApp.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("jpa")
public class MenuItemServiceJpaImpl implements MenuItemService {

    private final MenuItemRepositoryJpa menuItemRepository;

    @Autowired
    public MenuItemServiceJpaImpl(MenuItemRepositoryJpa menuItemRepository) {
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
