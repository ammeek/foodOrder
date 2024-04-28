package com.example.dto.request;

import com.example.models.MenuItem;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

@Introspected
@JsonSerialize
@Serdeable.Deserializable
public record CreateOrderRequest(
        @NotNull
        Instant dateOfMeal,
        @NotNull
        @NotBlank
        String mealId,
        @NotNull
        @NotEmpty
        List<MenuItem> menuItems,
        @NotNull
        @NotBlank
        String organizerUid
) {
}
