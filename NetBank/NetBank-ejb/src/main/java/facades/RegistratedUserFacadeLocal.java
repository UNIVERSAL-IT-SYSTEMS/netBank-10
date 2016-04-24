/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.RegistratedUser;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Daniel Szabo
 */
@Local
public interface RegistratedUserFacadeLocal {

    void create(RegistratedUser registratedUser);

    void edit(RegistratedUser registratedUser);

    void remove(RegistratedUser registratedUser);

    RegistratedUser find(Object id);

    List<RegistratedUser> findAll();

    List<RegistratedUser> findRange(int[] range);
    
    List<RegistratedUser> getAll();
    
    int count();
    
}
