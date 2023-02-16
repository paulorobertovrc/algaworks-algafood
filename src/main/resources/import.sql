INSERT INTO cozinha (id, nome) VALUES (1, 'Brasileira');
INSERT INTO cozinha (id, nome) VALUES (2, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (3, 'Indiana');

INSERT INTO estado (nome) VALUES ('MS');
INSERT INTO estado (nome) VALUES ('PR');

INSERT INTO cidade (nome, estado_id) VALUES ('Campo Grande', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Maringá', 2);

INSERT INTO restaurante (nome, taxa_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id) VALUES ('Thai Gourmet', 0, 2, '79002-340', 'Rua dos Pré-Socráticos', 1200, 'Sala 800', 'Bairro dos Estados', 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_cidade_id) VALUES ('Sabor Brasil', 5, 1, '87000-340', 'Avenida Paraná', 2000, 'Bairro dos Estados', 2);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES ('Tuk Tuk Comida Indiana', 15, 3);

insert into forma_pagamento (descricao) values ('Cartão de crédito');
insert into forma_pagamento (descricao) values ('Cartão de débito');
insert into forma_pagamento (descricao) values ('Dinheiro');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);