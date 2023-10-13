package ie.sean.dao.dto;

import ie.sean.entities.stylist.StylistRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StylistsRowMapper implements RowMapper<Stylists> {
    @Override
    public Stylists mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Stylists(rs.getString(1), rs.getString(2));
    }
}
