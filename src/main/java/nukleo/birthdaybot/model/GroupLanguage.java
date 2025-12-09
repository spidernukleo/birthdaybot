package nukleo.birthdaybot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupLanguage {
    private Long chatId;
    private String activeLangCode;
}
