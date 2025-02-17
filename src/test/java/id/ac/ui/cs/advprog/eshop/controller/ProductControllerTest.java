package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProductService service;

    @Test
    public void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testCreateProductPost() throws Exception {
        // When the service.create is called, just return the same product
        when(service.create(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Simulate form data submission
        mockMvc.perform(post("/product/create")
                        .param("productName", "Test Product")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        // Capture the product passed to the service
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(service).create(productCaptor.capture());
        Product created = productCaptor.getValue();
        
        // Verify that a random UUID was generated
        // (We check that the id is not null and looks like a UUID)
        assertThat(created.getProductId(), notNullValue());
        assertThat(created.getProductId(), instanceOf(String.class));
        // Optionally, you can verify that it is a valid UUID
        UUID.fromString(created.getProductId());

        assertThat(created.getProductName(), is("Test Product"));
        assertThat(created.getProductQuantity(), is(10));
    }

    @Test
    public void testProductListPage() throws Exception {
        // Arrange: prepare some products
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(5);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(15);

        List<Product> productList = Arrays.asList(product1, product2);
        when(service.findAll()).thenReturn(productList);

        // Act & Assert
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is("1")),
                                hasProperty("productName", is("Product 1"))
                        )
                )));
    }

    @Test
    public void testEditProductPage_Found() throws Exception {
        // Arrange: create a product and return it via findAll
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Product 1");
        product.setProductQuantity(5);
        when(service.findAll()).thenReturn(Arrays.asList(product));

        // Act & Assert: request edit page for product id "1"
        mockMvc.perform(get("/product/edit/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", hasProperty("productId", is("1"))));
    }

    @Test
    public void testEditProductPage_NotFound() throws Exception {
        // Arrange: return an empty list or a list without the requested product
        when(service.findAll()).thenReturn(Arrays.asList());

        // Act & Assert: request edit page for a non-existing product id
        mockMvc.perform(get("/product/edit/{id}", "non-existing-id"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    public void testEditProductPost() throws Exception {
        // Arrange: when update is called, return the product.
        when(service.update(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act: perform POST to edit endpoint.
        mockMvc.perform(post("/product/edit")
                        .param("productId", "1")
                        .param("productName", "Updated Product")
                        .param("productQuantity", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        // Verify that service.update was called with correct properties.
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(service).update(productCaptor.capture());
        Product updated = productCaptor.getValue();
        assertThat(updated.getProductId(), is("1"));
        assertThat(updated.getProductName(), is("Updated Product"));
        assertThat(updated.getProductQuantity(), is(20));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Arrange: assume delete returns true.
        when(service.delete("1")).thenReturn(true);

        // Act & Assert: call delete endpoint.
        mockMvc.perform(get("/product/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        // Verify that delete was invoked.
        verify(service).delete("1");
    }
}