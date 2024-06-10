package co.istad.idata.exception;

import co.istad.idata.base.BasedError;
import co.istad.idata.base.BasedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidationException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BasedErrorResponse handleValidationErrors(MethodArgumentNotValidException exception) {

        BasedError<List<?>> basedError = new BasedError<>();

        List<Map<String, Object>> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("field: ", fieldError.getField());
                    error.put("reason", fieldError.getDefaultMessage());
                    errors.add(error);
                });

        basedError.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        basedError.setDescription(errors);

        return new BasedErrorResponse(basedError);
    }
}
