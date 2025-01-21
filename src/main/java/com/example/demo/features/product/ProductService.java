package com.example.demo.features.product;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

  Product insertProductWithImage(MultipartFile file, ProductRequest productRequest) throws IOException;

  Product updateProductWithImage(Integer id, MultipartFile file, ProductRequest productRequest)
      throws IOException;

  Product deleteProductAndImage(Integer id) throws IOException;

  List<Product> getAllProducts();

  List<Product> getProductsByBusinessUuid(String businessUuid);

  Product getProductById(Long id);

}
