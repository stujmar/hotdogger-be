package io.hotdogger.login.savegame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveGameRepo extends JpaRepository<SaveGame, Long> {
}
