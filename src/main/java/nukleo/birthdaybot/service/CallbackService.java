package nukleo.birthdaybot.service;


import lombok.AllArgsConstructor;
import nukleo.birthdaybot.model.CallBackQuery;
import nukleo.birthdaybot.model.InlineKeyboardButton;
import nukleo.birthdaybot.model.InlineKeyboardMarkup;
import nukleo.birthdaybot.util.TranslationManager;
import org.springframework.stereotype.Service;

import static nukleo.birthdaybot.model.InlineKeyboardButton.cb;
import static nukleo.birthdaybot.model.InlineKeyboardMarkup.genMenu;


@Service
@AllArgsConstructor
public class CallbackService {

    private TranslationManager translationManager;
    private TelegramService telegramService;

    public void handleCallBack(CallBackQuery query) {
        String data = query.getData();
        Long chatId = query.getMessage().getChat().getId();
        String queryId = query.getId();
        Integer messageId = query.getMessage().getMessage_id();
        long senderId = query.getFrom().getId();
        if (data == null) return;
        else if(data.equals("/langit")) {
            if(!telegramService.isUserAdmin(chatId, senderId)) return;
            translationManager.setLanguage(chatId, "it");
            telegramService.answerCallback(queryId, translationManager.getMessage(chatId, "done"));
            telegramService.deleteMessage(chatId, messageId);
        }
        else if(data.equals("/langen")) {
            if(!telegramService.isUserAdmin(chatId, senderId)) return;
            translationManager.setLanguage(chatId, "en");
            telegramService.answerCallback(queryId, translationManager.getMessage(chatId, "done"));
            telegramService.deleteMessage(chatId, messageId);
        }
    }

}
