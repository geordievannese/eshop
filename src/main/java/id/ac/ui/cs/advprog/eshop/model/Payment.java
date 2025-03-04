package id.ac.ui.cs.advprog.eshop.model;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
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

        String[] statusList = {"REJECTED", "SUCCESS"};
        String[] methodList = {"Voucher Code", "Bank Transfer"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))
                || Arrays.stream(methodList).noneMatch(item -> (item.equals(method)))) {
            throw new IllegalArgumentException();
        } else {
            this.id = id;
            this.method = method;
            this.status = status;
            this.paymentData = paymentData;
        }
    }

    public void setStatus(String status) {
        String[] statusList = {"REJECTED", "SUCCESS"};
        if (Arrays.stream(statusList).noneMatch(item -> (item.equals(status)))) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }
}

