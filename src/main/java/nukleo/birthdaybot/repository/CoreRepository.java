package nukleo.birthdaybot.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CoreRepository {

    @Autowired
    private JdbcTemplate jdbc;


    public void createBdaysTable(){
        try{
            String sql = """
                    CREATE TABLE IF NOT EXISTS bdays (
                        chatId BIGINT NOT NULL,
                        userId BIGINT NOT NULL,
                        dateBday DATE NOT NULL,
                        userFirstName TEXT
                    )""";
            jdbc.execute(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
