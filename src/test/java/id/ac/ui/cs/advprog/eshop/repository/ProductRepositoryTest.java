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
        // Re-initialize the repository before each test
        // so each test starts with a fresh "database"
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

    // ------------------ Tests for 'update' ------------------

    @Test
    void testUpdateExistingProduct() {
        // First create a product
        Product product = new Product();
        product.setProductId("12345");
        product.setProductName("Old Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Create a product with the same ID but updated details
        Product updatedProduct = new Product();
        updatedProduct.setProductId("12345");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(20);

        // Perform the update
        Product result = productRepository.update(updatedProduct);

        // Verify that the update returned the product and changed the fields
        assertNotNull(result);
        assertEquals("12345", result.getProductId());
        assertEquals("New Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testUpdateNonExistingProduct() {
        // Create a product with some ID
        Product product = new Product();
        product.setProductId("12345");
        product.setProductName("Old Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Attempt to update a different ID that doesn't exist
        Product updatedProduct = new Product();
        updatedProduct.setProductId("67890");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(20);

        // Should return null since the product doesn't exist
        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    // ------------------ Tests for 'delete' ------------------

    @Test
    void testDeleteExistingProduct() {
        // Create a product
        Product product = new Product();
        product.setProductId("99999");
        product.setProductName("Name to be deleted");
        product.setProductQuantity(5);
        productRepository.create(product);

        // Delete it
        boolean deleted = productRepository.delete("99999");
        assertTrue(deleted);

        // Verify it was actually removed
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExistingProduct() {
        // Create a product
        Product product = new Product();
        product.setProductId("11111");
        product.setProductName("Non-existing check");
        product.setProductQuantity(5);
        productRepository.create(product);

        // Attempt to delete a product with a different ID
        boolean deleted = productRepository.delete("22222");
        assertFalse(deleted);

        // Verify the original product is still there
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product stillPresent = productIterator.next();
        assertEquals("11111", stillPresent.getProductId());
    }
}