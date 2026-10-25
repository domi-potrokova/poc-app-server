package via.sep3.pocappserver.exception;

//applies to all controllers = catches exceptions thrown anywhere in request + turns them into consistent HTTP error responses
//this way controller code stays clean, with no try/catch needed

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //tells Spring: this is not endpoint, it's helper code that applies to all controllers
//Rest means that return values are written as JSON response body
public class GlobalExceptionHandler
{

  //if this exception escapes from a controller, call this method instead of crashing
  //whatever the method returns, here a ProblemDetail, becomes the HTTP response
  @ExceptionHandler(ShipmentNotFoundException.class)
  public ProblemDetail handleNotFound(ShipmentNotFoundException ex)
  {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage()); //404
  }

  @ExceptionHandler(InvalidTransitionException.class)
  public ProblemDetail handleInvalidTransition(InvalidTransitionException ex)
  {
    return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage()); //409
  }

  //validation errors (@Valid failures) and invalid enum values are already turned into 400 by spring
  //its in app.properties, no need to have them here
}
