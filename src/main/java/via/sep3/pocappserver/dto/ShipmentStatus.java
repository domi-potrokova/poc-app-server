package via.sep3.pocappserver.dto;

//the only allowed statuses, if a client sends other value JSON parsing fails + Spring returns 400
public enum ShipmentStatus
{
  CREATED,
  IN_TRANSIT,
  OUT_FOR_DELIVERY,
  DELIVERED,
  CANCELLED
}
