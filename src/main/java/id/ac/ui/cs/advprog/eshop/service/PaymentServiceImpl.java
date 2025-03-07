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

    // What is the code smell and how it will affect code maintainability.
    // orderRepository.save(orderRepository.findById(payment.getId()));
    // Possible NullPointerException: If findById() returns an empty result, save() will throw an error.

    //Refactoring steps that you suggest.
    //Use Optional.ifPresent() to safely update the order only if it exists.


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
        if (payment.getMethod().equals(PaymentMethods.VOUCHER.getValue())) {
            // Validate voucher
            String voucherCode = payment.getPaymentData().get("voucherCode");
            if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
                return setStatus(payment, "REJECTED");
            }

            // Count digits in voucher code
            long digitCount = voucherCode.chars().filter(Character::isDigit).count();
            if (digitCount != 8) {
                return setStatus(payment, "REJECTED");
            }

            return setStatus(payment, "SUCCESS");
        }

        // BANK TRANSFER or other method
        String bankName = payment.getPaymentData().get("bankName");
        String referenceCode = payment.getPaymentData().get("referenceCode");

        return setStatus(payment, (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty())
                ? "REJECTED" : "SUCCESS");
    }
}
