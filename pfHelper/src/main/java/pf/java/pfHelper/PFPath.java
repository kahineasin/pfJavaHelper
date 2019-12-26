package pf.java.pfHelper;

import java.io.File;

/*
 * 代替C#里的Path类
 */
public class PFPath {
	public static String GetFileName(String dirPath) {
		File file = new File(dirPath);
		return file.getName();
	}
    //
    // 摘要:
    //     Returns the file name of the specified path string without the extension.
    //
    // 参数:
    //   path:
    //     The path of the file.
    //
    // 返回结果:
    //     The string returned by System.IO.Path.GetFileName(System.String), minus the last
    //     period (.) and all characters following it.
    //
    // 异常:
    //   T:System.ArgumentException:
    //     path contains one or more of the invalid characters defined in System.IO.Path.GetInvalidPathChars.
    public static String GetFileNameWithoutExtension(String path) {
    	String fileName=GetFileName(path);
    	return fileName.substring(0,fileName.lastIndexOf("."));
    }
}
