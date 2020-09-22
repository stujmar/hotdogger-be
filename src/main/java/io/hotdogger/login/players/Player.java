package io.hotdogger.login.players;

import static io.hotdogger.login.constants.StringConstants.REQUIRED_FIELD;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "players") //need this to prevent 2 customers tables (customer and customers) in DB
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer's first name" + REQUIRED_FIELD)
    private String firstName;

    @NotBlank(message = "Customer's last name" + REQUIRED_FIELD)
    private String lastName;

    @NotBlank(message = "Customer's email" + REQUIRED_FIELD)
    @Email(message = "Email should be valid, following email format")
    private String email;

    @NotBlank(message = "Password" + REQUIRED_FIELD)
    @Column(length = 80) // for password hashing
    private String password;

    private String phoneNumber;
    private String image;

    public Player() {
    }

    public Player(
            @NotBlank String firstName,
            @NotBlank String lastName,
            @NotBlank String email,
            @NotBlank String password,
            String phoneNumber,
            String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    /**
     * What is going on here?
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(password, customer.password) &&
                Objects.equals(phoneNumber, customer.phoneNumber) &&
                Objects.equals(image, customer.image) &&
                Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, firstName, lastName, email, password, phoneNumber, image);
    }
}