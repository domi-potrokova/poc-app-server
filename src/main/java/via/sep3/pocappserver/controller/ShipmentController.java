package via.sep3.pocappserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import via.sep3.pocappserver.dto.CreateShipmentRequest;
import via.sep3.pocappserver.dto.ShipmentResponse;
import via.sep3.pocappserver.dto.ShipmentStatus;
import via.sep3.pocappserver.dto.UpdateStatusRequest;
import via.sep3.pocappserver.service.ShipmentService;

import java.net.URI;

//DIP = the controller depends on the ShipmentService interface, not a concrete class
//so the implementation can be swapped
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/shpments") //base URL: http://8080/api/v1/shipments
@Tag(name = "Shipment management", description = "APIs for creating, finding and updating shipments") //groups endpoints in swagger UI
public class ShipmentController
{
  private final ShipmentService shipmentService;

  @Autowired
  public ShipmentController(ShipmentService shipmentService)
  {
    this.shipmentService = shipmentService;
  }

  @Operation(summary = "Create a shipment", description = "Creates a new shipment with status CREATED")
  @PostMapping
  //@Valid = tells Spring to check validation rules on obj before calling this method (looks at the annotation in DTO @NotBlank...
  //@RequestBody = tells Spring to take the JSON from the HTTP request body and convert it into a Java obj
  public ResponseEntity<ShipmentResponse> createShipment(@Valid @RequestBody CreateShipmentRequest request)
  {
    ShipmentResponse created = shipmentService.create(request); //calls ShipmentServiceImpl.create

    //URI = Uniform Resource Identifier (address of resource, java class that holds address)
    //ServletUriComponentsBuilder = Spring helper that builds a URI from the current request, so i dont have to hard-code localhost:8080
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(created.id()).toUri();
    return ResponseEntity.created(location).body(created); //201 created
  }

  @Operation(summary = "Get shipment by id", description = "Retrieves a shipment's details based on its id")
  @GetMapping("/{id}")
  public ShipmentResponse getShipmentById(@PathVariable String id)
  {
    return shipmentService.findById(id);
  }

  @Operation(summary = "Update shipment status", description = "Allowed: CREATED -> IN_TRANSIT/CANCELLED, IN_TRANSIT -> OUT_FOR_DELIVERY, OUT_FOR_DELIVERY -> DELIVERED")
  @PatchMapping("/{id}/status")
  public ShipmentResponse updateShipmentStatus(@PathVariable String id, @Valid @RequestBody
      UpdateStatusRequest request)
  {
    return shipmentService.updateStatus(id, request.status());
  }
}
