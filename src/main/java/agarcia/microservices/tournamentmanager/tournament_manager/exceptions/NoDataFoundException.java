package agarcia.microservices.tournamentmanager.tournament_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException(String message) {
        super(message);
    }

}
