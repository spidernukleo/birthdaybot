package nukleo.birthdaybot.util;


import lombok.AllArgsConstructor;
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

}
