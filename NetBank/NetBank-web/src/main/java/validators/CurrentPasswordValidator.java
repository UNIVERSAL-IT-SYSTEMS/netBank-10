package validators;

import controllers.UserController;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import services.UserService;

/**
 *
 * @author Daniel Szabo
 */
@ManagedBean(name="currentPasswordValidator")
@RequestScoped
public class CurrentPasswordValidator implements Validator {

    @ManagedProperty(value="#{userController}")
    private UserController userController;
    
    @Inject
    private UserService userService;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    
        try {
            String input = value.toString();
            input = userService.encode(input);
            String current = userController.getUser().getPassword();
            
            if(!input.equals(current)){
                throw new ValidatorException(new
                        FacesMessage("Hibás jelszó!"));
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CurrentPasswordValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
    
}
