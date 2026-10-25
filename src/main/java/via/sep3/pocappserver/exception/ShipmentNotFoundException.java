package via.sep3.pocappserver.exception;

//RuntimeException = build in java class, all exception classes inherit everything from parent
//best practice for spring, deals with unchecked exceptions (java doesnt check it, spring/handler deal with them on their own
//no need for me to throw exception
public class ShipmentNotFoundException extends RuntimeException
{
  public ShipmentNotFoundException(String id)
  {
    super("Shipment " + id + " not found");
  }
}
