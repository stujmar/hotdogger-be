package io.hotdogger.login.players;

import io.hotdogger.login.exceptions.ConflictException;
import io.hotdogger.login.exceptions.ResourceNotFound;
import io.hotdogger.login.exceptions.ServiceUnavailable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A service class that implements the methods from the PlayerService interface. It provides
 * services for the Player Entity and contain the basic CRUD methods to add, get, update, and
 * delete a player.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    /**
     * Sets up the dependency for CustomerServiceImpl class where the Customer service is dependent of
     * the Customer repository. It lets service connect to the CustomerDao.
     */
    @Autowired
    private PlayerRepo playerRepo;

    /**
     * Connects to the PasswordEncoder class to encode the customer password for creating and updating
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Adds a new Customer into the Customer repository.
     *
     * @param newPlayer -type Customer- a new customer object added by the user/client.
     * @return the newly added Customer object.
     */
    @Override
    public Player createPlayer(Player newPlayer) {
        //set the address entity (child) to parent entity (customer)
//        newPlayer.getAddress().setCustomer(newPlayer); //players don't have addresses.

        try {
            //checks if an existing customer in the repository have the same email as newCustomer
            Player existingPlayer = playerRepo.findByEmail(newPlayer.getEmail());
            if (existingPlayer == null) {//if no customer have the same email as new customer, add

                //encode the password of the newCustomer and save it in DB
                newPlayer.setPassword(passwordEncoder.encode(newPlayer.getPassword()));
                return playerRepo.save(newPlayer);
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

    /**
     * Gets a list of players or all the players from the Player repository.
     *
     * @return an array of all the players in the database.
     */
    @Override
    public List<Player> getAllPlayers() {
        try {
            return playerRepo.findAll();
        } catch (Exception e) {
            throw new ServiceUnavailable(e);
        }
    }

    /**
     * Gets a Player from the customer repository based on the id specified from the user.
     *
     * @param playerId -type Long- the id of an existing player.
     * @return a Player object from the database.
     */
    @Override
    public Player getPlayerById(Long playerId) {
        try {
            Player player = playerRepo.findById(playerId).orElse(null);
            if (player != null) {
                return player;
            }
        } catch (Exception e) {
            throw new ServiceUnavailable(e);
        }
        throw new ResourceNotFound("Cannot find the player by the specified id: " + playerId);
    }

    /**
     * Updates an existing Player from the Player repository.
     *
     * @param playerId      -type Long- the id of an existing player, is required, so the API
     *                        knows which player the client wants to update.
     * @param updatedPlayer -type Player- a new or updated version of an existing Player.
     * @return the updated Player object.
     */
    @Override
    public Player updatePlayerById(Long playerId, Player updatedPlayer) {
        //set child entity to parent entity NO ADDRESSES, ADDRESS THIS LATER
//        updatedCustomer.getAddress().setCustomer(updatedCustomer);

        try {
            if (playerRepo.existsById(playerId)) {
                //checks if there is an existing other player with same email
                Player existingPlayer = playerRepo
                        .findByEmail(updatedPlayer.getEmail());

                //if email changed & doesn't match OR //email not updated, match existing player
                if (existingPlayer == null || existingPlayer.getId().equals(updatedPlayer.getId())) {

                    //encode the password of the newPlayer and save it in DB
                    updatedPlayer.setPassword(passwordEncoder.encode(updatedPlayer.getPassword()));
                    return playerRepo.save(updatedPlayer);
                } else {
                    throw new ConflictException();
                }
            } else {
                throw new ResourceNotFound();
            }
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("Cannot find the customer by the specified id: " + playerId);
        } catch (ConflictException e) {
            throw new ConflictException(
                    "The email you provided already exists for another customer, please enter another email");
        } catch (Exception e) {
            throw new ServiceUnavailable(e);
        }
    }

    /**
     * Deletes an existing Customer from the Customer repository.
     *
     * @param customerId -type Long- the id of an existing Customer that the user wants to delete.
     */
    @Override
    public void deleteCustomerById(Long customerId) {
        try {
            customersRepo.deleteById(customerId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFound("Customer doesn't exist by the specified id: " + customerId);
        } catch (Exception e) {
            throw new ServiceUnavailable(e);
        }
    }
}