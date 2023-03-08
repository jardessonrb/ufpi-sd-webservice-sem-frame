drop table tb_mensagem, tb_usuario, tb_registro_mensagem_encaminhada;

create table if not exists tb_usuario (
	id serial primary key,
	nome_usuario varchar unique not null,
	senha_usuario varchar(20) not null,
	ativo boolean default true
);

create table if not exists tb_mensagem (
	id serial primary key,
	assunto_mensagem varchar not null,
	corpo_mensagem varchar not null,
	situacao_mensagem varchar not null,
	horario_envio timestamp default current_timestamp,
	tipo_mensagem varchar not null,
	id_remetente bigint not null,
	id_destinatario bigint not null,
	id_mensagem_original bigint default null,
	ativo boolean default true
);

create table if not exists tb_registro_mensagem_encaminhada (
	id serial primary key,
	id_mensagem_original bigint not null,
	id_remetente bigint not null,
	id_receptor bigint not null,
	ativo boolean default true
);

alter table tb_mensagem add foreign key (id_remetente) references tb_usuario(id);
alter table tb_mensagem add foreign key (id_destinatario) references tb_usuario(id);

alter table tb_mensagem add foreign key (id_mensagem_original) references tb_mensagem(id);
alter table tb_registro_mensagem_encaminhada add foreign key (id_remetente) references tb_usuario(id);
alter table tb_registro_mensagem_encaminhada add foreign key (id_receptor) references tb_usuario(id);





select * from tb_usuario where nome_usuario ilike '%1usuario%';
select * from tb_mensagem;

insert into tb_mensagem(assunto_mensagem, corpo_mensagem, situacao_mensagem, horario_envio, tipo_mensagem, id_remetente, id_destinatario, id_mensagem_original)
values (?,?,?,?,?,?,?,?);


select * from tb_mensagem where id = 1

update tb_mensagem set situacao_mensagem = 'ARQUIVADA' where id = 1
select * from tb_usuario order by id desc limit 1
select * from tb_mensagem order by id desc limit 1


select receptor.* from tb_mensagem msg
join tb_registro_mensagem_encaminhada registro on msg.id = registro.id_mensagem_original
join tb_usuario remetente on remetente.id = registro.id_remetente
join tb_usuario receptor on registro.id_receptor = receptor.id
where registro.id_remetente = 1 and registro.id_mensagem_original = 1;

select * from tb_mensagem where situacao_mensagem = 'ENVIADA' and id_remetente = 1

select msgr.* from tb_mensagem msgo
join tb_mensagem msgr on msgo.id = msgr.id_mensagem_original
where msgo.id = 2 and msgr.tipo_mensagem = 'RESPOSTA'


