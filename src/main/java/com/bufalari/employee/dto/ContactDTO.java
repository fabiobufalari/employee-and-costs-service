package com.bufalari.employee.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class ContactDTO {
    @NotNull(message = "Contact type is required")
    private String type; // e.g., "phone", "email"

    @NotNull(message = "Contact value is required")
    private String value; // e.g., phone number or email
}
