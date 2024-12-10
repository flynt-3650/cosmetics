package com.adele.cosmetics.dao;

import com.adele.cosmetics.models.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BasketDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BasketDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_SQL = "INSERT INTO basket (application_id, product_id, product_quantity, price_per_unit, total_amount) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM basket WHERE entry_basket = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM basket";
    private static final String UPDATE_SQL = "UPDATE basket SET application_id = ?, product_id = ?, product_quantity = ?, price_per_unit = ?, total_amount = ? WHERE entry_basket = ?";
    private static final String DELETE_SQL = "DELETE FROM basket WHERE entry_basket = ?";

    private static class BasketRowMapper implements RowMapper<Basket> {
        @Override
        public Basket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Basket basket = new Basket();
            basket.setEntryBasket(rs.getInt("entry_basket"));
            basket.setApplicationId(rs.getInt("application_id"));
            basket.setProductId(rs.getInt("product_id"));
            basket.setProductQuantity(rs.getInt("product_quantity"));
            basket.setPricePerUnit(rs.getBigDecimal("price_per_unit"));
            basket.setTotalAmount(rs.getBigDecimal("total_amount"));
            return basket;
        }
    }

    public void insert(Basket basket) {
        jdbcTemplate.update(INSERT_SQL,
                basket.getApplicationId(),
                basket.getProductId(),
                basket.getProductQuantity(),
                basket.getPricePerUnit(),
                basket.getTotalAmount());
    }

    public Basket findById(int entryBasket) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new BasketRowMapper(), entryBasket);
    }

    public List<Basket> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new BasketRowMapper());
    }

    public void update(Basket basket) {
        jdbcTemplate.update(UPDATE_SQL,
                basket.getApplicationId(),
                basket.getProductId(),
                basket.getProductQuantity(),
                basket.getPricePerUnit(),
                basket.getTotalAmount(),
                basket.getEntryBasket());
    }

    public void delete(int entryBasket) {
        jdbcTemplate.update(DELETE_SQL, entryBasket);
    }
}
