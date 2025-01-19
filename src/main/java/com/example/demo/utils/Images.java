package com.example.demo.utils;

import static com.example.demo.utils.Constants.MAX_SIZE_BYTES;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class Images {

  private final MultipartFile file;
  private final String imgName;
  private final String folderPath;
  private Path imagePath;


  public String checkImg() {
    String fileExtension = getFileExtension();
    checkFileSize();
    if (fileExtension.equalsIgnoreCase("jpg")
        || fileExtension.equalsIgnoreCase("jpeg")) {
      String newFileName = imgName.replaceAll("\\s+", "_");
      newFileName = newFileName.replaceAll("\\..+", "") + "."
          + fileExtension; // Nombre del producto con espacios reemplazados por guiones bajos y extensión original
      checkDirectory();
      imagePath = Paths.get(folderPath + "/" + newFileName); // Ruta de la imagen
      return imagePath.toString();
    } else {
      throw new IllegalArgumentException("The file must be of type JPG or JPEG.");
    }

  }

  private String getFileExtension() {
    String originalFileName = file.getOriginalFilename();
    if (originalFileName != null && originalFileName.lastIndexOf(".") != -1) {
      return originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
    } else {
      throw new IllegalArgumentException("The filename must contain extension.");
    }
  }

  private void checkFileSize() {
    if (file.getSize() > MAX_SIZE_BYTES) {
      throw new IllegalArgumentException("The file size exceeds the maximum allowed size of 4 MB.");
    }
  }

  private void checkDirectory() {
    // Verificar si la carpeta existe, si no, crearla
    Path directoryPath = Paths.get(folderPath);
    if (!Files.exists(directoryPath)) {
      try {
        Files.createDirectories(directoryPath);
      } catch (IOException e) {
        throw new RuntimeException("There was a problem creating the directory.");
      }
    }
  }

  public void writeImg() {
    // Escribir el archivo en la carpeta
    byte[] bytes = null;
    try {
      bytes = file.getBytes();
      Files.write(imagePath, bytes);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write image to directory.");
    }
  }

  public static void deleteImg(String path) {
    try {
      Files.deleteIfExists(Paths.get(path));
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
