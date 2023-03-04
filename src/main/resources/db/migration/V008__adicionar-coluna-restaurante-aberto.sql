alter table restaurante add aberto boolean not null;
update restaurante set restaurante.aberto = false;