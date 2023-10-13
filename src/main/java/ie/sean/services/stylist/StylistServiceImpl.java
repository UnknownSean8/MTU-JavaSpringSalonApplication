package ie.sean.services.stylist;

import ie.sean.dao.dto.Stylists;
import ie.sean.dao.stylist.StylistDao;
import ie.sean.entities.salon.Salon;
import ie.sean.entities.stylist.Stylist;
import ie.sean.exceptions.stylist.StylistDuplicateKeyException;
import ie.sean.exceptions.stylist.StylistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class StylistServiceImpl implements StylistService {

    private final StylistDao stylistDao;

    public StylistServiceImpl(StylistDao stylistDao) {
        this.stylistDao = stylistDao;
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public int count() {
        return stylistDao.count();
    }

    @Override
    public Stylist findStylistById(int id) throws StylistNotFoundException {
        return stylistDao.findStylistById(id).orElseThrow(() -> new StylistNotFoundException(applicationContext.getMessage("StylistIdNotFound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public void insertOneStylist(Stylist stylist) throws StylistDuplicateKeyException {
        if (stylistDao.findStylistById(stylist.getId()).isPresent())
            throw new StylistDuplicateKeyException(applicationContext.getMessage("StylistDuplicatePrimaryKeyException", null, Locale.getDefault()));

        stylistDao.insertOneStylist(stylist);
    }

    @Override
    public void moveStylist(int stylistId, int newSalonId) {
        stylistDao.moveStylist(stylistId, newSalonId);
    }

    @Override
    public Stylist deleteStylistById(int id) throws StylistNotFoundException {
        if (stylistDao.findStylistById(id).isEmpty())
            throw new StylistNotFoundException(applicationContext.getMessage("StylistIdNotFound", new Object[]{id}, Locale.getDefault()));

        Stylist retStylist = this.findStylistById(id);
        stylistDao.deleteStylistById(id);

        return retStylist;
    }

    @Override
    public double averageSalaryOfOneSalon(int salonId) {
        double total = 0;

        List<Stylist> stylists = stylistDao.getAllStylistFromSalon(salonId);

        for (Stylist stylist: stylists) {
            total += stylist.getSalary();
        }
        return total / stylists.size();
    }

    @Override
    public List<Stylist> getAllStylistFromSalon(int salonId) {
        return stylistDao.getAllStylistFromSalon(salonId);
    }

    @Override
    public List<Stylists> getAllStylistRecord() {
        return stylistDao.getAllStylistRecord();
    }
}
