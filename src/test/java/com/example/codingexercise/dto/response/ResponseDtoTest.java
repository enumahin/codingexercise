package com.example.codingexercise.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


class ResponseDtoTest {

    @Test
    void givenBuilderWithStatusCodeAndMessage_whenBuild_thenAllFieldsAreSet() {
        // When
        ResponseDto dto = ResponseDto.builder()
                .statusCode(200)
                .statusMessage("OK")
                .build();

        // Then
        assertEquals(200, dto.getStatusCode());
        assertEquals("OK", dto.getStatusMessage());
    }

    @Test
    void givenAllArgsConstructor_whenCreate_thenAllFieldsAreSet() {
        // When
        ResponseDto dto = new ResponseDto(201, "Created");

        // Then
        assertEquals(201, dto.getStatusCode());
        assertEquals("Created", dto.getStatusMessage());
    }

    @Test
    void givenTwoDtosWithSameStatusCodeAndMessage_whenEquals_thenTheyAreEqual() {
        // Given
        ResponseDto a = ResponseDto.builder().statusCode(200).statusMessage("OK").build();
        ResponseDto same = ResponseDto.builder().statusCode(200).statusMessage("OK").build();
        ResponseDto different = ResponseDto.builder().statusCode(404).statusMessage("Not Found").build();

        // When / Then
        assertEquals(a, same);
        assertNotEquals(a, different);
        assertEquals(a.hashCode(), same.hashCode());
    }

    @Test
    void givenResponseDto_whenEqualsWithNullOrOtherClass_thenReturnsFalse() {
        // Given
        ResponseDto dto = ResponseDto.builder().statusCode(200).build();

        // When / Then
        assertEquals(false, dto.equals(null));
        assertEquals(false, dto.equals("string"));
    }
}
