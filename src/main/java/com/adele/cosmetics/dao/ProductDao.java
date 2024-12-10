package com.adele.cosmetics.dao;

import com.adele.cosmetics.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_SQL = "INSERT INTO products (warehouse_id, product_name, product_description, price, production_date, expiration_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM products WHERE product_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM products";
    private static final String UPDATE_SQL = "UPDATE products SET warehouse_id = ?, product_name = ?, product_description = ?, price = ?, production_date = ?, expiration_date = ? WHERE product_id = ?";
    private static final String DELETE_SQL = "DELETE FROM products WHERE product_id = ?";

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setWarehouseId(rs.getInt("warehouse_id"));
            product.setProductName(rs.getString("product_name"));
            product.setProductDescription(rs.getString("product_description"));
            product.setPrice(rs.getBigDecimal("price"));

            Timestamp productionTimestamp = rs.getTimestamp("production_date");
            if (productionTimestamp != null) {
                product.setProductionDate(productionTimestamp.toLocalDateTime());
            }

            Timestamp expirationTimestamp = rs.getTimestamp("expiration_date");
            if (expirationTimestamp != null) {
                product.setExpirationDate(expirationTimestamp.toLocalDateTime());
            }

            return product;
        }
    }

    public void insert(Product product) {
        jdbcTemplate.update(
                INSERT_SQL,
                product.getWarehouseId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice(),
                Timestamp.valueOf(product.getProductionDate()),
                Timestamp.valueOf(product.getExpirationDate())
        );
    }

    public Product findById(int productId) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new ProductRowMapper(), productId);
    }

    public List<Product> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new ProductRowMapper());
    }

    public void update(Product product) {
        jdbcTemplate.update(
                UPDATE_SQL,
                product.getWarehouseId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice(),
                Timestamp.valueOf(product.getProductionDate()),
                Timestamp.valueOf(product.getExpirationDate()),
                product.getProductId()
        );
    }

    public void delete(int productId) {
        jdbcTemplate.update(DELETE_SQL, productId);
    }
}
