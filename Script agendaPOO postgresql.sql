CREATE TABLE usuario(
   Email varchar(60) not null,
   senha varchar(20) not null,
   nome varchar(100) not null,
   dataNascimento date,
   telefone varchar(14),
   PRIMARY KEY(Email)
);

CREATE TABLE Atividade(
   id text not null,
   descricao varchar(100) not null,
   local varchar(100) not null,
   data Date not null,
   horaInicio time not null,
   horaFim time not null,
   email_usuario varchar(60) not null,
   tipo varchar(20) not null,
   primary key(id),
   foreign key(email_usuario) references usuario(email)
);

CREATE TABLE Atividade_Usuario(
   id_atividade text not null,
   email varchar(60) not null,
   primary key(id_atividade,email),
   FOREIGN KEY(id_atividade) REFERENCES Atividade(id)
);