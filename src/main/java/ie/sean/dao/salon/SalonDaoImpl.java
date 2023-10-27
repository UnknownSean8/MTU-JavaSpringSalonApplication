package ie.sean.dao.salon;

import ie.sean.entities.salon.Salon;
import ie.sean.entities.salon.SalonRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SalonDaoImpl implements SalonDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int count() {
        return namedParameterJdbcTemplate.getJdbcTemplate().queryForObject("SELECT count(*) FROM salons", Integer.class);
    }

    @Override
    public List<Salon> findAll() {
        return namedParameterJdbcTemplate.getJdbcTemplate().query("SELECT * FROM salons", new SalonRowMapper());
    }

    @Override
    public void insertOne(Salon newSalon) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", newSalon.getId());
        mapSqlParameterSource.addValue("name", newSalon.getName());
        mapSqlParameterSource.addValue("address", newSalon.getAddress());
        mapSqlParameterSource.addValue("phone_number", newSalon.getPhone_number());
        mapSqlParameterSource.addValue("days_open", newSalon.getDays_open());
        String SQL = "INSERT INTO salons (salon_id, salon_name, salon_address, salon_phone_number, salon_days_open) VALUES (:id, :name, :address, :phone_number, :days_open)";
        namedParameterJdbcTemplate.update(SQL, mapSqlParameterSource);
    }

    @Override
    public List<Salon> findAllByName(String name) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", name);
        return namedParameterJdbcTemplate.query("SELECT * FROM salons WHERE salon_name = :name", mapSqlParameterSource, new SalonRowMapper());
    }

    @Override
    public Optional<Salon> findSalonById(int id) {
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("id", id);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("SELECT * FROM salons WHERE salon_id = :id", mapSqlParameterSource, new SalonRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateOneOnOpenDays(int id, String newOpenDays) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        mapSqlParameterSource.addValue("newOpenDays", newOpenDays);

        String SQL = "UPDATE salons SET salon_days_open = :newOpenDays WHERE salon_id = :id";
        namedParameterJdbcTemplate.update(SQL, mapSqlParameterSource);
    }

    @Override
    public void deleteById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        namedParameterJdbcTemplate.update("DELETE FROM salons WHERE salon_id = :id", mapSqlParameterSource);
    }

    @Override
    public Optional<List<Salon>> findSalonsOnDaysOpen(int daysOpen) {
        return Optional.empty();
    }
}
