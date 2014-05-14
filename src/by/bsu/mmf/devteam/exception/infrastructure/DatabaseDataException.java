package by.bsu.mmf.devteam.exception.infrastructure;

import by.bsu.mmf.devteam.exception.data.DataException;

public class DatabaseDataException extends DAOException {

    public DatabaseDataException(String s) {
        super(s);
    }

    public DatabaseDataException(String s, Throwable throwable) {
        super(s, throwable);
    }

}

