package io.hotdogger.login.savegame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Optional<Reservation> reservation = Optional.ofNullable(null);

        try {
            reservation = reservationRepository.findById(id);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }

        if (reservation.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            return reservation.get();
        }
    }
}
