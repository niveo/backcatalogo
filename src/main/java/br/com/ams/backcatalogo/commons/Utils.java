package br.com.ams.backcatalogo.commons;

import java.io.File;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static String carregarJSON(Object value) throws Exception {
		return carregarJSON(value, false);
	}

	public static File diretorioTemporarioEarquivo(String extencao) throws Exception {
		var dir = System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString()
				+ File.separator;

		System.out.println("Temporario: " + dir);

		var dirFile = new File(dir);

		dirFile.mkdirs();

		var file = new File(dirFile + File.separator + UUID.randomUUID() + extencao);

		System.out.println("Temporario: " + file);

		file.createNewFile();

		return file;
	}

	public static File diretorioTemporario() throws Exception {
		var dir = System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString()
				+ File.separator;

		System.out.println("Temporario: " + dir);

		var dirFile = new File(dir);

		dirFile.mkdirs();

		return dirFile;
	}

	public static String carregarJSON(Object value, Boolean ignorarAnotacoes) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter();
		return mapper.writeValueAsString(value);
	}

	public static File criarArquivoDinamicoTemporario(String extencao) throws Exception {
		return criarArquivoTemporario(UUID.randomUUID() + extencao);
	}

	public static File criarArquivoTemporario(String name) throws Exception {
		var file = new File(System.getProperty("java.io.tmpdir"), name);
		System.out.println("Temporario: " + file);
		file.createNewFile();
		return file;
	}

}
