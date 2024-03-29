package br.dev.pauloroberto.algafood.api.v1.controller;

import br.dev.pauloroberto.algafood.api.v1.helper.LinkHelper;
import br.dev.pauloroberto.algafood.api.v2.helper.LinkHelperV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private LinkHelper linkHelper;
    @Autowired
    private LinkHelperV2 linkHelperV2;

    @GetMapping
    private RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();
        rootEntryPointModel.add(linkHelper.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(linkHelper.linkToPedidos("pedidos"));
        rootEntryPointModel.add(linkHelper.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(linkHelper.linkToGrupos("grupos"));
        rootEntryPointModel.add(linkHelper.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(linkHelper.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(linkHelper.linkToFormasPagamento("formas-pagamento"));
        rootEntryPointModel.add(linkHelper.linkToEstados("estados"));
        rootEntryPointModel.add(linkHelper.linkToCidades("cidades"));
        rootEntryPointModel.add(linkHelperV2.linkToCidades("cidades"));
        rootEntryPointModel.add(linkHelper.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
