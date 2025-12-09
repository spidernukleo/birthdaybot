package nukleo.birthdaybot.service;


import lombok.AllArgsConstructor;
import nukleo.birthdaybot.model.*;
import nukleo.birthdaybot.util.TranslationManager;
import org.springframework.stereotype.Service;
import static nukleo.birthdaybot.model.InlineKeyboardButton.cb;
import static nukleo.birthdaybot.model.InlineKeyboardMarkup.genMenu;

@Service
@AllArgsConstructor
public class MessageService {
    private final TelegramService telegramService;
    private TranslationManager translationManager;

    public void handleMessage(Message message) {
        if(message.getChat().getType().equals("private")) return;
        String text = message.getText() != null ? message.getText().toLowerCase() : null;
        if(text==null) return;
        long chatId = message.getChat().getId();


        if(text.startsWith("/lang")) {
            if(!telegramService.isUserAdmin(chatId, message.getFrom().getId())) return;
            InlineKeyboardMarkup menu = genMenu(
                    new InlineKeyboardButton[]{
                            cb("\uD83C\uDDEE\uD83C\uDDF9", "/langit"),
                            cb("\uD83C\uDDFA\uD83C\uDDF8", "/langen")
                    } //add flags here
            );
            telegramService.sendMessage(chatId, translationManager.getMessage(chatId, "changelang"), menu);
        }

    }
}
