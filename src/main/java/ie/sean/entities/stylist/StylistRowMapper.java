package ie.sean.entities.stylist;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StylistRowMapper implements RowMapper<Stylist> {
        @Override
        public Stylist mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Stylist(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
        }
}
