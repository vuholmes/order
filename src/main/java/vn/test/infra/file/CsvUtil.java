package vn.test.infra.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CsvUtil {

  /**
   * Copy data from csv file to another csv file.
   *
   * @param inputFile      input csv file.
   * @param outputFilePath output csv file path.
   * @param filterIndex    the index of filter column. If no filter input -1.
   * @param filterValue    the filter value.
   * @param copiedIndexes  copied indexes.
   * @param ignoredHeader  whether header line is ignored.
   */
  public static void copyData(File inputFile, String outputFilePath, int filterIndex,
      String filterValue, int[] copiedIndexes, boolean ignoredHeader)
      throws IOException {
    try (
        var reader = new BufferedReader(new FileReader(inputFile));
        var writer = new BufferedWriter(new FileWriter(outputFilePath, true))
    ) {

      // Header line
      var line = reader.readLine();
      if (Objects.isNull(line)) {
        return;
      }
      if (!ignoredHeader) {
        System.out.println("Reading header");
        copyCsvLine(writer, line, -1, null, copiedIndexes);
      }

      // Data lines
      while (Objects.nonNull(line = reader.readLine())) {
        copyCsvLine(writer, line, filterIndex, filterValue, copiedIndexes);
      }
    }
  }

  private static void copyCsvLine(BufferedWriter writer, String inputLine, int filterIndex,
      String filterValue, int[] copiedIndexes) throws IOException {
    var inputValues = inputLine.split(",");

    if (filterIndex > -1) {
      if (filterIndex > (inputValues.length - 1)) {
        throw new IllegalArgumentException("Filter index is out of boundary.");
      }

      if (!inputValues[filterIndex].strip().equals(filterValue)) {
        return;
      }

      writer.newLine();
    }

    var outputValues = new ArrayList<String>();

    for (var index : copiedIndexes) {
      if (index > (inputValues.length - 1)) {
        throw new IllegalArgumentException("Copied index is out of boundary.");
      }

      outputValues.add(inputValues[index]);
    }

    writer.append(String.join(",", outputValues));
  }
}
