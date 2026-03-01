package com.example.codingexercise.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;


class ErrorResponseDtoTest {

    @Test
    void givenBuilderWithAllFields_whenBuild_thenAllFieldsAreSet() {
        // Given
        LocalDateTime time = LocalDateTime.of(2025, 1, 15, 10, 30);

        // When
        ErrorResponseDto dto = ErrorResponseDto.builder()
                .apiPath("/api/test")
                .errorCode(400)
                .errorMessage("Bad request")
                .errorTime(time)
                .build();

        // Then
        assertEquals("/api/test", dto.getApiPath());
        assertEquals(400, dto.getErrorCode());
        assertEquals("Bad request", dto.getErrorMessage());
        assertEquals(time, dto.getErrorTime());
    }

    @Test
    void givenAllArgsConstructor_whenCreate_thenAllFieldsAreSet() {
        // Given
        LocalDateTime time = LocalDateTime.now();

        // When
        ErrorResponseDto dto = new ErrorResponseDto("/path", 500, "Error", time);

        // Then
        assertEquals("/path", dto.getApiPath());
        assertEquals(500, dto.getErrorCode());
        assertEquals("Error", dto.getErrorMessage());
        assertEquals(time, dto.getErrorTime());
    }

    @Test
    void givenTwoDtosWithSameFields_whenEquals_thenTheyAreEqual() {
        // Given
        LocalDateTime time = LocalDateTime.of(2025, 1, 1, 0, 0);
        ErrorResponseDto a = ErrorResponseDto.builder()
                .apiPath("/a")
                .errorCode(400)
                .errorMessage("msg")
                .errorTime(time)
                .build();
        ErrorResponseDto same = ErrorResponseDto.builder()
                .apiPath("/a")
                .errorCode(400)
                .errorMessage("msg")
                .errorTime(time)
                .build();
        ErrorResponseDto different = ErrorResponseDto.builder()
                .apiPath("/b")
                .errorCode(404)
                .errorMessage("other")
                .errorTime(time)
                .build();

        // When / Then
        assertEquals(a, same);
        assertNotEquals(a, different);
        assertEquals(a.hashCode(), same.hashCode());
    }

    @Test
    void givenErrorResponseDto_whenEqualsWithNullOrOtherClass_thenReturnsFalse() {
        // Given
        ErrorResponseDto dto = ErrorResponseDto.builder().errorCode(400).build();

        // When / Then
        assertEquals(false, dto.equals(null));
        assertEquals(false, dto.equals("string"));
    }
}
