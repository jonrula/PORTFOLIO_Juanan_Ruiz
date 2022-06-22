

-- ***************************** CREACION DE TABLAS **************************************:

create table usuarios(
id_UserPass smallint unsigned not null  primary key auto_increment,
usuario varchar(40) not null,
contrasena varchar(70) not null
);

create table empleados(
dniEmple varchar(9) not null primary key,
nombreEmple varchar(40) default null,
ApeEmple varchar(40) default null,
f_Nac_Emple date default null,
f_Cont_Emple date default null,
nacionalidad varchar(40) default null,
cargo varchar(40) default null
);

create table clientes(
dniCli varchar(9) not null primary key,
nombreCli varchar(40) default null,
ApesCli varchar(40) default null,
edad tinyint unsigned default null
);

create table espectaculos(
no_espec smallint unsigned not null  primary key auto_increment,
nombreEspec varchar(40) default null,
aforo smallint unsigned default null,
descripcion varchar(1000) default null,
lugar varchar(40) default null,
fecha_Espec date default null,
horario_Espec time default null,
precio dec(6,2) unsigned default null,
responsable varchar(40) not null
);


-- TABLA PUENTE PARA ALMACENAR TODOS LOS ESPECTACULOS DE UN MISMO CLIENTE O DIFERENTES:

create table Espectaculos_Clientes(
ID_espec_Clien smallint unsigned not null  primary key auto_increment,
Cliente varchar(40)  not null,
Espectaculo smallint unsigned not null,
CONSTRAINT FK_LISTA_ESPEC_CLI FOREIGN KEY (Espectaculo) REFERENCES espectaculos(no_espec) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT FK_LISTA_ESPEC_CLI2 FOREIGN KEY (Cliente) REFERENCES clientes(dniCli)  ON DELETE CASCADE ON UPDATE CASCADE
);

-- Para evitar duplicados
ALTER TABLE Espectaculos_Clientes
ADD CONSTRAINT UNIKE_Espectaculos_Clientes UNIQUE (Cliente, Espectaculo);


-- TABLA PUENTE PARA ALMACENAR TODOS LOS ESPECTACULOS DE UN MISMO EMPLEADO O DIFERENTES:

create table Espectaculos_Empleados(
ID_espec_Emp smallint unsigned not null  primary key auto_increment,
Empleado varchar(40) not null,
Espectaculo smallint unsigned not null,
CONSTRAINT FK_LISTA_ESPEC_EMP FOREIGN KEY (Espectaculo) REFERENCES espectaculos(no_espec) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT FK_LISTA_ESPEC_EMP2 FOREIGN KEY (Empleado) REFERENCES empleados(dniEmple)  ON DELETE CASCADE ON UPDATE CASCADE
);


-- Para evitar duplicados
ALTER TABLE Espectaculos_Empleados
ADD CONSTRAINT UNIKE_Espectaculos_Empleados UNIQUE (Empleado, Espectaculo);



-- ********************** INSERCIONES ******************************

insert into  espectaculos values (default,'El rey León',1000,'Versión teatral de El rey León','Vitoria','2021-09-25','12:00',23.5,'Paco');
insert into  espectaculos values (default,'Wakaba',1000,'Versión teatral de Wakaba','Vitoria','2021-09-21','16:00',12.85,'Pepe');
insert into  espectaculos values (default,'Espectaculo2',200,'Versión teatral Espectaculo2','Vitoria','2023-09-21','12:00',99.99, 'Miranda');
insert into  espectaculos values (default,'Espectaculo3',800,'Versión teatral Espectaculo3','Bilbao','2023-09-21','13:00',65,'Julio');
insert into  espectaculos values (default,'Espectaculo4',900,'Versión teatral Espectaculo4','Pamplona','2023-02-23','18:00',25.8,'Julio');
insert into  espectaculos values (default,'Espectaculo5',900,'Versión teatral Espectaculo5','Vitoria','2023-02-25','14:00',69.55,'Pepe');
insert into  espectaculos values (default,'Espectaculo6',1200,'Versión teatral Espectaculo6','Santander','2023-02-22','20:00',20, 'Julio');
insert into  espectaculos values (default,'Espectaculo7',100,'Versión teatral Espectaculo7','Logroño','2021-10-22','14:00',20,'Pepe');
insert into  espectaculos values (default,'Espectaculo8',200,'Versión teatral Espectaculo8','Sevilla','2022-10-22','14:00',20,'Juanan');

insert into  empleados values ('80952379S','Paco','López','2000-06-12','2021-10-09','Spanish','Ingeniero sonido');
insert into  empleados values ('32315789F','Pepe','Pérez','1996-06-12','2021-12-06','Spanish','Ingeniero luces');
insert into  empleados values ('77780015H','Miranda','Fernandez','1985-08-24','2021-05-09','Spanish','Montadora');
insert into  empleados values ('72684558G','Julio','Fz.','1985-08-24','2021-05-09','Spanish','Montadora');


insert into clientes values ('16296028E','Juanan','Ruiz',50);
insert into clientes values ('16277771G','Patxi','Ruiz',56);
insert into clientes values ('26355282L','Antonio','Ruiz',86);
insert into clientes values ('26234528L','Paco','Ruiz',13);

insert into Espectaculos_Clientes values (default,'16296028E',1);
insert into Espectaculos_Clientes values (default,'16296028E',2);
insert into Espectaculos_Clientes values (default,'16296028E',3);
insert into Espectaculos_Clientes values (default,'16277771G',1);
insert into Espectaculos_Clientes values (default,'16277771G',5);
insert into Espectaculos_Clientes values (default,'26234528L',4);
insert into Espectaculos_Clientes values (default,'26234528L',5);


insert into Espectaculos_Empleados values (default,'12345678A',1);
insert into Espectaculos_Empleados values (default,'12345678A',2);
insert into Espectaculos_Empleados values (default,'12345678B',1);
insert into Espectaculos_Empleados values (default,'12345678B',2);
insert into Espectaculos_Empleados values (default,'12345678B',3);
insert into Espectaculos_Empleados values (default,'12345678B',4);
insert into Espectaculos_Empleados values (default,'12345678C',1);
insert into Espectaculos_Empleados values (default,'12235678D',6);
insert into Espectaculos_Empleados values (default,'12345678A',7);

insert into usuarios values (default,'Juanan','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4')
insert into usuarios values (default,'Ainara','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4')
insert into usuarios values (default,'Elena','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4')

select * from clientes;
select * from empleados;
select * from espectaculos;
select * from Espectaculos_Clientes;
select * from Espectaculos_Empleados;
select * from usuarios;
