package io.hotdogger.login.savegame;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<>(SaveGameService.getAll(), HttpStatus.OK);
    }

}
