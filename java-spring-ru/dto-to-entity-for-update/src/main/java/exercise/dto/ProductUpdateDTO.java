package exercise.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// BEGIN
@Setter
@Getter
public class ProductUpdateDTO {
    private long id;
    private String title;
    private int price;
}
// END
