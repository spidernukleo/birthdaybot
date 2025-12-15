package nukleo.birthdaybot.util;


import lombok.AllArgsConstructor;
import nukleo.birthdaybot.model.Bday;
import nukleo.birthdaybot.service.LogService;
import nukleo.birthdaybot.service.TelegramService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class BirthdayTask {

    private final CoreManager coreManager;

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Rome")
    private void checkTodaysBirthdays(){
        LocalDate today = LocalDate.now();
        coreManager.getGroupBdays().forEach((chatId, bdays) -> {
            List<Bday> todays = bdays.stream().filter(b -> b.getDate().getMonthValue() == today.getMonthValue() && b.getDate().getDayOfMonth() == today.getDayOfMonth()).toList();
            if (!todays.isEmpty()) {
                coreManager.sendBirthdayMessage(chatId, todays);
            }
        });
    }

}
