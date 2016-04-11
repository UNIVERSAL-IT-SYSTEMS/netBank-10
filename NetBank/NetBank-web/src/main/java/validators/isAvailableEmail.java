package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import services.UserService;

/**
 *
 * @author Daniel Szabo
 */
@FacesValidator("isAvailableEmail")
public class isAvailableEmail implements Validator {
    
    
    @Inject
    UserService userService; 
    
    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        if (!userService.isAvailableEmail(value.toString())) {

            FacesMessage msg
                    = new FacesMessage("A megadott email cím foglalt");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }

    }
}
