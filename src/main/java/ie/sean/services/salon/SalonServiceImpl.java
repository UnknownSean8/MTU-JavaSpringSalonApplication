package ie.sean.services.salon;

import ie.sean.dao.salon.SalonDao;
import ie.sean.entities.salon.Salon;
import ie.sean.exceptions.salon.SalonDuplicateKeyException;
import ie.sean.exceptions.salon.SalonMalformedException;
import ie.sean.exceptions.salon.SalonNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SalonServiceImpl implements SalonService {

    private final SalonDao salonDao;

    public SalonServiceImpl(SalonDao salonDao) {
        this.salonDao = salonDao;
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public int count() {
        return salonDao.count();
    }

    @Override
    public List<Salon> findAll() {
        return salonDao.findAll();
    }

    @Override
    public void insertOne(Salon newSalon) throws SalonDuplicateKeyException, SalonMalformedException {
        if (newSalon.getName().isBlank())
            throw new SalonMalformedException(applicationContext.getMessage("errors.salonNameBlank", null, Locale.getDefault()));
        if (newSalon.getAddress().isBlank())
            throw new SalonMalformedException(applicationContext.getMessage("errors.salonAddressBlank", null, Locale.getDefault()));
        if (newSalon.getPhone_number().isEmpty())
            throw new SalonMalformedException(applicationContext.getMessage("errors.salonPhoneNumberBlank", null, Locale.getDefault()));
        if (salonDao.findSalonById(newSalon.getId()).isPresent())
            throw new SalonDuplicateKeyException(applicationContext.getMessage("errors.salonDuplicatePrimaryKeyException", null, Locale.getDefault()));

        salonDao.insertOne(newSalon);
    }

    @Override
    public List<Salon> findAllByName(String name) throws SalonMalformedException {
        if (name.isBlank())
            throw new SalonMalformedException(applicationContext.getMessage("errors.salonNameBlank", null, Locale.getDefault()));
        return salonDao.findAllByName(name);
    }

    @Override
    public Salon findSalonById(int id) throws SalonMalformedException, SalonNotFoundException{
        if (id == 0)
            throw new SalonMalformedException(applicationContext.getMessage("errors.salonIdZero", null, Locale.getDefault()));
        return salonDao.findSalonById(id).orElseThrow(() -> new SalonNotFoundException(applicationContext.getMessage("errors.salonIdNotFound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public void updateOneOnOpenDays(int id, String newOpenDays) throws SalonMalformedException, SalonNotFoundException {
        // if newOpenDays is blank
        if (newOpenDays.isBlank())
            throw new SalonMalformedException(applicationContext.getMessage("errors.salonOpenDaysBlank", null, Locale.getDefault()));
        //check if id object exists
        if (salonDao.findSalonById(id).isEmpty())
            throw new SalonNotFoundException(applicationContext.getMessage("errors.salonIdNotFound", new Object[]{id}, Locale.getDefault()));

        salonDao.updateOneOnOpenDays(id, newOpenDays);
    }

    @Override
    public void deleteById(int id) throws SalonNotFoundException {
        if (salonDao.findSalonById(id).isEmpty())
            throw new SalonNotFoundException(applicationContext.getMessage("errors.salonIdNotFound", new Object[]{id}, Locale.getDefault()));

        salonDao.deleteById(id);
    }

    @Override
    public List<Salon> findSalonsOnDaysOpen(int daysOpen) throws SalonMalformedException{
        if (daysOpen > 7)
            throw new SalonMalformedException(applicationContext.getMessage("errors.salonDaysOpenMoreThanSeven", null, Locale.getDefault()));

        return this.findAll().stream().filter(salon -> {
            return ((int) (salon.getDays_open().chars().filter(c -> c == '1').count())) >= daysOpen;
        }).collect(Collectors.toList());
    }
}
