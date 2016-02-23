/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Group;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author DSzabo
 */
@Stateless
public class GroupFacade extends AbstractFacade<Group> implements GroupFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_NetBank-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupFacade() {
        super(Group.class);
    }
    
    @Override
    public Group findByLoginName(String name) {
        List<Group> groupList = findAll();
        for (Group group : groupList) {
            if (group.getUser().getLoginName().equals(name)) {
                return group;
            }
        }
        return null;
    }
    
}
