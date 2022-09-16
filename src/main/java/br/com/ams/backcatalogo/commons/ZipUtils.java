package br.com.ams.backcatalogo.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	public static void zipDirectory(List<String> filesListInDir, File dir, String zipDirName) {
		try {
			populateFilesList(filesListInDir, dir);
			var fos = new FileOutputStream(zipDirName);
			var zos = new ZipOutputStream(fos);
			for (var filePath : filesListInDir) {
				var ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
				zos.putNextEntry(ze);
				var fis = new FileInputStream(filePath);
				var buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void populateFilesList(List<String> filesListInDir, File dir) throws IOException {
		var files = dir.listFiles();
		for (var file : files) {
			if (file.isFile())
				filesListInDir.add(file.getAbsolutePath());
			else
				populateFilesList(filesListInDir, file);
		}
	}

	public static void zipSingleFile(File file, String zipFileName) {
		try {
			var fos = new FileOutputStream(zipFileName);
			var zos = new ZipOutputStream(fos);
			var ze = new ZipEntry(file.getName());
			zos.putNextEntry(ze);
			var fis = new FileInputStream(file);
			var buffer = new byte[1024];
			int len;
			while ((len = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}
			zos.closeEntry();
			zos.close();
			fis.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
