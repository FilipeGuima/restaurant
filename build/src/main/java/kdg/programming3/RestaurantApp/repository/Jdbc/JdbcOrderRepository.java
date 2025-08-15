package kdg.programming3.RestaurantApp.repository.Jdbc;

import kdg.programming3.RestaurantApp.domain.MenuItem;
import kdg.programming3.RestaurantApp.domain.Order;
import kdg.programming3.RestaurantApp.domain.OrderStatus;
import kdg.programming3.RestaurantApp.repository.CustomerRepository;
import kdg.programming3.RestaurantApp.repository.MenuItemRepository;
import kdg.programming3.RestaurantApp.repository.OrderRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Profile("jdbc")

public class JdbcOrderRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;
    private final MenuItemRepository menuItemRepository;
    private final SimpleJdbcInsert orderInserter;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate, CustomerRepository customerRepository, MenuItemRepository menuItemRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRepository = customerRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ORDERS")
                .usingGeneratedKeyColumns("id");
    }

    private Order mapOrderRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setDate(rs.getDate("order_date").toLocalDate());
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        customerRepository.findById(rs.getLong("customer_id")).ifPresent(order::setCustomer);
        return order;
    }

    private void loadItemsForOrders(List<Order> orders) {
        for (Order order : orders) {
            String sql = "SELECT menu_item_id FROM ORDER_MENU_ITEM WHERE order_id = ?";
            List<Long> itemIds = jdbcTemplate.queryForList(sql, Long.class, order.getId());
            List<MenuItem> items = itemIds.stream()
                    .map(menuItemRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            order.setItems(items);
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = jdbcTemplate.query("SELECT * FROM ORDERS", this::mapOrderRow);
        if (!orders.isEmpty()) {
            loadItemsForOrders(orders);
        }
        return orders;
    }

    @Override
    public Optional<Order> findById(Long id) {
        List<Order> orders = jdbcTemplate.query("SELECT * FROM ORDERS WHERE id = ?", this::mapOrderRow, id);
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        Order order = orders.get(0);
        loadItemsForOrders(List.of(order));
        return Optional.of(order);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM ORDER_MENU_ITEM WHERE order_id = ?", id);
        jdbcTemplate.update("DELETE FROM ORDERS WHERE id = ?", id);
    }

    @Override
    public Order save(Order order) {
        Map<String, Object> params = new HashMap<>();
        params.put("order_date", order.getDate() != null ? order.getDate() : LocalDate.now());
        params.put("status", order.getStatus().name());
        params.put("customer_id", order.getCustomer().getId());

        Number newId = orderInserter.executeAndReturnKey(params);
        order.setId(newId.longValue());

        for (MenuItem item : order.getItems()) {
            jdbcTemplate.update("INSERT INTO ORDER_MENU_ITEM (order_id, menu_item_id) VALUES (?, ?)",
                    order.getId(), item.getId());
        }
        return order;
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        List<Order> orders = jdbcTemplate.query("SELECT * FROM ORDERS WHERE status = ?", this::mapOrderRow, status.name());
        if (!orders.isEmpty()) {
            loadItemsForOrders(orders);
        }
        return orders;
    }
}