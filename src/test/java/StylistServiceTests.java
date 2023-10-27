import ie.sean.Config;
import ie.sean.dao.dto.Stylists;
import ie.sean.entities.stylist.Stylist;
import ie.sean.exceptions.salon.SalonNotFoundException;
import ie.sean.exceptions.stylist.StylistDuplicateKeyException;
import ie.sean.exceptions.stylist.StylistMalformedException;
import ie.sean.exceptions.stylist.StylistNotFoundException;
import ie.sean.services.stylist.StylistService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class StylistServiceTests {

    @Autowired
    StylistService stylistService;

    /**
     * Test get the correct number of stylist successful
     */
    @Test
    @Order(1)
    public void testStylistCountSuccessful() {
        Assertions.assertEquals(3, stylistService.count());
    }

    /**
     * Test getting all stylist in a particular salon successful
     */
    @Test
    @Order(3)
    public void testGetAllStylistFromOneSalonSuccessful() throws SalonNotFoundException {
        int salonId = 2;

        stylistService.getAllStylistFromSalon(salonId).forEach(System.out::println);
    }

    /**
     * Test getting all stylist in a non-existing salon raises error
     */
    @Test
    @Order(3)
    public void testGetAllStylistFromInvalidSalon() {
        int salonId = 100;

        Assertions.assertThrows(SalonNotFoundException.class, () -> stylistService.getAllStylistFromSalon(salonId));
    }

    /**
     * Test adding a new stylist successful
     */
    @Test
    @Order(2)
    public void testInsertOneStylistSuccessful() throws StylistDuplicateKeyException, StylistNotFoundException, StylistMalformedException {
        int oldCount = stylistService.count();

        Stylist newStylist = new Stylist(oldCount + 1, "Stylist " + (oldCount + 1), "12345673", 10000, 1);
        Stylist insertedStylist = stylistService.insertOneStylist(newStylist);

        Assertions.assertEquals(oldCount + 1, stylistService.count());
        Assertions.assertNotNull(stylistService.findStylistById(oldCount + 1));
        Assertions.assertEquals(oldCount + 1, stylistService.findStylistById(oldCount + 1).getId());

        Assertions.assertEquals(Stylist.class, insertedStylist.getClass());
    }

    /**
     * Test adding a new stylist with duplicate Primary Key raises error
     */
    @Test
    @Order(2)
    public void testInsertOneStylistInvalidPK() {
        int stylistId = 1;

        Stylist newStylist = new Stylist(stylistId, "Stylist", "12345673", 10000, 1);

        Assertions.assertThrows(StylistDuplicateKeyException.class, () -> stylistService.insertOneStylist(newStylist));
    }

    /**
     * Test adding a new stylist with blank name raises error
     */
    @Test
    @Order(2)
    public void testInsertOneStylistInvalidName() {
        int oldCount = stylistService.count();
        Stylist newStylist = new Stylist(oldCount + 1, "", "12345673", 10000, 1);

        Assertions.assertThrows(StylistMalformedException.class, () -> stylistService.insertOneStylist(newStylist));
    }

    /**
     * Test adding a new stylist with negative annual salary raises error
     */
    @Test
    @Order(2)
    public void testInsertOneStylistInvalidSalary() {
        int oldCount = stylistService.count();
        Stylist newStylist = new Stylist(oldCount + 1, "New Stylist", "12345673", -100, 1);

        Assertions.assertThrows(StylistMalformedException.class, () -> stylistService.insertOneStylist(newStylist));
    }

    /**
     * Test moving a stylist successful
     *
     * @throws StylistNotFoundException
     * @throws SalonNotFoundException
     */
    @Test
    @Order(2)
    public void testMoveStylistSuccessful() throws StylistNotFoundException, SalonNotFoundException {
        int stylistId = 1;
        int newSalonId = 2;

        stylistService.moveStylist(stylistId, newSalonId);

        Assertions.assertEquals(stylistService.findStylistById(stylistId).getSalon_id(), newSalonId);
    }

    /**
     * Test moving a non-existing stylist raises error
     */
    @Test
    @Order(2)
    public void testMoveStylistInvalidStylist() {
        int stylistId = 100;
        int newSalonId = 2;

        Assertions.assertThrows(StylistNotFoundException.class, () -> stylistService.moveStylist(stylistId, newSalonId));
    }

    /**
     * Test moving existing stylist to non-existing salon raises error
     */
    @Test
    @Order(3)
    public void testMoveStylistInvalidSalon() {
        int stylistId = 1;
        int newSalonId = 100;

        Assertions.assertThrows(SalonNotFoundException.class, () -> stylistService.moveStylist(stylistId, newSalonId));
    }

    /**
     * Test determining the average salary of stylists in a particular salon successful
     */
    @Test
    @Order(3)
    public void testGetAverageSalaryOfOneSalonSuccessful() throws SalonNotFoundException {
        int salonId = 1;

        double averageSalary = stylistService.averageSalaryOfOneSalon(salonId);

        int totalSalary = 0;
        List<Stylist> stylistList = stylistService.getAllStylistFromSalon(salonId);
        for (Stylist stylist : stylistList) {
            totalSalary += stylist.getSalary();
        }
        float correctAverageSalary = (float) totalSalary / stylistList.size();

        Assertions.assertEquals(correctAverageSalary, averageSalary);
    }

    /**
     * Test determining the average salary of stylists in non-existing salon raises error
     */
    @Test
    @Order(3)
    public void testGetAverageSalaryOfInvalidSalon() {
        int salonId = 100;

        Assertions.assertThrows(SalonNotFoundException.class, () -> stylistService.averageSalaryOfOneSalon(salonId));
    }

    /**
     * Get all stylists along with the name of the salon for which they work successful
     */
    @Test
    @Order(4)
    public void testGetAllStylistRecordSuccessful() {
        List<Stylists> stylists = stylistService.getAllStylistRecord();

        stylists.forEach((record) -> Assertions.assertEquals(Stylists.class, record.getClass()));
    }

    /**
     * Test deleting a stylist successful
     *
     * @throws StylistNotFoundException
     */
    @Test
    @Order(100)
    public void testDeleteStylistSuccessful() throws StylistNotFoundException {
        int stylistId = 1;

        Assertions.assertNotNull(stylistService.findStylistById(stylistId));

        Stylist deletedStylist = stylistService.deleteStylistById(stylistId);

        // Check stylist deleted
        Assertions.assertThrows(StylistNotFoundException.class, () -> stylistService.findStylistById(stylistId));

        // Check deleted stylist is returned
        Assertions.assertNotNull(deletedStylist);
    }

    /**
     * Test deleting a non-existing stylist raises error
     */
    @Test
    @Order(100)
    public void testDeleteStylistInvalidStylist() {
        int stylistId = 100;

        Assertions.assertThrows(StylistNotFoundException.class, () -> stylistService.deleteStylistById(stylistId));
    }
}
