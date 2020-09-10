package io.hotdogger.login.security;

import io.catalyte.training.tbm.customers.Customer;
import io.catalyte.training.tbm.customers.CustomersDao;
import io.catalyte.training.tbm.exceptions.ServiceUnavailable;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A custom Spring service class that inherits from UserDetailsService class, it contains an
 * important method: loadUserByUsername.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomersDao customersDao;

    /**
     * Spring Frameworks calls this method to load a user by username or, in our case, load a customer
     * by its email. Spring expects this method to load the customer from the customer database.
     *
     * @param s -type String- the email a customer entered to login
     * @return -type MyUserPrincipal- the custom MyUserPrincipal entity that contains the customer
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            Customer customer = customersDao.findByEmail(s);
            if (customer != null) {
                return new MyUserPrincipal(customer);
            } else {
                throw new UsernameNotFoundException(s);
            }
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Incorrect Email");
        } catch (Exception e) {
            throw new ServiceUnavailable(e);
        }
    }
}

/**
 * A customer UserPrincipal class/entity that implements UserDetails to use the Customer entity
 */
class MyUserPrincipal implements UserDetails {

    private Customer customer;

    public MyUserPrincipal(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}