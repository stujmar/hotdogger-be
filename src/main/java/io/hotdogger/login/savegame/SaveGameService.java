package io.hotdogger.login.savegame;

import java.util.List;

public interface SaveGameService {

    static Object getById(Long id) {
    }

    List<SaveGame> getAll();

}
