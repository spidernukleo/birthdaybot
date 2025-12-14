package nukleo.birthdaybot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bday {
    private Long userId;
    private String firstName;
    private LocalDate date;
}
