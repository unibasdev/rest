package it.unibas.mediapesata.persistenza;

import it.unibas.mediapesata.modello.Configurazione;
import it.unibas.mediapesata.persistenza.hibernate.DAOEsameHibernate;
import it.unibas.mediapesata.persistenza.hibernate.DAOStudenteHibernate;
import it.unibas.mediapesata.persistenza.hibernate.DAOUtenteHibernate;
import it.unibas.mediapesata.persistenza.mock.DAOEsameMock;
import it.unibas.mediapesata.persistenza.mock.DAOStudenteMock;
import it.unibas.mediapesata.persistenza.mock.DAOUtenteMock;

import static it.unibas.mediapesata.enums.EStrategiaPersistenza.DB_HIBERNATE;

public class DAOFactory {

    private static final DAOFactory singleton = new DAOFactory();

    public static DAOFactory getInstance() {
        return singleton;
    }

    private final IDAOUtente daoUtente;
    private final IDAOStudente daoStudente;
    private final IDAOEsame daoEsame;

    private DAOFactory() {
        if (Configurazione.getInstance().getStrategiaDb().equals(DB_HIBERNATE)) {
            daoUtente = new DAOUtenteHibernate();
            daoStudente = new DAOStudenteHibernate();
            daoEsame = new DAOEsameHibernate();
        } else {
            daoUtente = new DAOUtenteMock();
            daoStudente = new DAOStudenteMock();
            daoEsame = new DAOEsameMock();
        }
    }

    public IDAOUtente getDAOUtente() {
        return daoUtente;
    }

    public IDAOStudente getDAOStudente() {
        return daoStudente;
    }

    public IDAOEsame getDAOEsame() {
        return daoEsame;
    }

}
