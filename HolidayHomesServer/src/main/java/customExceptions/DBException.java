package customExceptions;

public class DBException extends Exception {

    public DBException(String msg) {
        super("Database exception at " + msg + ".");
    }
}
