/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Group;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author DSzabo
 */
@Local
public interface GroupFacadeLocal {

    void create(Group group);

    void edit(Group group);

    void remove(Group group);

    Group find(Object id);

    List<Group> findAll();

    List<Group> findRange(int[] range);
    
    Group findByLoginName(String name);

    int count();
    
}
