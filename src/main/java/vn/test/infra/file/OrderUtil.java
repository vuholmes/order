package vn.test.infra.file;

import java.io.File;
import java.io.IOException;

public class OrderUtil {

	private final static String[] FILTER_VALUES = new String[]{"HP", "DELL", "IBM"};
	private final static int FILTER_COLUMN_INDEX = 3;
	private final static int[] COPIED_COLUMN_INDEXES = new int[]{0, 1, 2};

	/**
	 * List all files with given extension in a folder.
	 *
	 * @param folderPath    path of folder.
	 * @param fileExtension file extension (with .).
	 * @return file list.
	 */
	private static File[] listFiles(String folderPath, String fileExtension) throws IOException {
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

	/**
	 * Write order csv files based on the input folder containing csv files.
	 *
	 * @param inputPath the input path
	 */
	public static void writeOrder(String inputPath) throws IOException {
		var csvFiles = listFiles(inputPath, ".csv");

		if (csvFiles.length < 1) {
			System.err.println("No csv file in folder.");
			return;
		}

		for (var filterValue : FILTER_VALUES) {
			var outputFilePath =
					inputPath + File.separator + "output" + File.separator + filterValue + "_order.csv";

			File outputFile = new File(outputFilePath);
			if (outputFile.exists() && outputFile.delete()) {
				System.out.println("Deleting existing file: " + outputFilePath);
			}

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
	}
}
