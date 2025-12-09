package nukleo.birthdaybot.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nukleo.birthdaybot.model.CallBackQuery;
import nukleo.birthdaybot.model.ChatMemberUpdate;
import nukleo.birthdaybot.model.Message;
import nukleo.birthdaybot.service.CallbackService;
import nukleo.birthdaybot.service.ChatMemberService;
import nukleo.birthdaybot.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TelegramController {

    private final ObjectMapper objectMapper;
    private final MessageService messageService;
    private final ChatMemberService chatMemberService;
    private final CallbackService callbackService;


    @GetMapping("/2test")
    public String test() {
        return "Bot is running!";
    }

    @PostMapping("/bot2-webhook")
    public void onTelegramUpdate(@RequestBody Map<String, Object> update) {
        printUpdate(update);

        if(update.containsKey("message")){
            Message message = objectMapper.convertValue(update.get("message"), Message.class);
            messageService.handleMessage(message);
        }
        else if(update.containsKey("my_chat_member")){
            ChatMemberUpdate member = objectMapper.convertValue(update.get("my_chat_member"), ChatMemberUpdate.class);
            chatMemberService.handleChat(member);
        }
        else if(update.containsKey("callback_query")){
            CallBackQuery query = objectMapper.convertValue(update.get("callback_query"), CallBackQuery.class);
            callbackService.handleCallBack(query);
        }

    }


    public void printUpdate(Map<String, Object> update) {
        try {
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(update);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
