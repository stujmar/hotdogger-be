package io.hotdogger.login.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Profile {

    @Id
    private long id;

    public Profile() {
    }

}
