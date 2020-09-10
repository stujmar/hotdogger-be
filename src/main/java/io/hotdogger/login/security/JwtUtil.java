package io.hotdogger.login.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * A service class used to abstract out all the JWT-related stuff, to create new a JWT given the
 * userDetails (MyUserPrincipal) object and able to read info from an existing JWT (like look up the
 * expiration date from a JWT) which can also validate a JWT.
 */
@Service
public class JwtUtil {

    /**
     * A secret key used for signing and keeping a JWT secure
     */
    private String SECRET_KEY = "D8774902D1876D828B1ECA7D1851AAE168A8FBAB5C01DFCAF03FE9D2847B8B06";

    /**
     * This parses the JWT and gets back the payload part of the JWT where all the claims are at.
     *
     * @param token -type String- the JWT.
     * @return the payload part (type String) of the JWT.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * It takes in a token and uses a claimsResolver in order to figure out what the claims are.
     *
     * @param token          -type String- the JWT.
     * @param claimsResolver -type Function- a specific function that focuses on getting a specific
     *                       claim from JWT.
     * @param <T>            is part of claimsResolver
     * @return whatever that was specify for the claimsResolver function (so this calls it and the
     * return value (claim) depends on the claimsResolver argument).
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gets the username or email from the token (the email is the subject of the JWT).
     *
     * @param token -type String- the JWT.
     * @return a String that is the email of the customer.
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * A private method that creates a JWT based on the info from the UserDetails object passed into
     * it from the generateToken method. This JWT will only contain sub(email) and issued date for the
     * payload.
     *
     * @param claims  -type Map- this is for any additional claims to be pass into the JWT.
     * @param subject -type String- this is the username/email that is set as the subject in the JWT.
     * @return a new JWT (used for generateToken method) that is a string.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // this is empty
                .setSubject(subject) // the customer being authenticated, which is the email/username
                .setIssuedAt(new Date(System.currentTimeMillis())) // the date the JWT is issued
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //sign the JWT with 256 alg. + Secret key
                .compact(); // compact and finish building JWT
    }

    /**
     * It will generate a JWT based off of the UserDetails object. It takes in the UserDetails
     * argument (which is given by UserDetailsService), then takes the username/email from userDetails
     * and pass it into the JWT. Finally, it calls the "create JWT" method which returns a new JWT.
     *
     * @param userDetails -type UserDetails- object containing the customer info when customer login.
     * @return a newly created JWT for the customer who got authenticated (it is a string).
     */
    public String generateToken(UserDetails userDetails) {
        // this is empty, if we need to pass in other data in payload, I'll change it
        Map<String, Object> claims = new HashMap<>();

        //the "getUsername" is technically getting the email, since the email is the username
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Checks if the token belongs to the customer who authenticated before. Will extract the email
     * from the JWT and match it against the userDetails from customer who log-in.
     *
     * @param token       -type String- the JWT created when a customer's authenticated or logged-in.
     * @param userDetails - the userDetails of the customer who logged in.
     * @return a boolean value, true if matching emails, false if not.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()));
    }
}