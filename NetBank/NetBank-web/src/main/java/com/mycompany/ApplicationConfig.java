package com.mycompany;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Daniel Szabo
 */
@javax.ws.rs.ApplicationPath("")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.mycompany.TestResource.class);
       /* resources.add(com.mycompany.LunaParkResource.class);
        resources.add(com.mycompany.MachineResource.class);
        resources.add(com.mycompany.VisitorResource.class);
        resources.add(com.mycompany.exceptions.mappers.CantFindItemExceptionMapper.class);*/
    }

}
