package it.unibas.mediapesata.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JWTUtil {

    private final static Logger logger = LoggerFactory.getLogger(JWTUtil.class);
    private static final String SECRET_KEY = "---chiave-segreta---";

    public static String generaToken(String email) {
        try {
            int dayToExpire = 1;
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer("unibas.it")
                    .withSubject(email);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, dayToExpire);
            builder.withExpiresAt(c.getTime());
            return builder.sign(algorithm);
        } catch (JWTCreationException ex) {
            logger.warn("Token non valido {}", ex.getMessage(), ex);
            throw new IllegalArgumentException("Token non valido " + ex);
        }
    }

    public static String verificaToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); 
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("unibas.it")
                    .build(); 
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException ex) {
            logger.warn("Token non valido {}", ex.getMessage(), ex);
            throw new IllegalArgumentException("Token non valido " + ex);
        }
    }

}
