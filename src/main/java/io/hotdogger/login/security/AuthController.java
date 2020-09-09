package io.hotdogger.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller with a new endpoint ("/login") used for logging in or authenticating users to access
 * the API
 */
@RestController
@RequestMapping("/login")
public class AuthController {

    /**
     * Connects to AuthenticationManager to authenticate email and password
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * Connects to MyUserDetailsService to load user details for generating JWT later
     */
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    /**
     * Connects to JwtUtil class to generate a JWT after authenticating user
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Deal with a POST request where the user sends in their crendentials (email and password) and if
     * authentication is successful, returns a JWT back to the user.
     *
     * @param authRequest -type AuthRequest- an object containing the user's email and password
     * @return -type AuthResponse- a response object containing a JWT the user can use to access the
     * API
     * @throws Exception
     */
    @PostMapping
    public ResponseEntity<?> loginCustomer(@RequestBody AuthRequest authRequest)
            throws Exception {
        try {
            // checks/validates if the credentials are correct
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {// if authentication fails, it throws an error, so we catch it here
//      throw new Exception("Incorrect email or password", e);
            throw new BadCredentialsException("Incorrect email or password");
        }

        //if authentication is successful, return JWT, so we need UserDetails to generate a JWT
        final UserDetails userDetails = myUserDetailsService
                .loadUserByUsername(authRequest.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails); //create the token

        return ResponseEntity.ok(new AuthResponse(jwt)); //returns this jwt as a response back to user
    }
}