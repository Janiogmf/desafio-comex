package steps_definitions;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import cucumber.api.DataTable;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CadastroEmprestimoStep {
	private static JSONObject dadosObjeto;
	private static JsonPath dadosJson;
	private static JSONObject obj = new JSONObject();
	private static List<Map<String, String>> dadosLista;
	public String urlStudent = "http://localhost:8080/students";
	
	/**
	 * Metodo responsavel pelas chamadas de requisições http
	 * 
	 * @param body, param, tipoResuest
	 * @author Jânio Marinho
	 * @return response
	 */
	public Response requestResponse(String body, String param, String tipoRequest) {
		RestAssured.baseURI = urlStudent;
		RequestSpecification request = RestAssured.given();

		request.header("Content-Type", "application/json");

		if(param != null) {request.pathParam("cpf", param);}
		if(body != null) {request.body(body);}
		
		Response response = null;
		if(tipoRequest.equals("post")) {response = request.post();}
		if(tipoRequest.equals("get")) {response = request.get("/{cpf}");}
		if(tipoRequest.equals("patch")) {response = request.patch("/{cpf}");}
		
		return response;	
	}

	@Dado("^que faca cadastro de emprestimo:$")
	public void que_faca_cadastro_de_emprestimo(DataTable dadosCadastro) {
		List<Map<String, String>> listDados = dadosCadastro.asMaps(String.class, String.class);
		
		JSONObject objStudent = new JSONObject();
		JSONObject objBook = new JSONObject();
        try {
        	objStudent.put("cpf", listDados.get(0).get("CPF"));
        	objStudent.put("email", listDados.get(0).get("EMAIL"));
        	objStudent.put("name", listDados.get(0).get("NOME"));
        	objStudent.put("years", listDados.get(0).get("IDADE"));
        	objBook.put("author", listDados.get(0).get("AUTOR_LIVRO"));
        	objBook.put("name", listDados.get(0).get("NOME_LIVRO"));
        	objBook.put("title", listDados.get(0).get("TITULO"));
            JSONArray news = new JSONArray();
            news.put(objBook);
            objStudent.put("books", news);  
            dadosObjeto = objStudent;
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Entao("^validar cadastro retorno (\\d+)$")
	public void validar_cadastro_retorno(int retorno) {
		Response response = requestResponse(dadosObjeto.toString(),null,"post");
		assertEquals(retorno, response.getStatusCode());
	}
	
	@Dado("^que faca atualizacao do estudante:$")
	public void que_faca_atualizacao_do_estudante(DataTable dadosUpdate) throws Throwable {
		List<Map<String, String>> listDados = dadosUpdate.asMaps(String.class, String.class);
		dadosLista = listDados;
		obj.put("name", listDados.get(0).get("NOME"));
		obj.put("email", listDados.get(0).get("EMAIL"));
		dadosObjeto = obj;
	}
	
	@Entao("^validar atualizacao de dados retorno (\\d+)$")
	public void validar_atualizacao_de_dados_retorno(int retorno) {	
		Response response = requestResponse(obj.toString(),dadosLista.get(0).get("CPF"),"patch");
		assertEquals(retorno, response.getStatusCode());
	}
	
	@Dado("^que busque estudante cadastrado com cpf \"([^\"]*)\"$")
	public void que_busque_estudante_cadastrado_com_cpf(String cpf) {		
		Response response = requestResponse(null,cpf,"get");
		assertEquals(200, response.getStatusCode());
		dadosJson = response.jsonPath();
	}

	@Entao("^validar retorno do cadastro:$")
	public void validar_retorno_do_cadastro(DataTable dadosBusca) {
		List<Map<String, String>> listDados = dadosBusca.asMaps(String.class, String.class);
		assertEquals(listDados.get(0).get("NOME"), dadosJson.get("name").toString());
		assertEquals(listDados.get(0).get("EMAIL"), dadosJson.get("email").toString());
		assertEquals(listDados.get(0).get("CPF"), dadosJson.get("cpf").toString());
		assertEquals(listDados.get(0).get("IDADE"), dadosJson.get("years").toString());
	}
}
