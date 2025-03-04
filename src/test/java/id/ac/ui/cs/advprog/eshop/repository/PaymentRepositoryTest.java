package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        HashMap<String, String> paymentData = new HashMap<>();
        paymentData.put("amount", "1000");
        payment = new Payment("1", "Bank Transfer", "SUCCESS", paymentData);
    }

    @Test
    void testAddPayment() {
        Payment savedPayment = paymentRepository.add(payment);
        assertEquals(payment, savedPayment);
    }

    @Test
    void testGetPaymentById() {
        paymentRepository.add(payment);
        Payment foundPayment = paymentRepository.getPaymentById("1");
        assertEquals(payment, foundPayment);
    }

    @Test
    void testGetPaymentByIdNotFound() {
        Payment foundPayment = paymentRepository.getPaymentById("nonexistent");
        assertNull(foundPayment);
    }

    @Test
    void testGetAllPayments() {
        paymentRepository.add(payment);
        List<Payment> payments = paymentRepository.getAllPayments();
        assertFalse(payments.isEmpty());
        assertEquals(1, payments.size());
        assertEquals(payment, payments.get(0));
    }
}