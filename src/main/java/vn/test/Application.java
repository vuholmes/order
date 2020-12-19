package vn.test;

import java.io.IOException;
import java.util.Objects;
import vn.test.infra.file.OrderUtil;

public class Application {


	public static void main(String[] args) {
		if (Objects.isNull(args) || (args.length < 1)) {
			System.err.println("Please input folder path.");
			return;
		}

		try {
			OrderUtil.writeOrder(args[0]);

			System.out.println("The orders were writen successfully.");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
