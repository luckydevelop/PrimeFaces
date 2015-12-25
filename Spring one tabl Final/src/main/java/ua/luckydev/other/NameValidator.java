package ua.luckydev.other;

import java.util.Map;
        import java.util.regex.Pattern;
        import javax.faces.application.FacesMessage;
        import javax.faces.component.UIComponent;
        import javax.faces.context.FacesContext;
        import javax.faces.validator.FacesValidator;
        import javax.faces.validator.Validator;
        import javax.faces.validator.ValidatorException;
        import org.primefaces.validate.ClientValidator;


@FacesValidator("custom.nameValidator")
public class NameValidator implements Validator, ClientValidator {

    private Pattern pattern;

    private static final String NAME_PATTERN = "^[a-z]{15}$";

    public NameValidator() {
        pattern = Pattern.compile(NAME_PATTERN);
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }

        if(!pattern.matcher(value.toString()).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error",
                    value + " is not a valid email;"));
        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "custom.nameValidator";
    }

}