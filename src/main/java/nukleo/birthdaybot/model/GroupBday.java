package nukleo.birthdaybot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupBday {
    private Long chatId;
    private Bday bday;
}
