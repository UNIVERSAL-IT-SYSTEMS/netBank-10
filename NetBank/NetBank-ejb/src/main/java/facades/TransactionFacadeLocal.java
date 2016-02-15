package facades;

import entities.Transaction;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Daniel Szabo
 */
@Local
public interface TransactionFacadeLocal {

    void create(Transaction transaction);

    void edit(Transaction transaction);

    void remove(Transaction transaction);

    Transaction find(Object id);

    List<Transaction> findAll();

    List<Transaction> findRange(int[] range);

    int count();
    
}
