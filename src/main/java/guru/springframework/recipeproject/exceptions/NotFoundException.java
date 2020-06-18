package guru.springframework.recipeproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(){
        super();
    }

    public NotFoundException(Class entityClass){
        super(entityClass.getSimpleName() + " not found");
    }

    public NotFoundException(Class entityClass, String attribute, String value){
        super(entityClass.getSimpleName() + " not found for attribute "+ attribute + " and value "+ value);
    }

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
