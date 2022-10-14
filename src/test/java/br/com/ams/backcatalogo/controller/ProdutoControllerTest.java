package br.com.ams.backcatalogo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ams.backcatalogo.model.Produto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithUserDetails("admin")
public class ProdutoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getVerificarStatusTipoContentRegistros() throws Exception {
		this.mockMvc.perform(get("/produto")).andExpect(status().isOk()).andDo(print())
				.andExpect(content().contentType("application/json")).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].referencia").value("49-8975"));
	}

	@Test
	public void getVerificarStatusTipoContentRegistroCodigo() throws Exception {
		this.mockMvc.perform(get("/produto/1")).andExpect(status().isOk()).andDo(print())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.referencia").value("49-8975"));
	}

	@Test
	public void getVerificarStatusTipoContentRegistroReferencia() throws Exception {
		this.mockMvc.perform(get("/produto/referencia/49-8975")).andExpect(status().isOk()).andDo(print())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.referencia").value("49-8975"));
	}

	@Test
	public void postIncluirNovoRegistro() throws Exception {
		var newProduto = new Produto("Teste Descrição", "9999", 1.50);
		this.mockMvc
				.perform(post("/produto").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new ObjectMapper().writeValueAsString(newProduto)))
				.andExpect(status().isOk()).andDo(print()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.referencia").value("9999"));
	}

	@Test
	public void putAtualizarRegistro() throws Exception {
		var updateProduto = new Produto("PRATINHO ANIMAL FUN LEAO", "49-8975", 5.00);
		this.mockMvc
				.perform(put("/produto/1").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new ObjectMapper().writeValueAsString(updateProduto)))
				.andExpect(status().isOk()).andDo(print()).andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.valor").value(5.0));
	}

	@Test
	public void deleteAtualizarRegistro() throws Exception {
		this.mockMvc.perform(delete("/produto/20")).andExpect(status().isOk()).andDo(print());
	}

}
