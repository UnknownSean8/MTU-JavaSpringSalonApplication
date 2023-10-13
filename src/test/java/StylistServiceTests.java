import ie.sean.Config;
import ie.sean.dao.dto.Stylists;
import ie.sean.entities.stylist.Stylist;
import ie.sean.exceptions.stylist.StylistDuplicateKeyException;
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

    @Test
    @Order(1)
    public void testStylistCount() {
        Assertions.assertEquals(3, stylistService.count());
    }

    @Test
    @Order(2)
    public void testInsertOneStylist() throws StylistDuplicateKeyException, StylistNotFoundException {
        int oldCount = stylistService.count();

        Stylist newStylist = new Stylist(oldCount + 1, "Stylist " + (oldCount + 1), 12345673, 10000, 1);
        stylistService.insertOneStylist(newStylist);

        Assertions.assertEquals(oldCount + 1, stylistService.count());
        Assertions.assertNotNull(stylistService.findStylistById(oldCount + 1));
        Assertions.assertEquals(oldCount + 1, stylistService.findStylistById(oldCount + 1).getId());
    }

    // TODO: Need tests
    @Test
    @Order(2)
    public void testMoveStylist() {
        int stylistId = 1;
        int newSalonId = 2;

        stylistService.getAllStylistRecord().forEach(System.out::println);
        stylistService.moveStylist(stylistId, newSalonId);
        System.out.println();
        stylistService.getAllStylistRecord().forEach(System.out::println);
    }

    // TODO: Need tests
    @Test
    @Order(3)
    public void testGetAllStylistFromSalon() {
        int salonId = 2;

        stylistService.getAllStylistFromSalon(salonId).forEach(System.out::println);
    }

    // TODO: Need tests
    @Test
    @Order(4)
    public void testGetAllStylistRecord() {
        List<Stylists> stylists =  stylistService.getAllStylistRecord();
    }

    // TODO: Need tests
    @Test
    @Order(5)
    public void testGetAverageSalaryOfOneSalon() {
        int salonId = 1;

        double averageSalary = stylistService.averageSalaryOfOneSalon(salonId);
        System.out.println(averageSalary);
    }

    @Test
    @Order(5)
    public void testDeleteStylist() throws StylistNotFoundException {
        int deleteId = 1;

        Assertions.assertNotNull(stylistService.findStylistById(deleteId));

        Stylist deletedStylist = stylistService.deleteStylistById(deleteId);

        // Check stylist deleted
        Assertions.assertThrows(StylistNotFoundException.class, () -> stylistService.findStylistById(deleteId));

        // Check deleted stylist is returned
        Assertions.assertNotNull(deletedStylist);
    }
}
