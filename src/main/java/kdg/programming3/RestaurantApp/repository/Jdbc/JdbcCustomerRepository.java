package kdg.programming3.RestaurantApp.repository.Jdbc;

import kdg.programming3.RestaurantApp.domain.Customer;
import kdg.programming3.RestaurantApp.repository.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert customerInserter;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CUSTOMER")
                .usingGeneratedKeyColumns("id");
    }

    private static final class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Customer(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getDate("creation_date").toLocalDate()
            );
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM CUSTOMER", new CustomerRowMapper());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        List<Customer> customers = jdbcTemplate.query("SELECT * FROM CUSTOMER WHERE id = ?", new CustomerRowMapper(), id);
        return customers.stream().findFirst();
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM CUSTOMER WHERE id = ?", id);
    }

    @Override
    public Customer save(Customer customer) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(customer);
        customer.setCreationDate(LocalDate.now());

        Number newId = customerInserter.executeAndReturnKey(params);

        customer.setId(newId.longValue());
        return customer;
    }

    @Override
    public List<Customer> search(String name, String email, LocalDate creationDate, Long id) {
        String sql = """
        SELECT * FROM CUSTOMER 
        WHERE (? IS NULL OR (LOWER(first_name) LIKE LOWER(?) OR LOWER(last_name) LIKE LOWER(?)))
          AND (? IS NULL OR LOWER(email) LIKE LOWER(?))
          AND (? IS NULL OR creation_date = ?)
          AND (? IS NULL OR id = ?)
    """;

        String namePattern = (name != null) ? "%" + name + "%" : null;
        String emailPattern = (email != null) ? "%" + email + "%" : null;

        return jdbcTemplate.query(
                sql,
                new CustomerRowMapper(),
                name, namePattern, namePattern,
                email, emailPattern,
                creationDate, creationDate,
                id, id
        );
    }




    @Override
    public List<Customer> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM CUSTOMER WHERE email = ?", new CustomerRowMapper(), email);
    }

}