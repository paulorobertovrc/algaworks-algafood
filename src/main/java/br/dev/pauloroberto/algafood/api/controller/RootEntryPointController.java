package br.dev.pauloroberto.algafood.api.controller;

import br.dev.pauloroberto.algafood.api.helper.LinkHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private LinkHelper linkHelper;

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
        rootEntryPointModel.add(linkHelper.linkToEstatisticas("estatisticas"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
