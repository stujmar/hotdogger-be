package io.hotdogger.login.savegame;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/saves")
public class SaveGameController {

    private final Logger logger = LoggerFactory.getLogger(SaveGameController.class);

    @Autowired
    private SaveGameService saveGameService;

    /**
     * This method retrieves all saved games from the database
     *
     * @return a collection of reservations and 200 status code
     * @throws Exception
     */
    @GetMapping
    @ApiOperation("Retrieve all reservations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SaveGame.class)
    })
    public ResponseEntity<List<SaveGame>> getAllReservations() {
        logger.info(" Get all request received");
        return new ResponseEntity<>(saveGameService.getAll(), HttpStatus.OK);
    }

    /**
     * This method retrieves a single SaveGame from the database
     *
     * @return a SaveGame by the id provided and 200 status code
     * @throws Exception
     */
    @GetMapping("/{id}")
    @ApiOperation("Retrieve a SaveGame by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SaveGame.class)
    })
    public ResponseEntity<SaveGame> getSaveGameById(@PathVariable Long id) {
        logger.info(" Get all request received");
        return new ResponseEntity<>(saveGameService.getById(id), HttpStatus.OK);
    }

    /**
     * This method creates a new SaveGave and saves it to the database
     *
     * @param saveGame to be created
     * @return created SaveGame and 201 status code
     * @throws Exception
     */
    @PostMapping
    @ApiOperation("Add a single SaveGame by the reservation info provided")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = SaveGame.class),
            @ApiResponse(code = 400, message = "Invalid request", response = ResponseStatusException.class)
    })
    public ResponseEntity<SaveGame> createSaveGame(
            @Valid @RequestBody SaveGame saveGame) {
        logger.info(" Post request received");
        return new ResponseEntity<>(saveGameService.createSaveGame(saveGame),
                HttpStatus.CREATED);
    }

    /**
     * This method updates an existing SaveGame record
     *
     * @param id          of the SaveGame to be updated
     * @param saveGame updated SaveGame information
     * @return updated SaveGame and 200 status code
     * @throws Exception
     */
    @PutMapping("/{id}")
    @ApiOperation("Update a single SaveGame by id and SaveGame info provided")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SaveGame.class),
            @ApiResponse(code = 400, message = "Invalid request", response = ResponseStatusException.class)
    })
    public ResponseEntity<SaveGame> updateSaveGame(@Valid @RequestBody SaveGame saveGame,
                                                         @PathVariable Long id) {
        logger.info(" Put request received");
        return new ResponseEntity<>(saveGameService.updateSaveGame(id, saveGame),
                HttpStatus.OK);
    }


}
