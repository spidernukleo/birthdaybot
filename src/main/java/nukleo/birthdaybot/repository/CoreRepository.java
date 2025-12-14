package nukleo.birthdaybot.repository;


import nukleo.birthdaybot.model.Bday;
import nukleo.birthdaybot.model.GroupBday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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
                        userFirstName TEXT,
                        PRIMARY KEY (chatId, userId)
                    )""";
            jdbc.execute(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<GroupBday> getAllGroupBdays(){
        try{
            String sql = "SELECT chatId, userId, dateBday, userFirstName FROM bdays";
            return jdbc.query(sql, (rs, rowNum) -> new GroupBday(
                    rs.getLong("chatId"),
                    new Bday(rs.getLong("userId"), rs.getString("userFirstName"), rs.getObject("dateBday", java.time.LocalDate.class))
            ));
        }
        catch(Exception e){
            e.printStackTrace();
            return List.of();
        }
    }



    public void addBday(Long chatId, Bday bday){
        try{
            String sql = """
                INSERT OR REPLACE INTO bdays (chatId, userId, dateBday, userFirstName) 
                VALUES (?, ?, ?, ?)
            """;
            jdbc.update(sql, chatId, bday.getUserId(), bday.getDate(), bday.getFirstName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


}
