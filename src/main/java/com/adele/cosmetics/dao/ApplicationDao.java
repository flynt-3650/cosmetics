package com.adele.cosmetics.dao;

import com.adele.cosmetics.models.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class ApplicationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ApplicationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_SQL = "INSERT INTO applications (client_id, status_id, creation_date, processing_date, total) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM applications WHERE application_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM applications";
    private static final String UPDATE_SQL = "UPDATE applications SET client_id = ?, status_id = ?, creation_date = ?, processing_date = ?, total = ? WHERE application_id = ?";
    private static final String DELETE_SQL = "DELETE FROM applications WHERE application_id = ?";

    private static class ApplicationRowMapper implements RowMapper<Application> {
        @Override
        public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
            Application application = new Application();
            application.setApplicationId(rs.getInt("application_id"));
            application.setClientId(rs.getInt("client_id"));
            application.setStatusId(rs.getInt("status_id"));

            Timestamp creationTimestamp = rs.getTimestamp("creation_date");
            if (creationTimestamp != null) {
                application.setCreationDate(creationTimestamp.toLocalDateTime());
            }

            Timestamp processingTimestamp = rs.getTimestamp("processing_date");
            if (processingTimestamp != null) {
                application.setProcessingDate(processingTimestamp.toLocalDateTime());
            }

            application.setTotal(rs.getBigDecimal("total"));
            return application;
        }
    }

    public void insert(Application application) {
        jdbcTemplate.update(INSERT_SQL,
                application.getClientId(),
                application.getStatusId(),
                Timestamp.valueOf(application.getCreationDate()),
                Timestamp.valueOf(application.getProcessingDate()),
                application.getTotal());
    }

    public Application findById(int applicationId) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new ApplicationRowMapper(), applicationId);
    }

    public List<Application> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new ApplicationRowMapper());
    }

    public void update(Application application) {
        jdbcTemplate.update(UPDATE_SQL,
                application.getClientId(),
                application.getStatusId(),
                Timestamp.valueOf(application.getCreationDate()),
                Timestamp.valueOf(application.getProcessingDate()),
                application.getTotal(),
                application.getApplicationId());
    }

    public void delete(int applicationId) {
        jdbcTemplate.update(DELETE_SQL, applicationId);
    }
}
