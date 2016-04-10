/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Topic;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dani
 */
@Local
public interface TopicFacadeLocal {

    void create(Topic topic);

    void edit(Topic topic);

    void remove(Topic topic);

    Topic find(Object id);

    List<Topic> findAll();

    List<Topic> findRange(int[] range);
    
    List<Topic> findByUser(Long userId);
    
    List<Topic> getAll();

    int count();
    
}
