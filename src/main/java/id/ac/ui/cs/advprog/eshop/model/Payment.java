package id.ac.ui.cs.advprog.eshop.model;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;

import enums.PaymentStatus;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    HashMap<String, String> paymentData;

    public Payment(String id, String method, String status, HashMap<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        } else {
            this.id = id;
            this.method = method;
            this.status = status;
            this.paymentData = paymentData;
        }
    }

    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }
}

