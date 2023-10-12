package ie.sean.entities.salon;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalonRowMapper implements RowMapper<Salon> {
    @Override
    public Salon mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Salon(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
    }
}
