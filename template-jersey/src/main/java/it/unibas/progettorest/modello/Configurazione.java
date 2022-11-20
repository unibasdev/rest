package it.unibas.progettorest.modello;

import it.unibas.progettorest.enums.EStrategiaPersistenza;

public class Configurazione {

    private static Configurazione singleton = new Configurazione();

    public static Configurazione getInstance() {
        return singleton;
    }

//    private EStrategiaPersistenza strategiaDb = EStrategiaPersistenza.DB_HIBERNATE;
    private EStrategiaPersistenza strategiaDb = EStrategiaPersistenza.DB_MOCK;
    
    private Configurazione() {
    }

    public static Configurazione getSingleton() {
        return singleton;
    }

    public static void setSingleton(Configurazione singleton) {
        Configurazione.singleton = singleton;
    }

    public EStrategiaPersistenza getStrategiaDb() {
        return strategiaDb;
    }

    public void setStrategiaDb(EStrategiaPersistenza strategiaDb) {
        this.strategiaDb = strategiaDb;
    }


}
