package io.hotdogger.login.savegame;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static io.hotdogger.login.constants.StringConstants.REQUIRED_FIELD;

@Entity
@Table(name = "saves")
public class SaveGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Dog Count" + REQUIRED_FIELD)
    private Integer dogCount;

    public SaveGame() {
    }

    public SaveGame(Long id, @NotNull(message = "Dog Count" + REQUIRED_FIELD) Integer dogCount) {
        this.id = id;
        this.dogCount = dogCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDogCount() {
        return dogCount;
    }

    public void setDogCount(Integer dogCount) {
        this.dogCount = dogCount;
    }

    @Override
    public String toString() {
        return "SaveGame{" +
                "id=" + id +
                ", dogCount=" + dogCount +
                '}';
    }
}
