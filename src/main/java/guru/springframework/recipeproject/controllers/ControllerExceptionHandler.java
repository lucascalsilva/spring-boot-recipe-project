package guru.springframework.recipeproject.controllers;

import guru.springframework.recipeproject.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        return handleException(exception, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception exception){
        return handleException(exception, HttpStatus.BAD_REQUEST);
    }

    private ModelAndView handleException(Exception exception, HttpStatus httpStatus){
        log.error("Handling {}! Message: {}", exception.getClass().getSimpleName(), exception.getMessage());
        ModelAndView mav = new ModelAndView();

        mav.addObject("exception", exception);
        mav.addObject("httpStatus", httpStatus);
        mav.setViewName("error_page");

        return mav;
    }
}
