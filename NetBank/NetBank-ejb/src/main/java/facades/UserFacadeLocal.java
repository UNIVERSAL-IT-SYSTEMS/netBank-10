package facades;

import entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Daniel Szabo
 */
@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    User findByLoginName(String name);

    int count();

}
