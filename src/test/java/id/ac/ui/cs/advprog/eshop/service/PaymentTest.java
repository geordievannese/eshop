package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

class PaymentTest {
    private HashMap<String, String> validPaymentData;

    @BeforeEach
    void setUp() {
        validPaymentData = new HashMap<>();
        validPaymentData.put("key", "value");
    }

    @Test
    void testPaymentConstructorWithValidArguments() {
        // "Bank Transfer" is now accepted because the enum normalizes the input.
        assertDoesNotThrow(() -> new Payment("1", "Bank Transfer", PaymentStatus.SUCCESS.name(), validPaymentData));
    }

    @Test
    void testPaymentConstructorWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("1", "Bank Transfer", "INVALID_STATUS", validPaymentData);
        });
    }

    @Test
    void testPaymentConstructorWithEmptyPaymentData() {
        HashMap<String, String> emptyData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("1", "Bank Transfer", PaymentStatus.SUCCESS.name(), emptyData);
        });
    }

    @Test
    void testSetStatusWithValidStatus() {
        Payment payment = new Payment("1", "Bank Transfer", PaymentStatus.SUCCESS.name(), validPaymentData);
        assertDoesNotThrow(() -> payment.setStatus(PaymentStatus.REJECTED.name()));
        assertEquals(PaymentStatus.REJECTED.name(), payment.getStatus());
    }

    @Test
    void testSetStatusWithInvalidStatus() {
        Payment payment = new Payment("1", "Bank Transfer", PaymentStatus.SUCCESS.name(), validPaymentData);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("INVALID_STATUS");
        });
    }
}
