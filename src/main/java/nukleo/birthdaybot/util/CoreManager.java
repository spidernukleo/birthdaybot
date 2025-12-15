package nukleo.birthdaybot.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nukleo.birthdaybot.model.Bday;
import nukleo.birthdaybot.model.GroupBday;
import nukleo.birthdaybot.repository.CoreRepository;
import nukleo.birthdaybot.service.TelegramService;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@AllArgsConstructor
@Component
public class CoreManager {

    private TranslationManager translationManager;
    private CoreRepository coreRepository;
    private TelegramService telegramService;


    @Getter
    private final Map<Long, List<Bday>> groupBdays = new HashMap<>();


    public void createBdaysTable(){
        coreRepository.createBdaysTable();
    }

    public void loadAllGroupBdays(){
        List<GroupBday> bdays = coreRepository.getAllGroupBdays();
        for(GroupBday gb : bdays){
            groupBdays.computeIfAbsent(gb.getChatId(), k -> new ArrayList<>()).add(gb.getBday());
        }
    }

    public List<Bday> getGroupBdays(Long chatid){
        return groupBdays.getOrDefault(chatid, List.of());
    }


    public boolean addGroupBday(Long chatId, Long userId, String userFirstName, String text){
        LocalDate date;
        try{
            date = LocalDate.parse(text.trim());
        }catch(DateTimeParseException e){
            return false;
        }
        List<Bday> bdays = groupBdays.computeIfAbsent(chatId, k -> new ArrayList<>());

        Optional<Bday> existingBday = bdays.stream()
                .filter(b -> b.getUserId().equals(userId))
                .findFirst();

        if(existingBday.isPresent()){
            Bday bday = existingBday.get();
            bday.setDate(date);
            bday.setFirstName(userFirstName);
            coreRepository.addBday(chatId, bday);
        }
        else{
            Bday newBday = new Bday();
            newBday.setFirstName(userFirstName);
            newBday.setUserId(userId);
            newBday.setDate(date);
            coreRepository.addBday(chatId, newBday);
            bdays.add(newBday);
        }

        return true;
    }

    public void removeGroupBday(Long chatId, Bday bday){

    }


    public void sendBirthdayMessage(Long chatId, List<Bday> bdays) {
        if (bdays == null || bdays.isEmpty()) return;

        StringBuilder caption = new StringBuilder("🎉 TANTI AUGURI AMOROSI A ");

        for (int i = 0; i < bdays.size(); i++) {
            Bday b = bdays.get(i);
            // clickable link to Telegram profile
            caption.append("<a href=\"tg://user?id=")
                    .append(b.getUserId())
                    .append("\">")
                    .append(b.getFirstName())
                    .append("</a>");

            // append a comma after every name, even the last one
            caption.append(", ");
        }

        // remove the last comma and space
        if (caption.length() >= 2) caption.setLength(caption.length() - 2);

        // send the animation with caption
        telegramService.sendAnimation(
                chatId,
                "CgACAgQAAyEFAATGzFXyAANjaT928_ODKJd2YY6vI2qwKAYVgXAAAsYGAAI_bKxRhDaVNS8RD-82BA",
                caption.toString()
        );
    }

}
