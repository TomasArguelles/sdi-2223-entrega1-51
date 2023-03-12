package com.uniovi.sdi2223entrega1n.validators;

import com.uniovi.sdi2223entrega1n.entities.Offer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class OfferFormValidation implements Validator {
    private static final String OFFER_TITLE = "title";
    private static final String OFFER_DESCRIPTION = "description";
    private static final String OFFER_PRICE = "price";

    public static final Double MIN_PRICE = 0.0; // Precio mínimo establecido
    public static final Double MAX_PRICE = 100000.0; // Precio máximo establecido

    private static final int TITLE_MAX_LENGTH = 100; // Longitud máxima para el titulo
    private static final int DESCRIPTION_MAX_LENGTH = 500; // Longitud máxima para la descripcion

    @Override
    public boolean supports(Class<?> myClass) {
        return Offer.class.equals(myClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, OFFER_TITLE, "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, OFFER_DESCRIPTION, "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, OFFER_PRICE, "Error.empty");

        // Precio en rango
        if (offer.getPrice() == null || offer.getPrice() < MIN_PRICE || offer.getPrice() > MAX_PRICE) {
            errors.rejectValue(OFFER_PRICE, "error.offer.price.range");
        }

        // Precio es un numero.
        if (offer.getPrice().isNaN()) {
            errors.rejectValue(OFFER_PRICE, "error.offer.price.mustBeNumber");
        }

        // Longitud del titulo
        if (offer.getTitle().length() > TITLE_MAX_LENGTH) {
            errors.rejectValue(OFFER_TITLE, "error.offer.title.maxLength");
        }

        // Longitud de la descripcion
        if (offer.getTitle().length() > DESCRIPTION_MAX_LENGTH) {
            errors.rejectValue(OFFER_DESCRIPTION, "error.offer.description.maxLength");
        }
    }
}
