package nukleo.birthdaybot.util;


import lombok.AllArgsConstructor;
import nukleo.birthdaybot.repository.CoreRepository;
import nukleo.birthdaybot.service.TelegramService;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CoreManager {

    private TranslationManager translationManager;
    private CoreRepository coreRepository;
    private TelegramService telegramService;


    public void createBdaysTable(){
        coreRepository.createBdaysTable();
    }

}
