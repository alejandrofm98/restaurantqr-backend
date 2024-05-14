package com.example.demo.services;

import static com.example.demo.utils.Constants.CONSTANT_IMAGE_MB;

import com.example.demo.entity.Product;
import com.example.demo.exception.Exceptions;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  private final BusinessRepository businessRepository;

  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }


  public Product uploadImage(MultipartFile file, String name, String description, Float price,
      Integer category, Integer status, String businessUuid) throws IOException {
    Product product = new Product();
    product.setName(name);
    product.setDescription(description);
    product.setPrice(price);
    product.setCategory(category);
    product.setStatus(status);
    if (!businessRepository.existsByBusinessUuid(businessUuid)) {
      throw new Exceptions("The business does not exist");
    } else {
      product.setBusiness(businessRepository.findById(businessUuid).get());
    }

    String originalFileName = file.getOriginalFilename();
    String fileExtension = getFileExtension(originalFileName);

    long maxSizeBytes = CONSTANT_IMAGE_MB * 1024 * 1024; // 4 MB en bytes
    if (file.getSize() > maxSizeBytes) {
      throw new IllegalArgumentException("The file size exceeds the maximum allowed size of 4 MB.");
    }

    if (fileExtension != null && (fileExtension.equalsIgnoreCase("jpg")
        || fileExtension.equalsIgnoreCase("jpeg"))) {
      String newFileName = name.replaceAll("\\s+", "_") + "."
          + fileExtension; // Nombre del producto con espacios reemplazados por guiones bajos y extensión original
      String directory = "images/" + product.getBusiness().getBusinessUuid();
      String imagePath = directory + "/" + newFileName; // Ruta de la imagen

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
    } else {
      throw new IllegalArgumentException("The file must be of type JPG or JPEG.");
    }

    return productRepository.save(product);
  }


  public Product updateProductWithImage(Integer id, MultipartFile file, String name,
      String description, Float price, Integer category, Integer status, String businessUuid)
      throws IOException {
    Optional<Product> optionalProduct = productRepository.findById(id);
    if (optionalProduct.isPresent()) {
      Product product = optionalProduct.get();
      String oldImage = product.getImage(); // Obtener la imagen actual del producto
      String newFileName = name.replaceAll("\\s+", "_") + "." + getFileExtension(
          oldImage); // Nuevo nombre de la imagen

      product.setName(name);
      product.setDescription(description);
      product.setPrice(price);
      product.setCategory(category);
      product.setStatus(status);

      if (!businessRepository.existsByBusinessUuid(businessUuid)) {
        throw new Exceptions("The business does not exist");
      } else {
        product.setBusiness(businessRepository.findById(businessUuid).get());
      }

      if (file != null && !file.isEmpty()) {
        String originalFileName = file.getOriginalFilename();
        String extension = getFileExtension(originalFileName);

        // Limitar el tamaño máximo del archivo a 4 MB
        long maxSizeBytes = CONSTANT_IMAGE_MB * 1024 * 1024; // 4 MB en bytes
        if (file.getSize() > maxSizeBytes) {
          throw new IllegalArgumentException(
              "The file size exceeds the maximum allowed size of 4 MB.");
        }

        if (extension != null && (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase(
            "jpeg"))) {
          String directory = "images/" + product.getBusiness().getBusinessUuid();
          String imagePath = directory + "/" + newFileName; // Ruta de la nueva imagen

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

          // Eliminar la imagen anterior si existe
          if (oldImage != null && !oldImage.equals(imagePath)) {
            Files.deleteIfExists(Paths.get(oldImage));
          }
        } else {
          throw new IllegalArgumentException("The file must be of type JPG or JPEG.");
        }
      } else {
        // Renombrar la imagen asociada si el nombre del producto cambia
        if (!oldImage.equals(newFileName)) {
          Path oldPath = Paths.get(oldImage);
          Path newPath = Paths.get("images" + "/" + newFileName);
          Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
          product.setImage(newPath.toString());
        }
      }

      return productRepository.save(product);
    } else {
      throw new EntityNotFoundException("Product with id " + id + " not found");
    }
  }


  private String getFileExtension(String fileName) {
    if (fileName != null && fileName.lastIndexOf(".") != -1) {
      return fileName.substring(fileName.lastIndexOf(".") + 1);
    } else {
      return null;
    }
  }


  public Product deleteProductAndImage(Integer id) throws IOException {
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

    // Devolver el producto eliminado
    return product;
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }


}
