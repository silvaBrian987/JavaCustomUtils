package net.bgsystems.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FileUtils {
	public static File crearArchivo(String filename) throws IOException {
		File file = new File(filename);
		if (!FileUtils.existeArchivo(file)) {
			file.createNewFile();
		}
		return file;
	}

	public static File crearArchivo(String filename, byte[] data) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			File file = crearArchivo(filename);
			is = new ByteArrayInputStream(data);
			fos = new FileOutputStream(file);
			IOUtils.copy(is, fos);
			return file;
		} catch (IOException e) {
			throw e;
		} finally {
			if (is != null)
				is.close();
			if (fos != null)
				fos.close();
		}
	}

	public static boolean existeArchivo(File archivo) {
		return archivo.exists();
	}

	public static void borrarArchivo(String ruta) throws Exception {
		File archivo = new File(ruta);
		FileUtils.borrarArchivo(archivo);
	}

	public static void borrarArchivo(File archivo) throws Exception {
		if (archivo != null) {
			if (existeArchivo(archivo)) {
				if (!archivoAbierto(archivo)) {
					archivo.delete();
				} else {
					throw new Exception("El archivo " + archivo + " no se puede borrar porque esta abierto");
				}
			} else {
				throw new Exception("El archivo " + archivo + " no existe");
			}
		}
	}

	public static boolean archivoAbierto(File archivo) {
		boolean isFileUnlocked = false;
		try {
			org.apache.commons.io.FileUtils.touch(archivo);
		} catch (IOException e) {
			isFileUnlocked = true;
		}
		return isFileUnlocked;
	}

	public static File obtenerArchivo(String filepath) {
		return new File(filepath);
	}

	public static File[] obtenerArchivos(String path) {
		File[] txtFiles = null;
		int lastBackslash = path.lastIndexOf('/');

		if (lastBackslash == -1) {
			lastBackslash = path.lastIndexOf('\\');
		}

		if (lastBackslash > -1) {
			File fileDir = new File(path.substring(0, lastBackslash));
			String wildcard = path.substring(lastBackslash + 1, path.length());
			FilenameFilter wildcardFilter = new WildcardFileFilter(wildcard);
			txtFiles = fileDir.listFiles(wildcardFilter);
		}
		return txtFiles;
	}

	public static void renombrarArchivo(File archivoOrigen, String nuevoNombre) throws Exception {
		File archivoDestino = new File(obtenerCarpeta(archivoOrigen.getPath()) + nuevoNombre);
		archivoDestino.createNewFile();
		FileInputStream fisOrigen = new FileInputStream(archivoOrigen);
		FileOutputStream fisDestino = new FileOutputStream(archivoDestino);
		copiarArchivo(fisOrigen, fisDestino);
		fisOrigen.close();
		fisDestino.close();
		FileUtils.borrarArchivo(archivoOrigen);

	}

	public static void copiarArchivo(FileInputStream fisOrigen, FileOutputStream fisDestino) throws IOException {
		IOUtils.copy(fisOrigen, fisDestino);
	}

	public static String obtenerCarpeta(String path) {
		return path.substring(0, path.lastIndexOf('\\') + 1);
	}

	public static byte[] fileToBytes(File file) throws FileNotFoundException, IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return IOUtils.toByteArray(fis);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
}
