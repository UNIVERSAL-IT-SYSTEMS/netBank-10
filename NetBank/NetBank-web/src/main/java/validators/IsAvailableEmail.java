package validators;

import controllers.UserController;
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
@ManagedBean(name="isAvailableEmail")
@RequestScoped
public class IsAvailableEmail implements Validator {
    
    
    @Inject
    UserService userService; 
    
    @ManagedProperty(value="#{userController}")
    private UserController userController;
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        
        String userEmail = userController.getUser().getEmail();

        if (!userService.isAvailableEmail(value.toString()) && !userEmail.equals(value.toString()) ) {

            FacesMessage msg
                    = new FacesMessage("A megadott email cím foglalt");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
    
     public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
}
