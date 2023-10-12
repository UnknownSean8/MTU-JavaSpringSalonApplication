package ie.sean.dao.salon;

import ie.sean.entities.salon.Salon;

import java.util.List;
import java.util.Optional;

public interface SalonDao {
    int count();

    List<Salon> findAll();

    void insertOne(Salon newSalon);

    List<Salon> findAllByName(String name);

    Optional<Salon> findSalonById(int id);

    void updateOneOnOpenDays(int id, String newOpenDays);

    void deleteById(int id);

    Optional<List<Salon>> findSalonsOnDaysOpen(int daysOpen);
}
