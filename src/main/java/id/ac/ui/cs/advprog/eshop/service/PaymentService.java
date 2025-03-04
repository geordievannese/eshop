package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.HashMap;
import java.util.List;

public interface PaymentService {
    public Payment addPayment(Order order, String method, HashMap<String, String> paymentData);
    public Payment getPayment(String id);
    public List<Payment> getAllPayments();
    public Payment setStatus(Payment payment, String status);
    public Payment pay(Payment payment, Order order);

}