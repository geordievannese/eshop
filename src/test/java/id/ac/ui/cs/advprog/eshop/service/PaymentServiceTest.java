package id.ac.ui.cs.advprog.eshop.service;
import enums.PaymentMethods;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    PaymentServiceImpl paymentService;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    OrderRepository orderRepository;
    List<Payment> payments;
    List<Order> orders = new ArrayList<>();
    @BeforeEach
    void initialize() {
        List<Product> productsList = createProductsList();

        orders = new ArrayList<>();
        addOrder("13652556-012a-4c07-b546-54eb1396d79b", productsList, 1708560000L, "Safira Sudrajat");
        addOrder("7f9e15bb-4b15-42f4-aebc-c3af385fb078", productsList, 1708570000L, "Safira Sudrajat");

        payments = new ArrayList<>();
        addPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethods.BANKTRANSFER.getValue(), "Jank Bago", "1234567890");
        addPayment("7f9e15bb-4b15-42f4-aebc-c3af385fb078", PaymentMethods.VOUCHER.getValue(), "ESHOP-12345678", null);
    }

    private List<Product> createProductsList() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        productList.add(product);
        return productList;
    }

    private void addOrder(String orderId, List<Product> products, long timestamp, String customerName) {
        Order order = new Order(orderId, products, timestamp, customerName);
        orders.add(order);
    }

    private void addPayment(String orderId, String paymentMethod, String bankNameOrVoucher, String referenceCode) {
        HashMap<String, String> paymentData = new HashMap<>();
        if (paymentMethod.equals(PaymentMethods.BANKTRANSFER.getValue())) {
            paymentData.put("bankName", bankNameOrVoucher);
            paymentData.put("referenceCode", referenceCode);
        } else if (paymentMethod.equals(PaymentMethods.VOUCHER.getValue())) {
            paymentData.put("voucherCode", bankNameOrVoucher);
        }
        Payment payment = new Payment(orderId, paymentMethod, paymentData);
        payments.add(payment);
    }
    @Test
    void testPaymentAddition() {
        Payment expectedPayment = payments.get(1);
        Order relatedOrder = orders.get(1);
        when(paymentRepository.add(any(Payment.class))).thenReturn(expectedPayment);
        Payment actualPayment = paymentService.addPayment(relatedOrder, expectedPayment.getMethod(), expectedPayment.getPaymentData());
        verify(paymentRepository, times(1)).add(any(Payment.class));
        assertEquals(expectedPayment.getId(), actualPayment.getId());
        assertEquals(expectedPayment.getMethod(), actualPayment.getMethod());
        assertEquals(expectedPayment.getPaymentData(), actualPayment.getPaymentData());
    }
    @Test
    void shouldRetrievePaymentByIdWhenFound() {
        Payment expectedPayment = payments.get(1);
        when(paymentRepository.getPaymentById(expectedPayment.getId())).thenReturn(expectedPayment);
        Payment actualPayment = paymentService.getPayment(expectedPayment.getId());
        verify(paymentRepository, times(1)).getPaymentById(expectedPayment.getId());
        assertEquals(expectedPayment.getId(), actualPayment.getId());
    }

    @Test
    void shouldThrowExceptionWhenPaymentNotFoundById() {
        String invalidPaymentId = "invalid-id";
        when(paymentRepository.getPaymentById(invalidPaymentId)).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> paymentService.getPayment(invalidPaymentId));
        verify(paymentRepository, times(1)).getPaymentById(invalidPaymentId);
    }
    @Test
    void shouldRetrieveAllPaymentsSuccessfully() {
        when(paymentRepository.getAllPayments()).thenReturn(payments);

        List<Payment> retrievedPayments = paymentService.getAllPayments();

        assertEquals(payments.size(), retrievedPayments.size());
        assertTrue(retrievedPayments.containsAll(payments));
    }
    @Test
    void shouldReturnEmptyListWhenNoPaymentsExist() {
        List<Payment> emptyPaymentsList = new ArrayList<>();
        when(paymentRepository.getAllPayments()).thenReturn(emptyPaymentsList);

        List<Payment> result = paymentService.getAllPayments();

        assertTrue(result.isEmpty());
        verify(paymentRepository, times(1)).getAllPayments();
    }
    @Test
    void shouldThrowExceptionWhenSettingInvalidPaymentStatus() {
        Payment payment = payments.get(1);
        String invalidStatus = "INVALID_STATUS";

        // Verify setting an invalid status throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, invalidStatus));
    }
}