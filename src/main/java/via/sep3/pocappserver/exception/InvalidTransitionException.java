package via.sep3.pocappserver.exception;

import via.sep3.pocappserver.dto.ShipmentStatus;

//thrown when a status change breaks the business rule = DELIVERED -> CREATED
public class InvalidTransitionException extends RuntimeException
{
  public InvalidTransitionException(ShipmentStatus from, ShipmentStatus to)
  {
    super("Cannot change status from " + from + " to " + to);
  }
}
