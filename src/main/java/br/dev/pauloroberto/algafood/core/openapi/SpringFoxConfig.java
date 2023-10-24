package br.dev.pauloroberto.algafood.core.openapi;

import br.dev.pauloroberto.algafood.api.exception.handler.Problem;
import br.dev.pauloroberto.algafood.api.v1.model.*;
import br.dev.pauloroberto.algafood.api.v1.openapi.model.*;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.function.Consumer;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {
    // http://localhost:8080/swagger-ui/index.html - URL para acessar a documentação da API
    // http://localhost:8080/v3/api-docs - URL para acessar a documentação da API em formato JSON

    // Esta classe estabelece parâmetros de configuração do Swagger. Para isso, é necessário criar um Bean do tipo Docket.
    // O Docket é o objeto que contém as configurações do Swagger. Ele é criado através do método apiDocket(), que retorna um Docket contendo as configurações.

    @Bean
    public Docket apiDocket() {
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("br.dev.pauloroberto.algafood.api")) // Exibe apenas os endpoints de um pacote específico
    //                .paths(PathSelectors.ant("/restaurantes/*")) // Este filtro exibe apenas os endpoints que começam com /restaurantes/
    //                .paths(PathSelectors.ant("/restaurantes/**")) // Este filtro exibe apenas os endpoints que começam com /restaurantes/ e terminam com qualquer coisa
                    .paths(PathSelectors.any()) // Este filtro exibe todos os endpoints
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
//                .globalRequestParameters(List.of(
//                        new RequestParameterBuilder()
//                                .name("campos")
//                                .description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//                                .in(ParameterType.QUERY)
//                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                                .required(false)
//                                .build()
//                )) // Adiciona um parâmetro global para todos os endpoints. Este parâmetro é utilizado para filtrar as propriedades que serão retornadas na resposta.
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // Substitui o Pageable pelo PageableModelOpenApi na documentação
                .directModelSubstitute(Links.class, LinksModelOpenApi.class) // Substitui a classe Links do Spring HATEOAS pela LinksModelOpenApi na documentação
                .alternateTypeRules(newRule(typeResolver.resolve(PagedModel.class, CozinhaDto.class),
                        CozinhasModelOpenApi.class)) // Substitui o Page<CozinhaDto> pelo CozinhasModelOpenApi na documentação
                .alternateTypeRules(newRule(typeResolver.resolve(Page.class, PedidoResumoDto.class),
                        PedidosResumoModelOpenApi.class)) // Substitui o Page<CozinhaDto> pelo CozinhasModelOpenApi na documentação
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, CidadeDto.class),
                        CidadesModelOpenApi.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, EstadoDto.class),
                        EstadosModelOpenApi.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, FormaPagamentoDto.class),
                        FormasPagamentoModelOpenApi.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, GrupoDto.class),
                        GruposModelOpenApi.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, PermissaoDto.class),
                        PermissoesModelOpenApi.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, PedidoResumoDto.class),
                        PedidosResumoModelOpenApi.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, ProdutoDto.class),
                        ProdutosModelOpenApi.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, RestauranteBasicoDto.class),
                        RestaurantesBasicoModelOpenApi.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, UsuarioDto.class),
                        UsuariosModelOpenApi.class))
                .ignoredParameterTypes(ServletWebRequest.class, FilterProvider.class, MappingJacksonValue.class) // Ignora o ServletWebRequest na documentação
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"),
                        new Tag("Grupos", "Gerencia os grupos de usuários"),
                        new Tag("Cozinhas", "Gerencia as cozinhas"),
                        new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
                        new Tag("Pedidos", "Gerencia os pedidos"),
                        new Tag("Restaurantes", "Gerencia os restaurantes"),
                        new Tag("Estados", "Gerencia os estados"),
                        new Tag("Produtos", "Gerencia os produtos de restaurantes"),
                        new Tag("Usuários", "Gerencia os usuários"),
                        new Tag("Estatísticas", "Estatísticas da AlgaFood"),
                        new Tag("Permissões", "Gerencia as permissões"));
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    private List<Response> globalDeleteResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build(),
                new ResponseBuilder()
                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .description("Erro interno do servidor")
                    .representation(MediaType.APPLICATION_JSON)
                    .apply(getProblemModelReference())
                    .build());
    }

    private List<Response> globalPostPutResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build()
        );
    }

    private List<Response> globalGetResponseMessages() {
        return List.of(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor")
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference())
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build());
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("0.1")
                .contact(new Contact("Paulo Roberto", "site", "email"))
                .license("MIT")
                .build();
    }

    private Consumer<RepresentationBuilder> getProblemModelReference() {
        return representationBuilder -> representationBuilder.model(m -> m.referenceModel(ref -> ref.key(k -> k.qualifiedModelName(q -> {
            q.namespace("br.dev.pauloroberto.algafood.api.exception.handler");
            q.name("Problema");
        }))));
    }

}
