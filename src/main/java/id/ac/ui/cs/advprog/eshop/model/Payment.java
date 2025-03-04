package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentMethods;
import enums.PaymentStatus;
import lombok.Getter;

import java.util.HashMap;
@Getter
public class Payment {
    String id;
    String method;
    String status;
    HashMap<String, String> paymentData;
    public Payment(String id, String method, HashMap<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!PaymentMethods.contains(method)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
    }
    public Payment(String id, String method, String status, HashMap<String, String> paymentData) {
        if (paymentData.isEmpty() || !PaymentStatus.contains(status) || !PaymentMethods.contains(method)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = status;
    }
    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }
}