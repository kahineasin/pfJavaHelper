package pf.java.pfHelper;

import java.io.File;

/*
 * 代替C#里的Directory类
 */
public class PFDirectory {
	public static Boolean Exists(String dirPath) {
		File file = new File(dirPath);
		return file.exists();
	}
	public static void EnsureExists(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists()) {
		file.mkdirs();
	}}
}
