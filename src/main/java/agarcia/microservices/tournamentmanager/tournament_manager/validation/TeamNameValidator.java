package agarcia.microservices.tournamentmanager.tournament_manager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TeamNameValidator implements ConstraintValidator<ValidTeamName, String> {

    private static final String NAME_PATTERN = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(NAME_PATTERN);
    }

}
