package agarcia.microservices.tournamentmanager.tournament_manager.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;

@Constraint(validatedBy = PlayerNameValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPlayerName {
    String message() default "Name contains invalid characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}