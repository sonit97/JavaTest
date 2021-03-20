package nal.example.sonnpt.unittest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONObject;

import nal.example.sonnpt.nalservice.util.WorkStatus;

/**
 * @author Son
 * @version 1.0
 * @since 2020-03-20
 */
public class SetupFortest {

	public String readFileData(String pathnameFile) throws FileNotFoundException {
		String out = "";
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new File(pathnameFile));
		while (sc.hasNextLine())
			out += sc.nextLine();
		return out;
	}

	public List<String> allFileInFolder(File folder) {
		List<String> files = new ArrayList<>();
		if (folder.exists() || folder.listFiles() != null) {
			for (File fileEntry : folder.listFiles()) {
				if (fileEntry.isFile()) {
					String[] split = fileEntry.getName().split("\\.");
					if ("in".equals(split[split.length - 1])) {
						files.add(split[0]);
					}
				}
			}
		}
		return files;
	}

	public static String convertObjectToWork(JSONObject type, String name) {
		return type.get(name) == null ? null : type.get(name).toString();
	}

	public static Long setLongConvert(JSONObject type) {
		return type.get("workId") == null ? null : Long.valueOf(type.get("workId").toString());
	}

	public static Integer setIntConvert(JSONObject type, String name) {
		 return type.get(name) == null ? 0 : Integer.valueOf(type.get(name).toString());
	}
	
	public static WorkStatus getActiveStatus(String jsonElement) {
		if (jsonElement != null && jsonElement.equals(0))
			return WorkStatus.PLANNING;
		else if (jsonElement != null && jsonElement.equals(1))
			return WorkStatus.DOING;
		else if (jsonElement != null && jsonElement.equals(2))
			return WorkStatus.COMPLETE;
		return null;
	}

}
