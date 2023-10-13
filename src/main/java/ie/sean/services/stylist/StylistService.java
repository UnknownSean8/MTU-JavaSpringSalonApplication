package ie.sean.services.stylist;

import ie.sean.dao.dto.Stylists;
import ie.sean.entities.salon.Salon;
import ie.sean.entities.stylist.Stylist;
import ie.sean.exceptions.stylist.StylistDuplicateKeyException;
import ie.sean.exceptions.stylist.StylistNotFoundException;

import java.util.List;
import java.util.Optional;

public interface StylistService {
    int count();

    Stylist findStylistById(int id) throws StylistNotFoundException;

    void insertOneStylist(Stylist stylist) throws StylistDuplicateKeyException;

    void moveStylist(int stylistId, int newSalonId);

    Stylist deleteStylistById(int id) throws StylistNotFoundException;

    double averageSalaryOfOneSalon(int salonId);

    List<Stylist> getAllStylistFromSalon(int salonId);

    List<Stylists> getAllStylistRecord();
}
