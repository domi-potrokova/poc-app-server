package via.sep3.pocappserver.service;

import via.sep3.pocappserver.dto.CreateShipmentRequest;
import via.sep3.pocappserver.dto.ShipmentResponse;
import via.sep3.pocappserver.dto.ShipmentStatus;

public interface ShipmentService
{
  ShipmentResponse create(CreateShipmentRequest request);
  ShipmentResponse findById(String id);
  ShipmentResponse updateStatus(String id, ShipmentStatus newStatus);
}
