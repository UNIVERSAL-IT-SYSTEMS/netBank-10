package exceptions;

/**
 *
 * @author Daniel Szabo
 */
public class LoggerExeption extends RuntimeException{

    public LoggerExeption(String message) {
        super(message);
    }

    public LoggerExeption(Exception e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
