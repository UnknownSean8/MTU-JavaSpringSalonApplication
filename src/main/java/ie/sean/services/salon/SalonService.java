package ie.sean.services.salon;

import ie.sean.entities.salon.Salon;
import ie.sean.exceptions.salon.SalonDuplicateKeyException;
import ie.sean.exceptions.salon.SalonMalformedException;
import ie.sean.exceptions.salon.SalonNotFoundException;

import java.util.List;
import java.util.Optional;

public interface SalonService {
    int count();

    List<Salon> findAll();

    void insertOne(Salon newSalon) throws SalonDuplicateKeyException, SalonMalformedException;

    List<Salon> findAllByName(String name) throws SalonMalformedException;

    Salon findSalonById(int id) throws SalonMalformedException, SalonNotFoundException;

    void updateOneOnOpenDays(int id, String newOpenDays) throws SalonMalformedException, SalonNotFoundException;

    void deleteById(int id) throws SalonNotFoundException;

    List<Salon> findSalonsOnDaysOpen(int daysOpen) throws SalonMalformedException;
}
