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
@FacesValidator("pincodeValidator")
public class PinCodeValidator implements Validator{

    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String password = value.toString();
        
        if(password.length()!=4){
            throw new ValidatorException(new
                FacesMessage("A PIN kódnak 4 karakterből kell állnia!"));
        }
    }
    
}
