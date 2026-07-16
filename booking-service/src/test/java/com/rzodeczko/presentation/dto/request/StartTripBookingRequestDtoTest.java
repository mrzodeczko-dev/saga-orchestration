package com.rzodeczko.presentation.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class StartTripBookingRequestDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void shouldPassValidationForValidInput() {
        StartTripBookingRequestDto dto = new StartTripBookingRequestDto("Jan", "Mars", new BigDecimal("100"));

        Set<ConstraintViolation<StartTripBookingRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailWhenCustomerNameIsBlank() {
        StartTripBookingRequestDto dto = new StartTripBookingRequestDto("", "Mars", BigDecimal.TEN);

        assertThat(validator.validate(dto)).extracting(v -> v.getPropertyPath().toString())
                .contains("customerName");
    }

    @Test
    void shouldFailWhenDestinationIsBlank() {
        StartTripBookingRequestDto dto = new StartTripBookingRequestDto("Jan", "  ", BigDecimal.TEN);

        assertThat(validator.validate(dto)).extracting(v -> v.getPropertyPath().toString())
                .contains("destination");
    }

    @Test
    void shouldFailWhenAmountIsNull() {
        StartTripBookingRequestDto dto = new StartTripBookingRequestDto("Jan", "Mars", null);

        assertThat(validator.validate(dto)).extracting(v -> v.getPropertyPath().toString())
                .contains("amount");
    }

    @Test
    void shouldFailWhenAmountIsZeroOrNegative() {
        StartTripBookingRequestDto zero = new StartTripBookingRequestDto("Jan", "Mars", BigDecimal.ZERO);
        StartTripBookingRequestDto negative = new StartTripBookingRequestDto("Jan", "Mars", new BigDecimal("-1"));

        assertThat(validator.validate(zero)).isNotEmpty();
        assertThat(validator.validate(negative)).isNotEmpty();
    }
}
