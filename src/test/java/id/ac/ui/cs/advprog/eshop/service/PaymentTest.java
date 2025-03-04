package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class PaymentTest {
    private HashMap<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("key", "value");
    }

    @Test
    void testPaymentConstructorWithValidArguments() {
        assertDoesNotThrow(() -> new Payment("1", "Bank Transfer", "SUCCESS", paymentData));
    }

    @Test
    void testPaymentConstructorWithInvalidPaymentMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment("1", "Invalid Method", "SUCCESS", paymentData);
        });
    }

    @Test
    void testPaymentConstructorWithInvalidStatus() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment("1", "Bank Transfer", "INVALID_STATUS", paymentData);
        });
    }

    @Test
    void testPaymentConstructorWithEmptyPaymentData() {
        HashMap<String, String> emptyData = new HashMap<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment("1", "Bank Transfer", "SUCCESS", emptyData);
        });
    }

    @Test
    void testSetStatusWithValidStatus() {
        Payment payment = new Payment("1", "Bank Transfer", "SUCCESS", paymentData);
        assertDoesNotThrow(() -> payment.setStatus("REJECTED"));
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusWithInvalidStatus() {
        Payment payment = new Payment("1", "Bank Transfer", "SUCCESS", paymentData);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("INVALID_STATUS");
        });
    }
}