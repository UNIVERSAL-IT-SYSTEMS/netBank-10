package validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@FacesValidator("phoneNumberValidator")
public class PhoneNumberValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        String string = value.toString();

        if (string.matches("\\+36-(20|30|70)-\\d{3}-\\d{4}")
                || string.matches("\\+36-(20|30|70)-\\d{3}-\\d{2}-\\d{2}")
                || string.matches("06-(20|30|70)-\\d{3}-\\d{4}") 
                || string.matches("06(20|30|70)\\d{7}") 
                || string.matches("\\+36(20|30|70)\\d{7}")
                || string.matches("(20|30|70)-\\d{7}") 
                || string.matches("\\((30|20|70)\\) \\d{3} \\d{4}")) {
            
        }else{
            FacesMessage msg
                    = new FacesMessage("Telefonszám Hiba!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
