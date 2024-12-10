package com.adele.cosmetics.dao;

import com.adele.cosmetics.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ClientDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_SQL = "INSERT INTO clients (client_type_id, first_name, last_name, contact_number, email, address) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM clients WHERE client_id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM clients";
    private static final String UPDATE_SQL = "UPDATE clients SET client_type_id = ?, first_name = ?, last_name = ?, contact_number = ?, email = ?, address = ? WHERE client_id = ?";
    private static final String DELETE_SQL = "DELETE FROM clients WHERE client_id = ?";
    private static final String COUNT_CONTACT_NUMBER_SQL = "SELECT COUNT(*) FROM clients WHERE contact_number = ? AND client_id <> ?";
    private static final String COUNT_EMAIL_SQL = "SELECT COUNT(*) FROM clients WHERE email = ? AND client_id <> ?";

    private static class ClientRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client();
            client.setClientId(rs.getInt("client_id"));
            client.setClientTypeId(rs.getInt("client_type_id"));
            client.setFirstName(rs.getString("first_name"));
            client.setLastName(rs.getString("last_name"));
            client.setContactNumber(rs.getString("contact_number"));
            client.setEmail(rs.getString("email"));
            client.setAddress(rs.getString("address"));
            return client;
        }
    }

    public void insert(Client client) {
        jdbcTemplate.update(INSERT_SQL,
                client.getClientTypeId(),
                client.getFirstName(),
                client.getLastName(),
                client.getContactNumber(),
                client.getEmail(),
                client.getAddress());
    }

    public Client findById(int clientId) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new ClientRowMapper(), clientId);
    }

    public List<Client> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new ClientRowMapper());
    }

    public void update(Client client) {
        jdbcTemplate.update(UPDATE_SQL,
                client.getClientTypeId(),
                client.getFirstName(),
                client.getLastName(),
                client.getContactNumber(),
                client.getEmail(),
                client.getAddress(),
                client.getClientId());
    }

    public void delete(int clientId) {
        jdbcTemplate.update(DELETE_SQL, clientId);
    }

    public boolean isContactNumberTaken(String contactNumber, int clientId) {
        Integer count = jdbcTemplate.queryForObject(COUNT_CONTACT_NUMBER_SQL, Integer.class, contactNumber, clientId);
        return count != null && count > 0;
    }

    public boolean isEmailTaken(String email, int clientId) {
        Integer count = jdbcTemplate.queryForObject(COUNT_EMAIL_SQL, Integer.class, email, clientId);
        return count != null && count > 0;
    }
}
