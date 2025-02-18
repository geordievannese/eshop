import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        // Create a mock for the repository
        productRepository = mock(ProductRepository.class);
        // Create an instance of the service implementation
        productService = new ProductServiceImpl();
        // Use reflection to inject the mock into the service
        try {
            Field field = ProductServiceImpl.class.getDeclaredField("productRepository");
            field.setAccessible(true);
            field.set(productService, productRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreate() {
        // Arrange
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        when(productRepository.create(product)).thenReturn(product);

        // Act
        Product created = productService.create(product);

        // Assert
        assertNotNull(created);
        assertEquals("1", created.getProductId());
        verify(productRepository).create(product);
    }

    @Test
    public void testFindAll() {
        // Arrange
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Test1");
        product1.setProductQuantity(10);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Test2");
        product2.setProductQuantity(20);

        List<Product> productList = Arrays.asList(product1, product2);
        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        // Act
        List<Product> result = productService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    public void testUpdate() {
        // Arrange
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Updated Product");
        product.setProductQuantity(15);
        when(productRepository.update(product)).thenReturn(product);

        // Act
        Product updated = productService.update(product);

        // Assert
        assertNotNull(updated);
        assertEquals("Updated Product", updated.getProductName());
        verify(productRepository).update(product);
    }

    @Test
    public void testDelete() {
        // Arrange
        String productId = "1";
        when(productRepository.delete(productId)).thenReturn(true);

        // Act
        boolean deleted = productService.delete(productId);

        // Assert
        assertTrue(deleted);
        verify(productRepository).delete(productId);
    }
}
