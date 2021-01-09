package io.hotdogger.login.savegame;

import io.hotdogger.login.exceptions.ResourceNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaveGameServiceImpl implements SaveGameService{

    private final Logger logger = LoggerFactory.getLogger(SaveGameServiceImpl.class);

    @Autowired
    private SaveGameRepo saveGameRepo;

    @Override
    public List<SaveGame> getAll() {
        List<SaveGame> saveGameList = new ArrayList<>();

        try {
            saveGameList = saveGameRepo.findAll();
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }

        return saveGameList;
    }

    @Override
    public SaveGame getById(Long id) {
        Optional<SaveGame> saveGame = Optional.ofNullable(null);

        try {
            saveGame = saveGameRepo.findById(id);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }

        if (saveGame.isPresent()) {
            return saveGame.get();
        } else {
            throw new ResourceNotFound();
        }
    }

    @Override
    public SaveGame createSaveGame(SaveGame saveGame) {
        //set the address entity (child) to parent entity (customer)
        newCustomer.getAddress().setCustomer(newCustomer);

        try {
            //checks if an existing customer in the repository have the same email as newCustomer
            Customer existingCustomer = customersRepo.findByEmail(newCustomer.getEmail());
            if (existingCustomer == null) {//if no customer have the same email as new customer, add

                //encode the password of the newCustomer and save it in DB
                newCustomer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
                return customersRepo.save(newCustomer);
            } else {
                throw new ConflictException();
            }
        } catch (ConflictException e) {
            throw new ConflictException(
                    "The email you provided already exists for another customer, please enter another email");
        } catch (Exception e) {
            throw new ServiceUnavailable(e);
        }
    }
}
