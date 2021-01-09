package io.hotdogger.login.savegame;

import io.hotdogger.login.exceptions.ConflictException;
import io.hotdogger.login.exceptions.ResourceNotFound;
import io.hotdogger.login.exceptions.ServiceUnavailable;
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
//        newCustomer.getAddress().setCustomer(newCustomer);

        try {
            //checks if an existing customer in the repository have the same email as newCustomer
            Optional<SaveGame> existingSaveGame = saveGameRepo.findById(saveGame.getId());
            if (existingSaveGame == null) {//if no customer have the same email as new customer, add
                return saveGameRepo.save(saveGame);
            } else {
                throw new ConflictException();
            }
        } catch (ConflictException e) {
            throw new ConflictException(
                    "Duplicate SaveGame id");
        } catch (Exception e) {
            throw new ServiceUnavailable(e);
        }
    }
}
