package com.qmoney.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * This class will load test data properties.
 * @author nitin
 *
 */
public class LoadDataUtil {
	private InputStream input = null;
	public static Properties prop = new Properties();

	public LoadDataUtil() {
		input = LoadDataUtil.class.getClassLoader().getResourceAsStream("TestData.properties");
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
