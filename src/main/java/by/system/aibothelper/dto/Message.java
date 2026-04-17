package by.system.aibothelper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Message(@NotNull @NotBlank String text, Chat chat) {}

