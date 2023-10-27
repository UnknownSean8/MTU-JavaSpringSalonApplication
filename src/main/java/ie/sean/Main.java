package ie.sean;

import ie.sean.entities.salon.Salon;
import ie.sean.services.salon.SalonService;
import ie.sean.services.salon.SalonServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test");

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        System.out.println(applicationContext.getMessage("messages.numberOfRows",null, Locale.FRENCH));

        SalonService salonService = applicationContext.getBean(SalonServiceImpl.class);

        System.out.println(applicationContext.getMessage("messages.numberOfRows", null, Locale.getDefault()));
        System.out.println("============================================");
        System.out.println(salonService.count());
        System.out.println();

        System.out.println(applicationContext.getMessage("messages.printAllRows", null, Locale.getDefault()));
        System.out.println("====================================");
        salonService.findAll().forEach(System.out::println);
        System.out.println();

        System.out.println(applicationContext.getMessage("messages.createANewSalon", null, Locale.getDefault()));
        System.out.println("=====================================");
        System.out.println(applicationContext.getMessage("messages.originalSalonTable", null, Locale.getDefault()));
        System.out.println("--------------------");
        salonService.findAll().forEach(System.out::println);
        Salon salon = new Salon(5, "Salon 6", "Cork, Ireland", "1234567890", "1111100");
        try {
            salonService.insertOne(salon);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(applicationContext.getMessage("messages.updatedSalonTable", null, Locale.getDefault()));
        System.out.println("-------------------");
        salonService.findAll().forEach(System.out::println);
        System.out.println();

        System.out.println(applicationContext.getMessage("messages.getAllSalonsByName", null, Locale.getDefault()));
        System.out.println("==========================");
        String salonName = "Salon 1";
        System.out.println("Input: " + salonName);
        try {
            salonService.findAllByName(salonName).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println(applicationContext.getMessage("messages.getAllSalonByPK", null, Locale.getDefault()));
        System.out.println("=================================");
        int salonId = 6;
        System.out.println("Input: " + salonId);
        try {
            System.out.println(salonService.findSalonById(salonId));
        } catch(Exception e) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println(applicationContext.getMessage("messages.listSalonOpen7Days", null, Locale.getDefault()));
        System.out.println("=========================================");
        try {
            salonService.findSalonsOnDaysOpen(7).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println(applicationContext.getMessage("messages.listSalonOpen7Days", null, Locale.getDefault()));
        System.out.println("=========================================");
        try {
            salonService.findSalonsOnDaysOpen(8).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();
    }
}