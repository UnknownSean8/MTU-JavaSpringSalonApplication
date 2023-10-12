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

    @Autowired
    private SalonDao salonDao;

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
    @SneakyThrows
    public void insertOne(Salon newSalon) {
//        if (newSalon.getName().isBlank())
//            throw new SalonMalformedException("Cartoon object malformed.");
        if (salonDao.findSalonById(newSalon.getId()).isPresent())
            throw new SalonDuplicateKeyException(applicationContext.getMessage("SalonDuplicatePrimaryKeyException", null, Locale.getDefault()));

        salonDao.insertOne(newSalon);
    }

    @Override
    @SneakyThrows
    public List<Salon> findAllByName(String name) {
        if (name.isBlank())
            throw new SalonMalformedException(applicationContext.getMessage("SalonNameBlank", null, Locale.getDefault()));
        return salonDao.findAllByName(name);
    }

    @Override
    @SneakyThrows
    public Salon findSalonById(int id) {
        if (id == 0)
            throw new SalonMalformedException(applicationContext.getMessage("SalonIdZero", null, Locale.getDefault()));
        return salonDao.findSalonById(id).orElseThrow(() -> new SalonNotFoundException(applicationContext.getMessage("SalonIdNotFound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    @SneakyThrows
    public void updateOneOnOpenDays(int id, String newOpenDays) {
        // if newOpenDays is blank
        if (newOpenDays.isBlank())
            throw new SalonMalformedException(applicationContext.getMessage("SalonOpenDaysBlank", null, Locale.getDefault()));
        //check if id object exists
        if (salonDao.findSalonById(id).isEmpty())
            throw new SalonNotFoundException(applicationContext.getMessage("SalonIdNotFound", new Object[]{id}, Locale.getDefault()));

        salonDao.updateOneOnOpenDays(id, newOpenDays);
    }

    // TODO: CRUD return practice, find return Object, save, update and delete return void?
    @Override
    public void deleteById(int id) {
        salonDao.deleteById(id);
    }

    @Override
    public List<Salon> findSalonsOnDaysOpen(int daysOpen) {
        return this.findAll().stream().filter(salon -> {
            return ((int) (salon.getDays_open().chars().filter(c -> c == '1').count())) >= daysOpen;
        }).collect(Collectors.toList());
    }
}
