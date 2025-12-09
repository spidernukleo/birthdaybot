package nukleo.birthdaybot;

import lombok.AllArgsConstructor;
import nukleo.birthdaybot.service.LogService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import nukleo.birthdaybot.util.TranslationManager;


@Component
@AllArgsConstructor
public class StartupInitializer implements CommandLineRunner {

    private final TranslationManager translationManager;
    private final LogService logService;


    @Override
    public void run(String... args) throws Exception {

        logService.pingOnline();

        System.out.println("✅ Startup complete!");

    }
}
