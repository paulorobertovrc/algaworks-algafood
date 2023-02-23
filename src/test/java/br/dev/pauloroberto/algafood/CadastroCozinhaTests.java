package br.dev.pauloroberto.algafood;

import br.dev.pauloroberto.algafood.domain.model.Cozinha;
import br.dev.pauloroberto.algafood.domain.model.Restaurante;
import br.dev.pauloroberto.algafood.domain.service.CadastroCozinhaService;
import br.dev.pauloroberto.algafood.domain.service.CadastroRestauranteService;
import br.dev.pauloroberto.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaTests {
    @LocalServerPort
    private int port;
    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .body("", hasSize(4))
                    .body("nome", hasItems("Tailandesa", "Indiana"));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.given()
                    .body("{ \"nome\": \"Chinesa\" }")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/1")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("nome", equalTo("Tailandesa"));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/100")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarStatus204_QuandoExcluirCozinhaExistente() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/4")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deveRetornarStatus409_QuandoExcluirCozinhaEmUso() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .delete("/1")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    private void prepararDados() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cadastroCozinhaService.salvar(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Indiana");
        cadastroCozinhaService.salvar(cozinha2);

        Cozinha cozinha3 = new Cozinha();
        cozinha3.setNome("Americana");
        cadastroCozinhaService.salvar(cozinha3);

        Cozinha cozinha4 = new Cozinha();
        cozinha4.setNome("Brasileira");
        cadastroCozinhaService.salvar(cozinha4);

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Restaurante 1");
        restaurante.setCozinha(cozinha1);
        restaurante.setTaxaFrete(new BigDecimal(10));
        cadastroRestauranteService.salvar(restaurante);
    }

}
