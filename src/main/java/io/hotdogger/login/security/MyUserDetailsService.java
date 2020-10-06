package io.hotdogger.login.security;

import io.hotdogger.login.exceptions.ServiceUnavailable;
import java.util.Collection;

import io.hotdogger.login.players.Player;
import io.hotdogger.login.players.PlayerRepo;
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
    private PlayerRepo playerRepo;

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
            Player player = playerRepo.findByEmail(s);
            if (player != null) {
                return new MyUserPrincipal(player);
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

    private Player player;

    public MyUserPrincipal(Player player) {
        this.player = player;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return player.getPassword();
    }

    @Override
    public String getUsername() {
        return player.getEmail();
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