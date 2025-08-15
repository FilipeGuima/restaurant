package kdg.programming3.RestaurantApp.service;

import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile({"jdbc-h2", "jdbc-postgres"})
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public Optional<MenuItem> getMenuItemsById(Long Id) {
        return menuItemRepository.findById(Id);
    }
}
