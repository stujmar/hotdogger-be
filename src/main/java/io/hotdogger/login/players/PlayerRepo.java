package io.hotdogger.login.players;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersDao extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
