package it.unibas.mediapesata;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationPath("/api/v1")
public class Applicazione extends Application {

    private final static Logger logger = LoggerFactory.getLogger(Applicazione.class);

    public Applicazione() {
        logger.info("Applicazione creata...");
    }
    
    
}
