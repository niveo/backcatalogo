package br.com.ams.backcatalogo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.ams.backcatalogo.exception.FileStorageException;
import br.com.ams.backcatalogo.exception.MyFileNotFoundException;

@Service
public class FileStorageService {
	private final Path fileStorageLocation;

	public FileStorageService(@Value("${diretorio.upload}") String sistemaDiretorio) {

		this.fileStorageLocation = Paths.get(sistemaDiretorio).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public String storeFile(MultipartFile file) {
		return storeFile(file, null);
	}

	public String storeFile(MultipartFile file, String fileNameOutput) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		if (fileNameOutput == null) {
			fileNameOutput = fileName;
		}

		try {
			// Check if the file's name contains invalid characters
			if (fileNameOutput.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileNameOutput);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileNameOutput;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException("File not found " + fileName, ex);
		}
	}
}
