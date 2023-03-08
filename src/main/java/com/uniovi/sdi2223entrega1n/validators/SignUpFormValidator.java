package com.uniovi.sdi2223entrega1n.validators;

import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SignUpFormValidator implements Validator {
    @Autowired
    private UsersService usersService;
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "Error.empty");
        //Se comprueba que el email tenga un formato valido (ejemplo@ejemplo.ejemplo)
        if (!user.getEmail().matches(".+@.+\\..+")) {
            errors.rejectValue("email", "Error.signup.email.format");}
        //Se comprueba que no se repita el email
        if (usersService.getUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Error.signup.email.duplicate");}
        //Se comprueba la longitud del nombre
        if (user.getName().length() < 5 || user.getName().length() > 24) {
            errors.rejectValue("name", "Error.signup.name.length");}
        //Se comprueba la longitud del apellido
        if (user.getLastName().length() < 5 || user.getLastName().length() > 24) {
            errors.rejectValue("lastName", "Error.signup.lastName.length");}
        //Se comprueba la longitud de la contraseña
        if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
            errors.rejectValue("password", "Error.signup.password.length");}
        //Se comprueba que la contraseña coincida con la confirmación de la contraseña
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm",
                    "Error.signup.passwordConfirm.coincidence");}
    }
}
