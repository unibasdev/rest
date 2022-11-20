package it.unibas.progettorest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import lombok.extern.slf4j.Slf4j;

@ApplicationPath("/api/v1")
@Slf4j
public class Applicazione extends Application {

    public Applicazione() {
        log.info("Applicazione creata...");
    }

}
