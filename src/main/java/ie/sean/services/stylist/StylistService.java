package ie.sean.services.stylist;

import ie.sean.dao.dto.Stylists;
import ie.sean.entities.salon.Salon;
import ie.sean.entities.stylist.Stylist;
import ie.sean.exceptions.salon.SalonNotFoundException;
import ie.sean.exceptions.stylist.StylistDuplicateKeyException;
import ie.sean.exceptions.stylist.StylistMalformedException;
import ie.sean.exceptions.stylist.StylistNotFoundException;

import java.util.List;
import java.util.Optional;

public interface StylistService {
    int count();

    Stylist findStylistById(int id) throws StylistNotFoundException;

    Stylist insertOneStylist(Stylist stylist) throws StylistDuplicateKeyException, StylistMalformedException;

    void moveStylist(int stylistId, int newSalonId) throws StylistNotFoundException, SalonNotFoundException;

    Stylist deleteStylistById(int id) throws StylistNotFoundException;

    double averageSalaryOfOneSalon(int salonId) throws SalonNotFoundException;

    List<Stylist> getAllStylistFromSalon(int salonId) throws SalonNotFoundException;

    List<Stylists> getAllStylistRecord();
}
