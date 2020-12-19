package vn.test;

import java.io.IOException;
import java.util.Objects;
import vn.test.infra.file.CsvUtil;
import vn.test.infra.file.FileUtil;

public class Application {

  private final static String[] FILTER_VALUES = new String[]{"HP", "DELL", "IBM"};
  private final static int FILTER_COLUMN_INDEX = 3;
  private final static int[] COPIED_COLUMN_INDEXES = new int[]{0, 1, 2};


  public static void main(String[] args) {
    if (Objects.isNull(args)
        || (args.length < 1)) {
      System.err.println("Please input folder path.");
      return;
    }

    try {
      var folderPath = args[0];

      var csvFiles = FileUtil.listFiles(folderPath, ".csv");

      if (csvFiles.length < 1) {
        System.err.println("No csv file in folder.");
        return;
      }

      for (var filterValue : FILTER_VALUES) {
        var outputFilePath = folderPath + "\\" + filterValue + "_order.csv";

        System.out.println("Writing file: " + outputFilePath);

        var ignoredHeader = false;
        for (var file : csvFiles) {
          System.out.println("- Reading file: " + file.getAbsolutePath());

          CsvUtil.copyData(file, outputFilePath, FILTER_COLUMN_INDEX, filterValue,
              COPIED_COLUMN_INDEXES, ignoredHeader);

          if (!ignoredHeader) {
            ignoredHeader = true;
          }
        }
      }

      System.out.println("Done.");

    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

  }

}
