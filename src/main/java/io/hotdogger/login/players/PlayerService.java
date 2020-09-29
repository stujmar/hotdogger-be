package io.hotdogger.login.players;

import java.util.List;

public interface PlayerService {

    Player createPlayer(Player newPlayer);

    List<Player> getAllPlayers();

    Player getPlayerById(Long playerId);

    Player updatePlayerById(Long playerId, Player updatedPlayer);

    void deletePlayerById(Long playerId);
}
