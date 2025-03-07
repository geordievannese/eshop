package id.ac.ui.cs.advprog.eshop.service;

import enums.PaymentMethods;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, HashMap<String, String> paymentData) {
        Payment payment = new Payment(order.getId(), method, paymentData);
        paymentRepository.add(payment);
        return payment;
    }

    @Override
    public Payment getPayment(String id) {
        return paymentRepository.getPaymentById(id);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        paymentRepository.add(payment); // e.g. "upsert" or "save"
        // Also update the order:
        orderRepository.save(orderRepository.findById(payment.getId()));
        return payment;
    }

    @Override
    public Payment pay(Payment payment, Order order) {
        Payment savedPayment;

        if (payment.getMethod().equals(PaymentMethods.VOUCHER.getValue())) {
            // VOUCHER path
            String voucherCode = payment.getPaymentData().get("voucherCode");
            if (voucherCode.length() != 16) {
                savedPayment = setStatus(payment, "REJECTED");
                return savedPayment;
            }
            if (!voucherCode.startsWith("ESHOP")) {
                savedPayment = setStatus(payment, "REJECTED");
                return savedPayment;
            }
            // Count digits
            int digitCount = 0;
            for (int i = 0; i < voucherCode.length(); i++) {
                if (Character.isDigit(voucherCode.charAt(i))) {
                    digitCount++;
                }
            }
            if (digitCount != 8) {
                savedPayment = setStatus(payment, "REJECTED");
                return savedPayment;
            }
            // If we pass all checks, success
            savedPayment = setStatus(payment, "SUCCESS");
            return savedPayment;

        } else {
            // BANK TRANSFER or other method
            String bankName = payment.getPaymentData().get("bankName");
            String referenceCode = payment.getPaymentData().get("referenceCode");
            if (bankName == null || bankName.isEmpty() ||
                    referenceCode == null || referenceCode.isEmpty()) {
                savedPayment = setStatus(payment, "REJECTED");
            } else {
                savedPayment = setStatus(payment, "SUCCESS");
            }
        }
        return savedPayment;
    }
}
