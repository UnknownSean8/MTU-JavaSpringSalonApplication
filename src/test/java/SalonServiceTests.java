import ie.sean.Config;
import ie.sean.entities.salon.Salon;
import ie.sean.exceptions.salon.SalonDuplicateKeyException;
import ie.sean.exceptions.salon.SalonMalformedException;
import ie.sean.exceptions.salon.SalonNotFoundException;
import ie.sean.services.salon.SalonService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class SalonServiceTests {
    @Autowired
    SalonService salonService;

    @Test
    @Order(1)
    public void testSalonFindAll() {
        Assertions.assertEquals(5, salonService.findAll().size());
    }

    @Test
    @Order(2)
    public void testCreateANewSalon() {
        int oldCount = salonService.count();

        Salon newSalon = new Salon(oldCount + 1, "Salon " + (oldCount + 1), "Cork, Ireland", 1234567890, "1111100");
        salonService.insertOne(newSalon);

        Assertions.assertEquals(oldCount + 1, salonService.count());
        Assertions.assertNotNull(salonService.findSalonById(oldCount + 1));
        Assertions.assertEquals(oldCount + 1, salonService.findSalonById(oldCount + 1).getId());
    }

    @Test
    @Order(2)
    public void testCreateClashPK() {
        Salon newSalon = new Salon(1, "Salon 1", "Cork, Ireland", 1234567890, "1111100");

        Assertions.assertThrows(SalonDuplicateKeyException.class, () -> salonService.insertOne(newSalon));
    }

    @Test
    @Order(3)
    public void testFindAllSalonByName() {
        String salonName = "Salon 1";

        Assertions.assertNotNull(salonName);
        List<Salon> salons = salonService.findAllByName(salonName);
        salons.forEach((salon) -> Assertions.assertEquals(salonName, salon.getName()));
    }

    @Test
    @Order(3)
    public void testFindAllSalonByEmptyName() {
        String salonName = "";

        Assertions.assertThrows(SalonMalformedException.class, () -> salonService.findAllByName(salonName));
    }

    @Test
    @Order(4)
    public void testFindSalonByPK() {
        int primaryKey = 1;

        Salon salon = salonService.findSalonById(primaryKey);
        Assertions.assertNotNull(salon);

        Assertions.assertEquals(primaryKey, salon.getId());
    }

    @Test
    @Order(4)
    public void testFindSalonByZeroPK() {
        int primaryKey = 0;

        Assertions.assertThrows(SalonMalformedException.class, () -> salonService.findSalonById(primaryKey));
    }

    @Test
    @Order(5)
    public void testUpdateSalonOpenDays() throws SalonMalformedException, SalonNotFoundException {
        int changeId = 1;
//        int oldOpenDays = salonService.findSalonById(changeId).getDays_open();
        String newOpenDays = "1111100";

        salonService.updateOneOnOpenDays(changeId, newOpenDays);

        Assertions.assertEquals(newOpenDays, salonService.findSalonById(changeId).getDays_open());
    }

    @Test
    @Order(5)
    public void testUpdateSalonEmptyOpenDays() {
        int id = 1;
        String openDays = "";

        Assertions.assertThrows(SalonMalformedException.class, () -> salonService.updateOneOnOpenDays(id, openDays));
    }

    @Test
    @Order(5)
    public void testUpdateSalonIdNotFound() {
        int id = 0;
        String openDays = "1111100";

        Assertions.assertThrows(SalonNotFoundException.class, () -> salonService.updateOneOnOpenDays(id, openDays));
    }

    @Test
    @Order(6)
    public void testDeleteSalon() {
        int deleteId = 1;

        // Check if id exists
        Assertions.assertNotNull(salonService.findSalonById(deleteId));

        // Delete Salon
        salonService.deleteById(deleteId);

        // Check
        Assertions.assertThrows(SalonNotFoundException.class, () -> salonService.findSalonById(deleteId));

        // TODO: Delete all that Salon's stylist
    }

    @Test
    @Order(6)
    public void testDeleteSalonIdNotFound() {
        int deleteId = 0;

        Assertions.assertThrows(SalonNotFoundException.class, () -> salonService.deleteById(deleteId));
    }

    @Test
    @Order(7)
    public void testFindSalonThatOpen7DaysAWeek() {
        List<Salon> salons = salonService.findSalonsOnDaysOpen(7);

        salons.forEach((salon) -> Assertions.assertEquals("1111111", salon.getDays_open()));
    }

    @Test
    @Order(7)
    public void testFindSalonThatOpenMoreThan7DaysAWeek() {
        int daysOpen = 8;
        Assertions.assertThrows(SalonMalformedException.class, () -> salonService.findSalonsOnDaysOpen(daysOpen));
    }
}


//    @Nested
//    @DisplayName("Find Methods")
//    class FindMethods {
//        @Autowired
//        SalonService salonService;
//
//        @Test
//        public void testSalonFindAll() {
//            Assertions.assertEquals(5, salonService.findAll().size());
//        }
//    }