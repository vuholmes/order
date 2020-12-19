package vn.test.infra.file;

import java.io.File;
import java.io.IOException;

public class FileUtil {

  /**
   * List all files with given extension in a folder.
   *
   * @param folderPath    path of folder.
   * @param fileExtension file extension (with .).
   * @return file list.
   */
  public static File[] listFiles(String folderPath, String fileExtension) throws IOException {
    var folder = new File(folderPath);

    if (!folder.exists()
        || !folder.isDirectory()) {
      throw new IOException("Invalid folder path.");
    }

    if (!folder.canRead()) {
      throw new IOException("Can not read folder.");
    }

    return folder.listFiles((dir, name) -> name.endsWith(fileExtension));
  }
}
