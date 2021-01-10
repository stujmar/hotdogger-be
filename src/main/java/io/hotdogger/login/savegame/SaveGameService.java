package io.hotdogger.login.savegame;

import java.util.List;

public interface SaveGameService {

    SaveGame getById(Long id);

    List<SaveGame> getAll();

    SaveGame createSaveGame(SaveGame saveGame);

    SaveGame updateSaveGame(Long id, SaveGame saveGame);
}
