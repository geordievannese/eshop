package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Clear the repository for each test by re-instantiating the ProductRepository
        // (Alternatively, you could add a clear method to ProductRepository if needed.)
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    // New test cases to increase coverage

    @Test
    void testUpdateExistingProduct() {
        // Create and add a product
        Product originalProduct = new Product();
        originalProduct.setProductId("prod-001");
        originalProduct.setProductName("Original Name");
        originalProduct.setProductQuantity(10);
        productRepository.create(originalProduct);

        // Update the product
        Product updatedProduct = new Product();
        updatedProduct.setProductId("prod-001");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("prod-001", result.getProductId());
        assertEquals("Updated Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());

        // Verify via findAll
        Iterator<Product> iterator = productRepository.findAll();
        assertTrue(iterator.hasNext());
        Product savedProduct = iterator.next();
        assertEquals("Updated Name", savedProduct.getProductName());
        assertEquals(20, savedProduct.getProductQuantity());
    }

    @Test
    void testUpdateNonExistingProduct() {
        // Create a product that is not added to the repository
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("Doesn't Matter");
        updatedProduct.setProductQuantity(5);

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteExistingProduct() {
        // Create and add a product
        Product product = new Product();
        product.setProductId("prod-002");
        product.setProductName("To Be Deleted");
        product.setProductQuantity(15);
        productRepository.create(product);

        // Verify the product exists
        Iterator<Product> iterator = productRepository.findAll();
        assertTrue(iterator.hasNext());

        // Delete the product
        boolean deleted = productRepository.delete("prod-002");
        assertTrue(deleted);

        // Verify repository is empty now
        iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteNonExistingProduct() {
        // Try deleting a product that doesn't exist
        boolean deleted = productRepository.delete("non-existent-id");
        assertFalse(deleted);
    }
}

