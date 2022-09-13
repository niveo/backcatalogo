package br.com.ams.backcatalogo.configuration;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import br.com.ams.backcatalogo.model.Catalogo;
import br.com.ams.backcatalogo.model.Produto;
import br.com.ams.backcatalogo.repository.CatalogoRepository;
import br.com.ams.backcatalogo.repository.ProdutoRepository;

@Configuration
public class IniciarBancoDadosConfiguration {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CatalogoRepository catalogoRepository;

	@Order(value = Ordered.HIGHEST_PRECEDENCE)
	@Bean
	CommandLineRunner iniciarBanco() {
		return args -> {

			var listaProdutos = new ArrayList<Produto>();
			listaProdutos.add(new Produto("PRATINHO ANIMAL FUN LEAO", "49-8975"));
			listaProdutos.add(new Produto("PRATINHO BOWL ANIMAL FUN LEAO", "49-8979"));
			listaProdutos.add(new Produto("GARRAFINHA ANI FUN LEAO 400ML", "49-8983"));
			listaProdutos.add(new Produto("PRATINHO ANIMAL FUN GIRAFA", "49-8974"));
			listaProdutos.add(new Produto("PRATINHO BOWL ANIMAL FUN GIRAFA", "49-8978"));
			listaProdutos.add(new Produto("GARRAFINHA ANI FUN GIRAFA 400ML", "49-8982"));
			listaProdutos.add(new Produto("COPO DINO COM ALCA VERDE 240ML", "49-1826"));
			listaProdutos.add(new Produto("COPO DINO COM ALCA ROSA 240ML", "49-1825"));
			listaProdutos.add(new Produto("COPO C/ALCA REMO URSINHO RS250ML", "49-8237"));
			listaProdutos.add(new Produto("COPO C/ALCA REM URSINHO AZ 250ML", "49-8238"));
			listaProdutos.add(new Produto("GARRAFINHA FRESH ROSA 300ML", "49-5818"));
			listaProdutos.add(new Produto("GARRAFINHA FRESH AZUL300ML", "49-5819"));
			listaProdutos.add(new Produto("COPO D TREINAMENTO TIGRINHO180ML", "49-2634"));
			listaProdutos.add(new Produto("COPO D TREINAMENTO GATINHO 180ML", "49-2647"));
			listaProdutos.add(new Produto("COPO D TREINA C/ ALCA RS 160ML", "49-9727"));
			listaProdutos.add(new Produto("COLHER FLEXIVEL SORTIDO", "49-5248"));
			listaProdutos.add(new Produto("POTE LEITE PO C/ DIVIS ROSA300ML", "49-7761"));
			listaProdutos.add(new Produto("POTE LEITE PO C/ DIVISOAZUL300ML", "49-7762"));
			listaProdutos.add(new Produto("PORTA FRUTI SUGA EM SILI SORTIDO", "49-6156"));
			listaProdutos.add(new Produto("ASPIRADOR NASAL DE SUCCAO", "49-9742"));
			listaProdutos.add(new Produto("ASPIRA NASAL D SUCCCAO C/ ESTOJO", "49-1859"));
			listaProdutos.add(new Produto("ASPIRADOR NASAL COM ESTOJO", "49-7751"));
			listaProdutos.add(new Produto("CINTA TERMICA PARA COLICA AZUL", "49-9922"));
			listaProdutos.add(new Produto("CINTA TERMICA PARA COLICA ROSA", "49-9921"));
			listaProdutos.add(new Produto("ESCOVA E PENTE CERDAS NATURAIS", "49-8243"));
			listaProdutos.add(new Produto("KIT CUIDADOS BRANCO", "49-3611"));
			listaProdutos.add(new Produto("PRENDEDOR DE CHUPETA ANIMAL LEAO", "49-2047"));
			listaProdutos.add(new Produto("PRENDEDOR D/CHUPETA ANIMAL GIRAF", "49-2049"));
			listaProdutos.add(new Produto("MORDEDOR COM GEL SORTIDO", "49-7230"));
			listaProdutos.add(new Produto("MORDEDOR COM AGUA MACAQUINHO", "49-1822"));
			listaProdutos.add(new Produto("MORDEDOR COM AGUA CENTOPEIA", "49-9611"));
			listaProdutos.add(new Produto("MASSAGEADOR DE GENGIVA BANANA", "49-7232"));
			listaProdutos.add(new Produto("DEDOCHES DIVERTIDOS SAFARI", "49-7284"));
			listaProdutos.add(new Produto("RISQUE E APAGUE COM ESPONJA", "49-7472"));
			listaProdutos.add(new Produto("KIT LIXA DE UNHA ELETRICO", "49-3777"));
			listaProdutos.add(new Produto("LENÇO BABY NASAL 20UN", "49-1990 "));
			listaProdutos.add(new Produto("GARRAFINHA D ALUM LEAO SOR 450ML", "49-2115"));
			listaProdutos.add(new Produto("GARRAFINHA D ALU GIRAFA SOR450ML", "49-2118"));
			listaProdutos.add(new Produto("POTE TERMICO GUMY AZUL 320ML", "49-2471"));
			listaProdutos.add(new Produto("POTE TERMICO GUMY ROSA 320ML", "49-2469"));
			listaProdutos.add(new Produto("COPO TERMIC C/CANU GUMY RS 470ML", "49-2110"));
			listaProdutos.add(new Produto("COPO TERM C/CANUDO GUMY VD 470ML", "49-2111"));
			listaProdutos.add(new Produto("COPO TERM C/CANUDO GUMY AZ 470ML", "49-2109"));
			listaProdutos.add(new Produto("POTE TERMI ACO INOXIDAV RS 350ML", "49-0740"));
			listaProdutos.add(new Produto("POTE TERMICO ACO INOXID AZ 350ML", "49-0741"));
			listaProdutos.add(new Produto("COPO TERMICO C/CANUDO RS 480ML", "49-1384"));
			listaProdutos.add(new Produto("COPO TERMICO C/CANUDO AZ 480ML", "49-1385"));
			listaProdutos.add(new Produto("COPO D TREINA C/ ALCA AZUL 160ML", "49-9726"));
			listaProdutos.add(new Produto("BABADOR COM BOLSO DINO", "49-3239"));
			listaProdutos.add(new Produto("BABADOR COM BOLSO ARCO IRIS", "49-3241"));
			produtoRepository.saveAll(listaProdutos);

			try {
				var catalogo = new Catalogo();
				catalogo.setDescricao("Buba");
				catalogo.setImagemUrl("https://bubababy.com.br/wp-content/uploads/2020/03/Buba_Logo-1.png");
				catalogoRepository.save(catalogo);
			} catch (Exception e) {
				e.printStackTrace();
			}

		};
	}

}
