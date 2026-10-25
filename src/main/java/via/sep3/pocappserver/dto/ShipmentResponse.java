package via.sep3.pocappserver.dto;

//what the API returns to the client (serialized to JSON automatically)
public record ShipmentResponse(
    String id,
    String senderName,
    String receiverAddress,
    double weightKg,
    ShipmentStatus status
)
{ }
