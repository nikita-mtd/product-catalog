package ru.nomia.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nomia.service.ProductService;

@SpringBootTest
class ProductComponentTest {

    @Autowired
    private ProductService productService;

    @Test
    void create_product_request_works() {
        //RestAssured()
        var product = productService.createProduct(ProductService.CreateProduct.builder().build());
    }

}
