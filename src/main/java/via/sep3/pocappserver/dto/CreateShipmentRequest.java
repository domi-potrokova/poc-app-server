package via.sep3.pocappserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

//Record = immutable (can't be changed after being created) data class (java generates the constructor and getters
//annotations = validation rules, checked when the controller uses @Valid
public record CreateShipmentRequest(
    @NotBlank String senderName, //not null, not empty, not just spaces
    @NotBlank String receiverAddress,
    @Positive double weightKg //must be greater than 0
)
{ }
