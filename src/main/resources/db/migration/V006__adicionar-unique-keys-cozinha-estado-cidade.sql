alter table cozinha add constraint UK_nome_cozinha_unico unique (nome);
alter table estado add constraint UK_nome_estado_unico unique (nome);
alter table cidade add constraint UK_nome_cidade_unico unique (nome);