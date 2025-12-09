package nukleo.birthdaybot;

import lombok.AllArgsConstructor;
import nukleo.birthdaybot.service.LogService;
import nukleo.birthdaybot.util.CoreManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import nukleo.birthdaybot.util.TranslationManager;


@Component
@AllArgsConstructor
public class StartupInitializer implements CommandLineRunner {

    private final TranslationManager translationManager;
    private final LogService logService;
    private final CoreManager coreManager;


    @Override
    public void run(String... args) throws Exception {

        translationManager.createLanguageTable();
        translationManager.loadAllGroupsLanguages();
        translationManager.loadTranslations();

        coreManager.createBdaysTable();

        logService.pingOnline();

        System.out.println("✅ Startup complete!");

    }
}
