package by.bsu.mmf.devteam.exception.infrastructure;

public class DAOException extends Exception {

    public DAOException(String s) {
        super(s);
    }

    public DAOException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
