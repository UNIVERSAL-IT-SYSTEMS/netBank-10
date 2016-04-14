package validators;

import javax.faces.application.FacesMessage;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Daniel Szabo
 */
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator{

    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String password = value.toString();
        
        if(password.length()<8){
            throw new ValidatorException(new
                FacesMessage("A jelszónak legalább 8 karakterből kell állnia!"));
        }
    }
    
}
