package com.mycompany;

import entities.Account;
import entities.Address;
import entities.User;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import services.AccountService;
import services.UserService;

/**
 *
 * @author Daniel Szabo
 */

@Path("test")
@RequestScoped
public class TestResource {

    public TestResource() {
    }
    
    @Inject 
    UserService userService;
    
    @Inject
    AccountService accountService;

    @GET
    @Path("init")
    public Response init() {
        Address address1 = new Address();
        address1.setCity("Pecs");
        address1.setZipCode(7632);
        address1.setStreet("Kodolanyi");
        address1.setHouseNumber(25);
        
        User user1 = new User();
        user1.setName("Szabo Daniel");
        user1.setLoginName("sz.daniel94");
        user1.setPassword("fgh132");
        user1.setAddress(address1);
        user1.setEmail("dszabo@grepton.hu");
        user1.setDateOfBirth(new Date(1994, 4, 27));
        user1.setPhoneNumber("+36204216511");
        userService.addUser(user1);
        
        Account account1 = new Account();
        account1.setAccountNumber(1111);
        account1.setBalance(100000);
        account1.setUser(user1);
        user1.getAccountList().add(account1);
        accountService.addAccount(account1);
        userService.updateUser(account1.getUser());
        
        return Response.ok().build();
    }
    
    @GET
    @Produces("application/json")
    @Path("listUser")
    public List<User> getUsers() {
        return userService.getUserList();
    }
    
    @GET
    @Produces("application/json")
    @Path("listAccount")
    public List<Account> getAccounts() {
        return accountService.getAccountList();
    }
    
    
}
