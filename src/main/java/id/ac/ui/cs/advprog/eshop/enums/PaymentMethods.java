package enums;

import lombok.Getter;

@Getter
public enum PaymentMethods {
    VOUCHER("VOUCHER"),
    BANKTRANSFER("BANKTRANSFER");

    private final String value;

    private PaymentMethods(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        // Normalize the parameter: remove spaces and convert to uppercase.
        String normalizedParam = param.replaceAll("\\s+", "").toUpperCase();
        for (PaymentMethods paymentMethod : PaymentMethods.values()) {
            if (paymentMethod.name().equals(normalizedParam)) {
                return true;
            }
        }
        return false;
    }
}
