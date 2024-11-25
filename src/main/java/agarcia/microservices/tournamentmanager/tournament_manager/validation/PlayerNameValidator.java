package agarcia.microservices.tournamentmanager.tournament_manager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlayerNameValidator implements ConstraintValidator<ValidPlayerName, String> {

    private static final String NAME_PATTERN = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("Validating: " + value);
        return value != null && value.matches(NAME_PATTERN);
    }

}
