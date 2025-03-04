package id.ac.ui.cs.advprog.eshop.service;

import enums.PaymentMethods;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;

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
        paymentRepository.add(payment);
        orderRepository.save(orderRepository.findById(payment.getId()));
        return payment;
    }
    @Override
    public Payment pay(Payment payment, Order order) {
        Payment savedPayment;
        if (payment.getMethod().equals(PaymentMethods.VOUCHER.getValue())) {
            int digitCount = 0;
            for (int i=0; i < payment.getPaymentData().get("voucherCode").length(); i++) {
                if (Character.isDigit(payment.getPaymentData().get("voucherCode").charAt(i))) {
                    digitCount += 1;
                }
            }
            String voucherCode = payment.getPaymentData().get("voucherCode");
            if (voucherCode.length() != 16) {
                savedPayment =  setStatus(payment, "REJECTED");
                return savedPayment;
            }
            if (!voucherCode.startsWith("ESHOP")) {
                savedPayment = setStatus(payment, "REJECTED");
                return savedPayment;
            }
            if (digitCount != 8) {
                savedPayment = setStatus(payment, "REJECTED");
                return savedPayment;
            }

            savedPayment = setStatus(payment, "SUCCESS");
            return savedPayment;

        } else {
            if (payment.getPaymentData().get("bankName").isEmpty() || payment.getPaymentData().get("referenceCode").isEmpty()){
                savedPayment = setStatus(payment, "REJECTED");
            } else {
                savedPayment = setStatus(payment, "SUCCESS");
            }
        }
        return savedPayment;
    }

}