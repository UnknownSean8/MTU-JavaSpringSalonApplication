package ie.sean.entities.stylist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stylist {
    private int id;
    private String name;
    private int phone_number;
    private int salary;
    private int salon_id;
}
