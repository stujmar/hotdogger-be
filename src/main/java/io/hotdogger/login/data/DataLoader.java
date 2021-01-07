package io.hotdogger.login.data;

import io.hotdogger.login.players.Player;
import io.hotdogger.login.players.PlayerRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * The DataLoader class loads sample/test data into the database every time the API is ran. It is
 * used for testing.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final Logger logger = LogManager.getLogger(DataLoader.class);

    /**
     * Connects the class to the Customers Repository to load sample customers into the database.
     */
    @Autowired
    private PlayerRepo playerRepo;

    /**
     * Connects to the PasswordEncoder to encode the passwords and save it to the database
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * A method that runs loading methods (to load sample data into the database).
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading sample data...");

        loadSampleCustomers();
    }

    /**
     * A method that contains the sample customers that will be added into the database once it is
     * run.
     */
    private void loadSampleCustomers() {

        Player player1 = new Player("Nancy", "Drew", "detective@gmail.com", "theBestDetective111",
                "312-232-1234", "Image1");

        player1.setPassword(passwordEncoder.encode(player1.getPassword())); //encode the password
        playerRepo.save(player1);

        Player player2 = new Player("Hugo", "Knight", "artist@yahoo.com", "drawingAlot",
                "413-299-3420", "Image2");

        player2.setPassword(passwordEncoder.encode(player2.getPassword()));
        playerRepo.save(player2);
    }

    private void loadSampleSaves(){
        
    }
}