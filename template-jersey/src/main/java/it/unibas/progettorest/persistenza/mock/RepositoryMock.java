package it.unibas.progettorest.persistenza.mock;

public class RepositoryMock extends RepositoryGenericoMock {

    private static final RepositoryMock singleton = new RepositoryMock();

    private RepositoryMock() {
    }

    public static RepositoryMock getInstance() {
        return singleton;
    }

}
