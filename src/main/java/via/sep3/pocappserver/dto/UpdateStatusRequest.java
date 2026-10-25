package via.sep3.pocappserver.dto;

import jakarta.validation.constraints.NotNull;

//why record here = short way to write a class, whose only job is to hold data (java generates it)
//record is equivalent to writing full class with constructor + setter + getters
//DTO is just data being passed, so we dont need logic = best use of record
//noone can modify it

//body for PATCH /api/shipments/{id}/status = (e.g IN_TRANSIT)
public record UpdateStatusRequest(
    @NotNull ShipmentStatus status //the field must be present in JSON
)
{ }
