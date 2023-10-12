package ie.sean;

import ie.sean.dao.salon.SalonDao;
import ie.sean.dao.salon.SalonDaoImpl;
import ie.sean.entities.salon.Salon;
import ie.sean.services.salon.SalonService;
import ie.sean.services.salon.SalonServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test");

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

//        SalonDao salonDao = applicationContext.getBean(SalonDaoImpl.class);
        SalonService salonService = applicationContext.getBean(SalonServiceImpl.class);

        System.out.println("Print the number of rows in the salon table.");
        System.out.println("============================================");
        System.out.println(salonService.count());
        System.out.println();

        System.out.println("Print all the row in the salon table");
        System.out.println("====================================");
        salonService.findAll().forEach(System.out::println);
        System.out.println();

        System.out.println("Create a new salon providing all data");
        System.out.println("=====================================");
        System.out.println("Original salon table");
        System.out.println("--------------------");
        salonService.findAll().forEach(System.out::println);
        Salon salon = new Salon(6, "Salon 6", "Cork, Ireland", 1234567890, "1111100");
        salonService.insertOne(salon);
        System.out.println("Updated salon table");
        System.out.println("-------------------");
        salonService.findAll().forEach(System.out::println);
        System.out.println();

        System.out.println("Get all the salons by name");
        System.out.println("==========================");
        String salonName = "Burger King";
        System.out.println("Input: " + salonName);
        salonService.findAllByName(salonName).forEach(System.out::println);
        System.out.println();

        System.out.println("Get salon by its primary key (id)");
        System.out.println("=================================");
        int salonId = 7;
        System.out.println("Input: " + Integer.toString(salonId));
        System.out.println(salonService.findSalonById(salonId));
        System.out.println();

        System.out.println("List all those salons open 7 days a week.");
        System.out.println("=========================================");
        salonService.findSalonsOnDaysOpen(7).forEach(System.out::println);
        System.out.println();
    }
}