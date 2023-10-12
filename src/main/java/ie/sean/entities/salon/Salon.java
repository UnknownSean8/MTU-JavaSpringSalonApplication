package ie.sean.entities.salon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.IntStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salon {
    private int id;
    private String name;
    private String address;
    private int phone_number;
    private String days_open;

    public String[] daysOpenInString(String days_open) {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        char[] seperatedDaysOpen = days_open.toCharArray();
        System.out.println(Arrays.toString(seperatedDaysOpen));
        return IntStream.range(0, seperatedDaysOpen.length).filter(i -> (seperatedDaysOpen[i] == '1')).mapToObj(i -> daysOfWeek[i]).toArray(String[]::new);
    }

    @Override
    public String toString() {
        return "Salon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone_number=" + phone_number +
                ", days_open=" + Arrays.toString(daysOpenInString(days_open)) +
                '}';
    }
}
