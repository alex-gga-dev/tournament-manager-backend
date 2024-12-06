package agarcia.microservices.tournamentmanager.tournament_manager.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;

@Constraint(validatedBy = TeamNameValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTeamName {
    String message() default "Team name contains invalid characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}