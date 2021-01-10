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
        SaveGame postedSaveGame = null;

        try {
            postedSaveGame = saveGameRepo.save(saveGame);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return postedSaveGame;
    }

    @Override
    public SaveGame updateSaveGame(Long id, SaveGame saveGame) {
        Reservation updatedReservation = null;

        try {
            Optional<Reservation> reservationToUpdate = reservationRepository.findById(id);
            if (reservationToUpdate.isEmpty()) {
                throw new ResourceNotFoundException();
            } else {
                updatedReservation = reservationRepository.save(reservation);
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }

        return updatedReservation;
    }
}
