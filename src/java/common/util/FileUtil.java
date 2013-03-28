package common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class FileUtil {

	private static Logger log = Logger.getLogger(FileUtil.class);

	public static String readFile(File file) {
		StringBuilder line = new StringBuilder();
		try {
			// BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gb2312"));
			String str = "";
			while ((str = reader.readLine()) != null) {
				line.append(str);
			}
		} catch (FileNotFoundException e) {
			log.error("can't find the file" + file.getAbsolutePath());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("file read error" + file.getAbsolutePath());
			e.printStackTrace();
		}
		return line.toString();
	}

}
