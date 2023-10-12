package ie.sean.services.salon;

import ie.sean.entities.salon.Salon;

import java.util.List;
import java.util.Optional;

public interface SalonService {
    int count();

    List<Salon> findAll();

    void insertOne(Salon newSalon);

    List<Salon> findAllByName(String name);

    Salon findSalonById(int id);

    void updateOneOnOpenDays(int id, String newOpenDays);

    void deleteById(int id);

    List<Salon> findSalonsOnDaysOpen(int daysOpen);
}
