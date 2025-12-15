package nukleo.birthdaybot.service;


import lombok.AllArgsConstructor;
import nukleo.birthdaybot.model.*;
import nukleo.birthdaybot.util.CoreManager;
import nukleo.birthdaybot.util.TranslationManager;
import org.springframework.stereotype.Service;

import java.util.List;

import static nukleo.birthdaybot.model.InlineKeyboardButton.cb;
import static nukleo.birthdaybot.model.InlineKeyboardMarkup.genMenu;

@Service
@AllArgsConstructor
public class MessageService {
    private final TelegramService telegramService;
    private TranslationManager translationManager;
    private CoreManager coreManager;

    public void handleMessage(Message message) {
        if(message.getChat().getType().equals("private")) return;
        String text = message.getText() != null ? message.getText().toLowerCase() : null;
        if(text==null) return;
        long chatId = message.getChat().getId();
        if(chatId==-1001949370621L || chatId==-1003335280114L) { //temp

//            if (text.startsWith("/lang")) {
//                if (!telegramService.isUserAdmin(chatId, message.getFrom().getId())) return;
//                InlineKeyboardMarkup menu = genMenu(
//                        new InlineKeyboardButton[]{
//                                cb("\uD83C\uDDEE\uD83C\uDDF9", "/langit"),
//                                cb("\uD83C\uDDFA\uD83C\uDDF8", "/langen")
//                        } //add flags here
//                );
//                telegramService.sendMessage(chatId, translationManager.getMessage(chatId, "changelang"), menu);
//            }
            if (text.startsWith("/setbday")) {
                if (!telegramService.isUserAdmin(chatId, message.getFrom().getId())) return;
                Message repliedMessage = message.getReply_to_message();
                if (repliedMessage == null || repliedMessage.getFrom().getIs_bot())
                    telegramService.sendMessage(chatId, translationManager.getMessage(chatId, "notreply"));
                else {
                    String[] parts = text.split(" ", 2);
                    if (parts.length > 1) {
                        if (!coreManager.addGroupBday(chatId, repliedMessage.getFrom().getId(), repliedMessage.getFrom().getFirst_name(), parts[1])) {
                            telegramService.sendMessage(chatId, translationManager.getMessage(chatId, "wrongDateFormat"));
                        } else {
                            telegramService.sendMessage(chatId, translationManager.getMessage(chatId, "birthdayAdded"));
                        }
                    } else {
                        telegramService.sendMessage(chatId, translationManager.getMessage(chatId, "noDateArg"));
                    }
                }

            }
//            else if (text.startsWith("/bdays")) {
//                List<Bday> bdays = coreManager.getGroupBdays(chatId);
//                if (!bdays.isEmpty()) {
//                    String msg = "";
//                    for (Bday bday : bdays) {
//                        msg += bday.getFirstName() + " " + bday.getDate() + "\n";
//                    }
//                    telegramService.sendMessage(chatId, msg);
//                } else telegramService.sendMessage(chatId, translationManager.getMessage(chatId, "nobdays"));
//            }
        }
        else return;
    }
}
