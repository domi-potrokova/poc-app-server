package via.sep3.pocappserver.service;

import org.springframework.stereotype.Service;
import via.sep3.pocappserver.dto.CreateShipmentRequest;
import via.sep3.pocappserver.dto.ShipmentResponse;
import via.sep3.pocappserver.dto.ShipmentStatus;
import via.sep3.pocappserver.exception.InvalidTransitionException;
import via.sep3.pocappserver.exception.ShipmentNotFoundException;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//Service registers this class as a Spring bean = creates one instance + injects it wherever its needed
@Service
public class ShipmentServiceImpl implements ShipmentService
{
  //temporary in memory storage, later replace by gRPC calls to C# service
  //no need to change controller
  //concurrentHashMap is thread-safe = server handles requests in parallel
  private final Map<String, ShipmentResponse> store = new ConcurrentHashMap<>();

  //business rule: from each status, which statuses can come next
  private static final Map<ShipmentStatus, Set<ShipmentStatus>> ALLOWED_TRANSITIONS = Map.of(
      ShipmentStatus.CREATED,         Set.of(ShipmentStatus.IN_TRANSIT, ShipmentStatus.CANCELLED),
      ShipmentStatus.IN_TRANSIT,      Set.of(ShipmentStatus.OUT_FOR_DELIVERY),
      ShipmentStatus.OUT_FOR_DELIVERY,Set.of(ShipmentStatus.DELIVERED),
      ShipmentStatus.DELIVERED,       Set.of(), //final state,nothing comes after
      ShipmentStatus.CANCELLED,       Set.of()  //final state
  );

  public ShipmentResponse create(CreateShipmentRequest request)
  {
    ShipmentResponse shipment = new ShipmentResponse(
        UUID.randomUUID().toString(), //generate a unique id
        request.senderName(),
        request.receiverAddress(),
        request.weightKg(),
        ShipmentStatus.CREATED //every new shipment starts as CREATED
    );
    store.put(shipment.id(), shipment);
    return shipment;
  }

  public ShipmentResponse findById(String id)
  {
    ShipmentResponse shipment = store.get(id);
    if (shipment == null)
    {
      throw new ShipmentNotFoundException(id); //404 via GlobalExceptionHandler
    }
    return shipment;
  }

  public ShipmentResponse updateStatus(String id, ShipmentStatus newStatus)
  {
    ShipmentResponse current = findById(id); //throws 404 if it doens't exist
    if (!ALLOWED_TRANSITIONS.get(current.status()).contains(newStatus))
    {
      throw new InvalidTransitionException(current.status(), newStatus); //409
    }
    ShipmentResponse updated = new ShipmentResponse(
        current.id(), current.senderName(), current.receiverAddress(), current.weightKg(), newStatus
    );
    store.put(id, updated);
    return updated;
  }
}
