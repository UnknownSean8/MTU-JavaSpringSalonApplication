package ie.sean.services.stylist;

import ie.sean.dao.dto.Stylists;
import ie.sean.dao.salon.SalonDao;
import ie.sean.dao.stylist.StylistDao;
import ie.sean.entities.stylist.Stylist;
import ie.sean.exceptions.salon.SalonNotFoundException;
import ie.sean.exceptions.stylist.StylistDuplicateKeyException;
import ie.sean.exceptions.stylist.StylistMalformedException;
import ie.sean.exceptions.stylist.StylistNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class StylistServiceImpl implements StylistService {

    private final StylistDao stylistDao;
    private final SalonDao salonDao;

    public StylistServiceImpl(StylistDao stylistDao, SalonDao salonDao) {
        this.stylistDao = stylistDao;
        this.salonDao = salonDao;
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public int count() {
        return stylistDao.count();
    }

    @Override
    public Stylist findStylistById(int id) throws StylistNotFoundException {
        return stylistDao.findStylistById(id).orElseThrow(() -> new StylistNotFoundException(applicationContext.getMessage("errors.stylistIdNotFound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public Stylist insertOneStylist(Stylist stylist) throws StylistDuplicateKeyException, StylistMalformedException {
        if (stylistDao.findStylistById(stylist.getId()).isPresent())
            throw new StylistDuplicateKeyException(applicationContext.getMessage("errors.stylistDuplicatePrimaryKeyException", null, Locale.getDefault()));
        if (stylist.getName().isBlank())
            throw new StylistMalformedException(applicationContext.getMessage("errors.stylistNameBlank", null, Locale.getDefault()));
        if (stylist.getSalary() < 0)
            throw new StylistMalformedException(applicationContext.getMessage("errors.stylistSalaryNegative", null, Locale.getDefault()));

        stylistDao.insertOneStylist(stylist);
        return stylist;
    }

    @Override
    public void moveStylist(int stylistId, int newSalonId) throws StylistNotFoundException, SalonNotFoundException {
        if (stylistDao.findStylistById(stylistId).isEmpty())
            throw new StylistNotFoundException(applicationContext.getMessage("errors.stylistIdNotFound", new Object[]{stylistId}, Locale.getDefault()));

        if (salonDao.findSalonById(newSalonId).isEmpty())
            throw new SalonNotFoundException(applicationContext.getMessage("errors.salonIdNotFound", new Object[]{newSalonId}, Locale.getDefault()));

        stylistDao.moveStylist(stylistId, newSalonId);
    }

    @Override
    public Stylist deleteStylistById(int id) throws StylistNotFoundException {
        if (stylistDao.findStylistById(id).isEmpty())
            throw new StylistNotFoundException(applicationContext.getMessage("errors.stylistIdNotFound", new Object[]{id}, Locale.getDefault()));

        Stylist retStylist = this.findStylistById(id);
        stylistDao.deleteStylistById(id);

        return retStylist;
    }

    @Override
    public double averageSalaryOfOneSalon(int salonId) throws SalonNotFoundException {
        if (salonDao.findSalonById(salonId).isEmpty())
            throw new SalonNotFoundException(applicationContext.getMessage("errors.salonIdNotFound", new Object[]{salonId}, Locale.getDefault()));
        double total = 0;

        List<Stylist> stylists = stylistDao.getAllStylistFromSalon(salonId);

        for (Stylist stylist : stylists) {
            total += stylist.getSalary();
        }
        return total / stylists.size();
    }

    @Override
    public List<Stylist> getAllStylistFromSalon(int salonId) throws SalonNotFoundException{
        if (salonDao.findSalonById(salonId).isEmpty())
            throw new SalonNotFoundException(applicationContext.getMessage("errors.salonIdNotFound", new Object[]{salonId}, Locale.getDefault()));

        return stylistDao.getAllStylistFromSalon(salonId);
    }

    @Override
    public List<Stylists> getAllStylistRecord() {
        return stylistDao.getAllStylistRecord();
    }
}
