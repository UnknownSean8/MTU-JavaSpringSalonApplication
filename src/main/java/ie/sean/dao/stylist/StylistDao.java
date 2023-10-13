package ie.sean.dao.stylist;

import ie.sean.dao.dto.Stylists;
import ie.sean.entities.salon.Salon;
import ie.sean.entities.stylist.Stylist;

import java.util.List;
import java.util.Optional;

public interface StylistDao {
    int count();

    Optional<Stylist> findStylistById(int id);

    void insertOneStylist(Stylist stylist);

    void moveStylist(int stylistId, int newSalonId);

    void deleteStylistById(int id);

    List <Stylist> getAllStylistFromSalon(int salonId);

    List<Stylists> getAllStylistRecord();
}
