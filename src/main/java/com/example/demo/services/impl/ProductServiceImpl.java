package com.example.demo.services.impl;

import static com.example.demo.utils.Constants.CONSTANT_IMAGE_MB;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.entity.Business;
import com.example.demo.entity.Product;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  public static final String INSERT = "INSERT";
  public static final String UPDATE = "UPDATE";
  private final ProductRepository productRepository;

  private final BusinessRepository businessRepository;

  private final AuxServiceImpl auxService;


  public Product insertProductWithImage(MultipartFile file, ProductRequest productRequest)
      throws IOException {
    Product product = new Product();
    setProduct(productRequest, product);

    String originalFileName = file.getOriginalFilename();
    String fileExtension = getFileExtension(originalFileName);

    checkImageExtension(fileExtension);
    checkImageSize(file, INSERT);

    // Nombre del producto con espacios reemplazados por guiones bajos y extensión original
    String newFileName = productRequest.getName().replaceAll("\\s+", "_") + "."
        + fileExtension;
    writeImage(file, product, newFileName);
    return productRepository.save(product);
  }

  private static void checkImageExtension(String fileExtension) {
    if (fileExtension == null || (!fileExtension.equalsIgnoreCase("jpg")
        && !fileExtension.equalsIgnoreCase("jpeg"))) {
      throw new IllegalArgumentException("The file must be of type JPG or JPEG.");
    }
  }

  private static String writeImage(MultipartFile file, Product product, String newFileName)
      throws IOException {
    String directory = "images/" + product.getBusiness().getBusinessUuid();
    String imagePath = directory + "/" + newFileName; // Ruta de la imagen

    // Verificar si la carpeta existe, si no, crearla
    Path directoryPath = Paths.get(directory);
    if (!Files.exists(directoryPath)) {
      Files.createDirectories(directoryPath);
    }

    // Eliminar la imagen anterior si existe
    Files.deleteIfExists(Paths.get(imagePath));

    // Escribir el archivo en la carpeta
    byte[] bytes = file.getBytes();
    Path path = Paths.get(imagePath);
    Files.write(path, bytes);
    product.setImage(imagePath);

    return imagePath;
  }

  private static void checkImageSize(MultipartFile file, String operation) {
    long maxSizeBytes = CONSTANT_IMAGE_MB * 1024 * 1024; // 4 MB en bytes
    if (file == null || file.isEmpty() && operation.equals(INSERT)) {
      throw new IllegalArgumentException("The file cannot be empty.");
    }
    if (file.getSize() > maxSizeBytes) {
      throw new IllegalArgumentException("The file size exceeds the maximum allowed size of 4 MB.");
    }
  }


  public Product updateProductWithImage(Integer id, MultipartFile file,
      ProductRequest productRequest)
      throws IOException {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
    String oldImage = product.getImage(); // Obtener la imagen actual del producto
    String newFileName =
        productRequest.getName().replaceAll("\\s+", "_") + "." + getFileExtension(
            oldImage); // Nuevo nombre de la imagen

    setProduct(productRequest, product);
    String originalFileName = file.getOriginalFilename();
    String fileExtension = getFileExtension(originalFileName);
    checkImageExtension(fileExtension);
    checkImageSize(file, UPDATE);

    if (Boolean.FALSE.equals(renameOldImageIfExist(oldImage, newFileName, file, product))) {
      writeImage(file, product, newFileName);
    }

    return productRepository.save(product);

  }

  private static Boolean renameOldImageIfExist(String oldImage, String newFileName,
      MultipartFile file, Product product) {

    if (!oldImage.equals(newFileName) && file != null && !file.isEmpty()) {
      Path oldPath = Paths.get(oldImage);
      Path newPath = Paths.get("images" + "/" + newFileName);
      try {
        Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
        product.setImage(newPath.toString());
        return true;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return false;
  }

  private void setProduct(ProductRequest productRequest, Product product) {
    product.setName(productRequest.getName());
    product.setDescription(productRequest.getDescription());
    product.setPrice(productRequest.getPrice());
    product.setCategory(productRequest.getCategory());
    product.setStatus(productRequest.getStatus());

    product.setBusiness(businessRepository.findById(auxService.getBussinesUUid())
        .orElseThrow(() -> new EntityNotFoundException("The bussines doesnt exist")));
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

  public List<Product> getProductsByBusinessUuid(String businessUuid) {
    Business business = businessRepository.findById(businessUuid)
        .orElseThrow(() -> new EntityNotFoundException(
            "Business not found with businessUuid: " + businessUuid));
    return productRepository.findByBusiness(business);
  }

  public Product getProductById(Long id) {
    return productRepository.findByIdAndBusinnesUuid(id, auxService.getBussinesUUid())
        .orElseThrow(() -> new EntityNotFoundException(
            "Product not found with id: " + id + " and Businnes id: " +
                auxService.getBussinesUUid()));
  }


}
