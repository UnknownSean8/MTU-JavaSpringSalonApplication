package ie.sean.dao.stylist;

import ie.sean.dao.dto.Stylists;
import ie.sean.dao.dto.StylistsRowMapper;
import ie.sean.entities.salon.Salon;
import ie.sean.entities.stylist.Stylist;
import ie.sean.entities.stylist.StylistRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StylistDaoImpl implements StylistDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int count() {
        return namedParameterJdbcTemplate.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM stylists", Integer.class);
    }

    @Override
    public Optional<Stylist> findStylistById(int id) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", id);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("SELECT * FROM stylists WHERE stylist_id = :id", mapSqlParameterSource, new StylistRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void insertOneStylist(Stylist stylist) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", stylist.getId());
        mapSqlParameterSource.addValue("name", stylist.getName());
        mapSqlParameterSource.addValue("phone_number", stylist.getPhone_number());
        mapSqlParameterSource.addValue("annual_salary", stylist.getSalary());
        mapSqlParameterSource.addValue("salon_id", stylist.getSalon_id());
        String SQL = "INSERT INTO stylists (stylist_id, stylist_name, stylist_phone_number, stylist_annual_salary, salon_id) VALUES (:id, :name, :phone_number, :annual_salary, :salon_id)";
        namedParameterJdbcTemplate.update(SQL, mapSqlParameterSource);
    }

    @Override
    public void moveStylist(int stylistId, int newSalonId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("stylistId", stylistId);
        mapSqlParameterSource.addValue("newSalonId", newSalonId);

        String SQL = "UPDATE stylists SET salon_id = :newSalonId WHERE stylist_id = :stylistId";
        namedParameterJdbcTemplate.update(SQL, mapSqlParameterSource);
    }


    @Override
    public void deleteStylistById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        namedParameterJdbcTemplate.update("DELETE FROM stylists WHERE stylist_id = :id", mapSqlParameterSource);
    }

    @Override
    public List<Stylist> getAllStylistFromSalon(int salonId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("salonId", salonId);

        String SQL = "SELECT * FROM stylists s INNER JOIN salons c on s.salon_id = c.salon_id WHERE c.salon_id = :salonId";

        return namedParameterJdbcTemplate.query(SQL, mapSqlParameterSource, new StylistRowMapper());
    }

    @Override
    public List<Stylists> getAllStylistRecord() {
        String SQL = "SELECT s.stylist_name, c.salon_name FROM stylists s INNER JOIN salons c on s.salon_id = c.salon_id";
        return namedParameterJdbcTemplate.getJdbcTemplate().query(SQL, new StylistsRowMapper());
    }
}
