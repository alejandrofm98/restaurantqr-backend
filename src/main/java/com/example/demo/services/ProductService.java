package com.example.demo.services;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }


    public Product uploadImage(MultipartFile file, String name, String description, String price, Integer category, Integer status, Integer idempresa) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setStatus(status);
        product.setIdempresa(idempresa);

        String fileName = file.getOriginalFilename();
        String directory = "images";
        String imagePath = directory + "\\" + fileName; // Ruta de la imagen

        // Verificar si la carpeta existe, si no, crearla
        Path directoryPath = Paths.get(directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // Escribir el archivo en la carpeta
        byte[] bytes = file.getBytes();
        Path path = Paths.get(imagePath);
        Files.write(path, bytes);

        product.setImage(imagePath);

        return productRepository.save(product);
    }



    public Product updateProductWithImage(Integer id, MultipartFile file, String name, String description, String price, Integer category, Integer status, Integer idempresa) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(category);
            product.setStatus(status);
            product.setIdempresa(idempresa);

            if (file != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String directory = "images";
                String imagePath = directory + "\\" + fileName; // Ruta de la imagen

                // Verificar si la carpeta existe, si no, crearla
                Path directoryPath = Paths.get(directory);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                // Escribir el archivo en la carpeta
                byte[] bytes = file.getBytes();
                Path path = Paths.get(imagePath);
                Files.write(path, bytes);

                product.setImage(imagePath);
            }

            return productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Product with id " + id + " not found");
        }
    }




        public void deleteProductAndImage(Integer id) throws IOException {
            // Buscar el producto por ID
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

            // Eliminar la imagen del disco
            String imagePath = product.getImage();
            if (imagePath != null) {
                Path path = Paths.get(imagePath);
                Files.deleteIfExists(path);
            }

            // Eliminar el producto de la base de datos
            productRepository.deleteById(id);
        }






}