package it.unibas.progettorest.persistenza;

import it.unibas.progettorest.modello.Configurazione;

import static it.unibas.progettorest.enums.EStrategiaPersistenza.DB_HIBERNATE;

public class DAOFactory {

    private static final DAOFactory singleton = new DAOFactory();

    public static DAOFactory getInstance() {
        return singleton;
    }

    private DAOFactory() {
        if (Configurazione.getInstance().getStrategiaDb().equals(DB_HIBERNATE)) {

        } else {

        }
    }

}
