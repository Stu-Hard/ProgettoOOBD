package utility;

import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.TextInputControl;

public class Validators{

    public RequiredFieldValidator createRequiredValidator(String message){
        RequiredFieldValidator v = new RequiredFieldValidator();
        v.setMessage(message);
        FontAwesomeIcon f = new FontAwesomeIcon();
        f.setIconName("EXCLAMATION_TRIANGLE");
        v.setIcon(f);
        return v;
    }

    public ValidatorBase createEmailValidator(String m){
        ValidatorBase v = new ValidatorBase() {
            @Override
            protected void eval() {
                if (srcControl.get() instanceof TextInputControl) {
                    evalTextInputField();
                }
            }

            private void evalTextInputField() {
                TextInputControl textField = (TextInputControl) srcControl.get();
                if (textField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") || textField.getText().isEmpty()) {
                    hasErrors.set(false);
                } else {
                    hasErrors.set(true);
                }
            }
        };

        v.setMessage(m);
        FontAwesomeIcon f = new FontAwesomeIcon();
        f.setIconName("EXCLAMATION_TRIANGLE");
        v.setIcon(f);
        return v;
    }
}
