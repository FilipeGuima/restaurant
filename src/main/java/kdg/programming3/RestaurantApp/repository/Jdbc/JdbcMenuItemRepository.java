package kdg.programming3.RestaurantApp.repository.Jdbc;

import kdg.programming3.RestaurantApp.domain.Allergen;
import kdg.programming3.RestaurantApp.domain.ItemType;
import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.repository.MenuItemRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("jdbc")

public class JdbcMenuItemRepository implements MenuItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMenuItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<MenuItem> menuItemMapper = (rs, rowNum) -> {
        String allergensStr = rs.getString("allergens");
        List<Allergen> allergens;

        if (allergensStr == null || allergensStr.isBlank()) {
            allergens = Collections.emptyList();
        } else {
            allergens = Arrays.stream(allergensStr.split(","))
                    .map(String::trim)
                    .map(Allergen::valueOf)
                    .collect(Collectors.toList());
        }

        return new MenuItem(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                allergens,
                rs.getInt("calories"),
                rs.getString("image_name"),
                rs.getString("item_type") != null ? ItemType.valueOf(rs.getString("item_type")) : null
        );
    };

    @Override
    public List<MenuItem> findAll() {
        return jdbcTemplate.query("SELECT * FROM MENU_ITEM", menuItemMapper);
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        List<MenuItem> items = jdbcTemplate.query("SELECT * FROM MENU_ITEM WHERE id = ?", menuItemMapper, id);
        return items.stream().findFirst();
    }

    @Override
    public MenuItem save(MenuItem item) {
        jdbcTemplate.update("INSERT INTO MENU_ITEM (name, price, allergens, calories, image_name, item_type) VALUES (?, ?, ?, ?, ?, ?)",
                item.getName(), item.getPrice(), item.getAllergens().stream().map(Enum::name).collect(Collectors.joining(",")),
                item.getCalories(), item.getImageName(), item.getItemType().name());
        return item;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM MENU_ITEM WHERE id = ?", id);
    }
}