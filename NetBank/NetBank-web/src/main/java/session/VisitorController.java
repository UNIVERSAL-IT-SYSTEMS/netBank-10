package session;

import javax.inject.Singleton;

/**
 *
 * @author Daniel Szabo
 */
@Singleton

public class VisitorController {
    
    private Integer number = 0;

    public Integer getNumber() {
        return ++number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}