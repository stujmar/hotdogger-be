package io.hotdogger.login.players;

import io.catalyte.training.tbm.exceptions.ResourceNotFound;
import io.catalyte.training.tbm.exceptions.ServiceUnavailable;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller class for the Customers domain. It helps in taking in requests from the client and
 * sending back responses.
 */
@RestController
@RequestMapping("/customers")
public class PlayerController {

    private final Logger logger = LogManager.getLogger(PlayerController.class);

    /**
     * Sets up the dependency for the Customer Controller which is dependent on Customer Service.
     */
    @Autowired
    private PlayerService playerService;

    /**
     * Takes in a POST request and calls the Customer service to perform the createCustomer method,
     * and returns a response and CREATED 201 status code.
     *
     * @param newCustomer -type Customer- a JSON object of the new Customer passed by the client.
     * @return a response of the new created Customer (in JSON), and a status code of 201.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // for swagger to only display the responses listed here
    @ApiOperation("Add a new customer into a database.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Player.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<Player> addPlayer(@Valid @RequestBody Player newPlayer) {
        logger.info("Post player request received " + newPlayer.toString());

        return new ResponseEntity<>(playerService.createPlayer(newPlayer), HttpStatus.CREATED);
    }

    /**
     * Takes in a GET request and calls the Customer service to perform the getAllCustomers method,
     * which returns a response and 0K 200 status code.
     *
     * @return a response which contains a list of all the customers (in JSON), and a 200 status code.
     */
    @GetMapping
    @ApiOperation("Gets back all players from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Player.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<List<Player>> getAllPlayers() {
        logger.info("Get all players request received");

        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }

    /**
     * Takes in a GET request and calls the Player service to perform the getPlayerById method,
     * which returns a response and 0K 200 status code.
     *
     * @param playerId -type Long- the id of an existing Player object.
     * @return a response that is the Player (in JSON) specified from user, and a 200 status code.
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("Gets a player from the database by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Player.class),
            @ApiResponse(code = 404, message = "Resource Not Found", response = ResourceNotFound.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") Long playerId) {
        logger.info("Get player by id request received: player id: " + playerId);

        return new ResponseEntity<>(playerService.getPlayerById(playerId), HttpStatus.OK);
    }

    /**
     * Takes in a PUT request and calls the Player service to perform updatePlayerById method,
     * then returns a response and 0K 200 status code.
     *
     * @param playerId      -type Long- the id of an existing Player object.
     * @param updatedPlayer -type Player- the Player object that contains the updated
     *                        data/version sent by the client.
     * @return a response that is the updated version of an existing Player (in JSON), and a 200
     * status code.
     */
    @PutMapping(value = "/{id}")
    @ApiOperation("Updates a player in the database by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Player.class),
            @ApiResponse(code = 404, message = "Resource Not Found", response = ResourceNotFound.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<Player> updatePlayerById(@PathVariable("id") Long playerId,
                                                       @Valid @RequestBody Player updatedPlayer) {
        logger.info("Update player by id request received: player id: " + playerId);

        return new ResponseEntity<>(playerService.updatePlayerById(playerId, updatedPlayer),
                HttpStatus.OK);
    }

    /**
     * Takes in a DELETE request and calls the Player service to perform deletePlayerById method,
     * and returns a NO CONTENT status code if performed successfully.
     *
     * @param playerId -type Long- the id of an existing Player the client wants to delete.
     * @return a NO CONTENT 204 status code when the Player has been deleted.
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // for swagger to display only the status codes listed here
    @ApiOperation("Deletes a player in the database by its id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content", response = Player.class),
            @ApiResponse(code = 404, message = "Resource Not Found", response = ResourceNotFound.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ServiceUnavailable.class),
            @ApiResponse(code = 503, message = "Service Unavailable", response = ServiceUnavailable.class)
    })
    public ResponseEntity<Player> deletePlayerById(@PathVariable("id") Long playerId) {
        logger.info("Delete player by id request received: player id: " + playerId);

        playerService.deletePlayerById(playerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}