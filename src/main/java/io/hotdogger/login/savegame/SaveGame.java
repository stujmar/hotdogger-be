package io.hotdogger.login.savegame;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class SaveGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
