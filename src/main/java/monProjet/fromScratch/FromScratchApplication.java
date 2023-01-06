package monProjet.fromScratch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class FromScratchApplication {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) throws SQLException {
        context = SpringApplication.run(FromScratchApplication.class, args);
    }
}
