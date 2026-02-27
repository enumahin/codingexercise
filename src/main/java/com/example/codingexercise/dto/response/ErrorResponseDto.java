package com.example.codingexercise.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * Dto for exchanging HttpError Exception.
 */
@Schema(
        name = "ErrorResponseDto",
        description = "Exception response entity"
)
@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "Endpoint called"
    )
    private String apiPath;

    @Schema(
            description = "Response Status Code",
            minimum = "400",
            maximum = "500"
    )
    private int errorCode;

    @Schema(
            description = "Error Message"
    )
    private String errorMessage;

    @Schema(
            description = "Time the Exception was raised"
    )
    private LocalDateTime errorTime;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponseDto that = (ErrorResponseDto) o;
        return getErrorCode() == that.getErrorCode()
                && Objects.equals(getApiPath(), that.getApiPath())
                && Objects.equals(getErrorMessage(), that.getErrorMessage())
                && Objects.equals(getErrorTime(), that.getErrorTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApiPath(), getErrorCode(), getErrorMessage(), getErrorTime());
    }
}
