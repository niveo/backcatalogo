package br.com.ams.backcatalogo.configuration;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ams.backcatalogo.commons.FileDirTemporario;
import br.com.ams.backcatalogo.model.Catalogo;
import br.com.ams.backcatalogo.model.CatalogoPaginaProduto;
import br.com.ams.backcatalogo.model.Produto;
import br.com.ams.backcatalogo.repository.CatalogoRepository;
import br.com.ams.backcatalogo.repository.ProdutoRepository;
import br.com.ams.backcatalogo.service.CatalogoArquivosService;
import br.com.ams.backcatalogo.service.CatalogoService;

@Configuration
public class IniciarBancoDadosConfiguration {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CatalogoRepository catalogoRepository;

	@Autowired
	private CatalogoArquivosService catalogoArquivosService;

	@Autowired
	private CatalogoService catalogoService;

	@Value("${diretorio.upload}")
	String sistemaDiretorioUpload;

	@Order(value = Ordered.HIGHEST_PRECEDENCE)
	@Bean
	@Transactional(propagation = Propagation.REQUIRED)
	CommandLineRunner iniciarBanco() {
		return args -> {

			try {

				var inProdutos = this.getClass().getResourceAsStream("/produtos.json");
				String jsonProdutos = new String(inProdutos.readAllBytes(), StandardCharsets.UTF_8);
				var listaProdutos = new ObjectMapper().readValue(jsonProdutos, new TypeReference<ArrayList<Produto>>() {
				});
				produtoRepository.saveAll(listaProdutos);

				var catalogo = new Catalogo();
				catalogo.setDescricao("Buba");
				catalogo.setImagemUrl("https://bubababy.com.br/wp-content/uploads/2020/03/Buba_Logo-1.png");
				catalogo = catalogoRepository.save(catalogo);

				var nameFile = UUID.randomUUID().toString();
				var newFIle = new File(sistemaDiretorioUpload + File.separator + nameFile);

				var in = this.getClass().getResourceAsStream("/CATALOGO.pdf");

				var tempFile = FileDirTemporario.criarArquivoTemporario(UUID.randomUUID().toString() + ".pdf");

				FileUtils.copyInputStreamToFile(in, tempFile);

				FileUtils.copyFile(tempFile, newFIle);

				catalogoArquivosService.importarCatalogo(nameFile, catalogo.getCodigo(), 150);

				catalogo = catalogoService.obterCodigoLazy(catalogo.getCodigo());

				catalogo.getCatalogoPaginas().forEach(c -> {
					if (c.getPagina() == 1) {
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-2469").get(),
										158.15F, 350.972F, 289.463F, 552.986F, 192.821F, 263.523F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-2471").get(),
										423.28F, 606.46F, 281.429F, 551.379F, 183.18F, 269.95F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-0740").get(),
										672.341F, 829.812F, 279.822F, 577.088F, 157.471F, 297.266F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-0741").get(),
										878.017F, 1091.73F, 266.967F, 578.695F, 213.71F, 311.728F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-2110").get(),
										247.589F, 459.693F, 600.449F, 907.356F, 212.104F, 306.908F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-2109").get(),
										519.146F, 721.609F, 603.662F, 910.57F, 202.463F, 306.908F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-2111").get(),
										761.78F, 972.276F, 595.628F, 907.356F, 210.497F, 311.728F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-1385").get(),
										150.705F, 359.134F, 941.371F, 1254.82F, 208.429F, 313.451F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-1384").get(),
										380.139F, 596.646F, 947.833F, 1259.67F, 216.507F, 311.835F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-2118").get(),
										627.345F, 816.385F, 954.296F, 1249.97F, 189.04F, 295.678F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-2115").get(),
										877.782F, 1068.44F, 949.449F, 1274.21F, 190.656F, 324.761F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-3239").get(),
										380.139F, 583.72F, 1291.98F, 1595.74F, 203.581F, 303.757F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-3241").get(),
										672.585F, 890.708F, 1291.98F, 1587.66F, 218.123F, 295.678F));
					} else if (c.getPagina() == 2) {
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-8975").get(),
										107.825F, 322.275F, 301.834F, 579.445F, 214.451F, 277.611F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-8979").get(),
										363.403F, 592.542F, 287.146F, 576.508F, 229.139F, 289.362F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-8974").get(),
										643.952F, 855.465F, 303.303F, 582.383F, 211.513F, 279.08F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-8978").get(),
										893.655F, 1136.01F, 284.208F, 577.977F, 242.359F, 293.768F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-1826").get(),
										68.1658F, 238.551F, 642.606F, 924.623F, 170.386F, 282.018F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-1825").get(),
										257.646F, 414.812F, 644.075F, 923.154F, 157.166F, 279.08F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-8237").get(),
										425.094F, 595.48F, 629.386F, 920.217F, 170.386F, 290.831F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-8238").get(),
										611.637F, 776.148F, 629.386F, 917.279F, 164.51F, 287.893F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-9726").get(),
										802.587F, 972.972F, 629.386F, 923.154F, 170.386F, 293.768F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-9727").get(),
										1003.82F, 1187.42F, 633.793F, 921.686F, 183.605F, 287.893F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-8983").get(),
										78.4477F, 388.373F, 970.157F, 1283.02F, 309.926F, 312.863F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-8982").get(),
										78.4477F, 388.373F, 970.157F, 1283.02F, 309.926F, 312.863F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-2634").get(),
										441.252F, 620.45F, 1045.07F, 1284.49F, 179.199F, 239.421F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-2647").get(),
										676.266F, 836.37F, 1042.13F, 1283.02F, 160.104F, 240.89F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-5818").get(),
										917.156F, 1160.98F, 980.439F, 1293.3F, 243.828F, 312.863F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-5819").get(),
										917.156F, 1160.98F, 980.439F, 1293.3F, 243.828F, 312.863F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-7761").get(),
										113.7F, 314.931F, 1330.02F, 1597.35F, 201.231F, 267.329F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-7762").get(),
										372.216F, 582.26F, 1327.09F, 1609.1F, 210.044F, 282.018F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-5248").get(),
										668.922F, 831.964F, 1324.15F, 1576.79F, 163.041F, 252.641F));
						c.getCatalogoPaginaProdutos()
								.add(new CatalogoPaginaProduto(c, produtoRepository.obterReferencia("49-6156").get(),
										906.875F, 1127.2F, 1324.15F, 1609.1F, 220.326F, 284.955F));
					}
				});
				catalogoRepository.save(catalogo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}
}
