package br.com.ams.backcatalogo.configuration;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class IniciarDiretoriosConfiguration {

	@Value("${diretorio}")
	private String diretorio;

	@Value("${diretorio.upload}")
	private String diretorioUpload;

	@Value("${diretorio.catalogos}")
	private String diretorioCatalogos;

	@Order(value = Ordered.HIGHEST_PRECEDENCE)
	@Bean
	CommandLineRunner iniciarDiretorios() {
		return args -> {

			FileUtils.forceMkdir(new File(diretorio));
			FileUtils.forceMkdir(new File(diretorioUpload));
			FileUtils.forceMkdir(new File(diretorioCatalogos));

		};
	}

}
