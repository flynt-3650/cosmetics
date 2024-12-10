package com.adele.cosmetics.dao;

import com.adele.cosmetics.models.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OrderStatusDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderStatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_SQL = "INSERT INTO order_status (status_name, status_description) VALUES (?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM order_status WHERE status_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM order_status";
    private static final String UPDATE_SQL = "UPDATE order_status SET status_name = ?, status_description = ? WHERE status_id = ?";
    private static final String DELETE_SQL = "DELETE FROM order_status WHERE status_id = ?";

    private static class OrderStatusRowMapper implements RowMapper<OrderStatus> {
        @Override
        public OrderStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setStatusId(rs.getInt("status_id"));
            orderStatus.setStatusName(rs.getString("status_name"));
            orderStatus.setStatusDescription(rs.getString("status_description"));
            return orderStatus;
        }
    }

    public void insert(OrderStatus orderStatus) {
        jdbcTemplate.update(INSERT_SQL,
                orderStatus.getStatusName(),
                orderStatus.getStatusDescription());
    }

    public OrderStatus findById(int statusId) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new OrderStatusRowMapper(), statusId);
    }

    public List<OrderStatus> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new OrderStatusRowMapper());
    }

    public void update(OrderStatus orderStatus) {
        jdbcTemplate.update(UPDATE_SQL,
                orderStatus.getStatusName(),
                orderStatus.getStatusDescription(),
                orderStatus.getStatusId());
    }

    public void delete(int statusId) {
        jdbcTemplate.update(DELETE_SQL, statusId);
    }
}
