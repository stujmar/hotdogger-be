package io.hotdogger.login.savegame;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static io.hotdogger.login.constants.StringConstants.REQUIRED_FIELD;

@Entity
@Table(name = "saves")
public class SaveGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Dog Count" + REQUIRED_FIELD)
    private Integer dogCount;

    public SaveGame() {

    }

    public SaveGame(Long id, @NotBlank(message = "Dog Count" + REQUIRED_FIELD) Integer dogCount) {
        this.id = id;
        this.dogCount = dogCount;
    }
}
