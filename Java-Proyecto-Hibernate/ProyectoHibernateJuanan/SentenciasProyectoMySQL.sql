
-- ****************************************************************************************************************** TABLAS ****************************************************************************************************


-- Ir creándolas en el siguiente orden:

-- 1
create table piezas(
id_pieza smallint unsigned not null primary key auto_increment,
pieza varchar(40) default null,
precio dec(6,2) unsigned default null,
descripcion text default null
);

-- 2
create table proveedores(
id_proveedor smallint unsigned not null  primary key auto_increment,
proveedor varchar(40) default null,
responsableVentas varchar(40) default null,
dirCP varchar(40) default null
);

-- 3
create table proyectos(
id_proyecto smallint unsigned not null  primary key auto_increment,
proyecto varchar(40) default null,
ciudad varchar(40) default null
);

-- 4
create table gestion(
ID_gestion smallint unsigned not null  primary key auto_increment,
ID_gestion_pieza smallint unsigned not null,
ID_gestion_proveedor smallint unsigned not null,
ID_gestion_proyecto smallint unsigned not null,
cantidad dec(6,2) unsigned default null,
CONSTRAINT FK_ID_GESTION_PIEZA FOREIGN KEY (ID_gestion_pieza) REFERENCES piezas(id_pieza) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT FK_ID_GESTION_PROVEEDOR FOREIGN KEY (ID_gestion_proveedor) REFERENCES proveedores(id_proveedor)  ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT FK_ID_GESTION_PROYECTO FOREIGN KEY (ID_gestion_proyecto) REFERENCES proyectos(id_proyecto)  ON DELETE CASCADE ON UPDATE CASCADE
);

-- Para evitar duplicados
-- 5
ALTER TABLE gestion ADD CONSTRAINT UNIKE_Gestion_Pieza_Prov_Proy UNIQUE (ID_gestion_pieza, ID_gestion_proveedor,ID_gestion_proyecto);


-- ******************************************************************************************************* INSERCIONES DATOS *******************************************************************************************************

-- Insertar registro en el siguiente orden: piezas, proveedores, proyectos y al final gestion

-- Ejemplo de momtar una habitación en diferntes proyectos:

-- ************************************************************************************************************* PIEZAS *************************************************************************************************************

-- mesa
insert into  piezas values (default,'Tornillo M5',0.25,'Amarrar mesa'); -- 1
insert into  piezas values (default,'Pata',10,'Pata de la mesa'); -- 2
insert into  piezas values (default,'Tablero',20,'Tablero de la mesa'); -- 3

-- armario
insert into  piezas values (default,'Paneles de madera',10,'laterales armario'); -- 4
insert into  piezas values (default,'Panel fondo armario',50,'Fondo armario'); -- 5
insert into  piezas values (default,'Tablas para estanterías',20,'Estanterías para armario'); -- 6
insert into  piezas values (default,'Tornillos M2',0.35,'Estanterías para armario'); -- 7
insert into  piezas values (default,'Cajones',35,'Cajones para armario'); -- 8
insert into  piezas values (default,'Pantalonero',80,'Pantalonero para armario'); -- 9
insert into  piezas values (default,'Cola',5,'Cola para madera'); -- 10


-- silla
insert into  piezas values (default,'Pata',4,'Pata de silla'); -- 11
insert into  piezas values (default,'Asiento',5,'Asiento de la silla'); -- 12
insert into  piezas values (default,'Tela asiento',30,'Tela asiento de la silla'); -- 13

-- lampara
insert into  piezas values (default,'Bombilla',10,'Bombilla para lámpara'); -- 14
insert into  piezas values (default,'Lámpara',30,'Lámpara de techo'); -- 15
insert into  piezas values (default,'Cables',7,'Cable para lámpara'); -- 16

-- pintar habitación
insert into  piezas values (default,'Pintura',100,'Pintura azul para habitación'); -- 17
insert into  piezas values (default,'Brocha',10,'Brocha para pintar'); -- 18
insert into  piezas values (default,'Rodillo',20,'Rodillo para pintar'); -- 19
insert into  piezas values (default,'Cinta carrocero',4,'Cinta carrocero para pintar'); -- 20
insert into  piezas values (default,'Plástico',8,'Plástico protector suelo'); -- 21

-- montar cama
insert into  piezas values (default,'Paneles laterales',15,'Paneles laterales cama'); -- 22
insert into  piezas values (default,'Cabecero cama',30,'Cabecero cama'); -- 23
insert into  piezas values (default,'Base cama',80,'Base cama'); -- 24
insert into  piezas values (default,'Pie cama',65,'Pie cama'); -- 25
insert into  piezas values (default,'Patas cama',3,'Patas cama'); -- 26
insert into  piezas values (default,'Textil',75,'Ropa para cama'); -- 27


-- montar cuarto baño
insert into  piezas values (default,'Tornillos M10',3,'Tornillos lavabos'); -- 28
insert into  piezas values (default,'Tuberias PVC',10,'Tuberías para el agua'); -- 29
insert into  piezas values (default,'Grifería',35,'Grifos para cuarto baño'); -- 30
insert into  piezas values (default,'Tuberias PVC',25,'Tuberías para el agua'); -- 31
insert into  piezas values (default,'Lavabo',250,'Lavabo cuarto baño'); -- 32
insert into  piezas values (default,'WC cuarto baño',150,'WC cuarto baño'); -- 33
insert into  piezas values (default,'Utensilios albañileria',60,'Utensilios albañileria'); -- 34

-- montar puertas
insert into  piezas values (default,'Puertas madera',200,'Puertas madera'); -- 35
insert into  piezas values (default,'Bisagras puertas',5,'Bisagras puertas'); -- 36
insert into  piezas values (default,'Manilla dorada puertas',40,'Manillas puertas'); -- 37


-- montar tarima madera
insert into  piezas values (default,'Tarima flotante',1250,'Tarima flotante'); -- 38
insert into  piezas values (default,'Barniz',40,'Barniz para madera'); -- 39

-- montar espejo
insert into  piezas values (default,'Espejo rectangular pared',200,'Espejo pared'); -- 40
insert into  piezas values (default,'Silicona',20,'Silicona para pegar cristal'); -- 41

-- montar estor
insert into  piezas values (default,'Estor',300,'Estor gris 3 metros'); -- 42

-- montar ventanas aluminio
insert into  piezas values (default,'Estor',800,'Estor gris 3 metros'); -- 43

-- estanteria
insert into  piezas values (default,'Paneles',6,'Paneles para estanteria'); -- 44
insert into  piezas values (default,'Patas',6,'Patas para estanteria'); -- 45




-- ************************************************************************************************************* PROVEEDORES ********************************************************************************************************


insert into proveedores values (default,'Tornillería Martinez','Alonso Martinez','Aligobeo 12, Vitoria'); -- 1
insert into proveedores values (default,'Ikea','Dpto. Ventas Ikea','Ikea Barakaldo'); -- 2
insert into proveedores values (default,'Carpintería López','Luis López','Uritiasolo 20, Vitoria'); -- 3
insert into proveedores values (default,'Pinturas Goiko','Goiko López','Ali 10, Vitoria'); -- 4
insert into proveedores values (default,'Lámparas GusyLuz','Leopoldo MArtinez','Jundiz 3, Vitoria'); -- 5
insert into proveedores values (default,'Carpintería Maderas Txomin','Txomin Dominguez','Segismundo 1, Pamplona'); -- 6
insert into proveedores values (default,'Puertas portazo','Miranda Cruz','Abre 2,Logroño'); -- 7
insert into proveedores values (default,'Colchones dormión','Fermin Feliz','Megapark 1, Bilbao'); -- 8
insert into proveedores values (default,'Textiles Castaño','Castaño Ruiz','Gamarra 4, Vitoria'); -- 9
insert into proveedores values (default,'Fontanería Aguados','Lucas Pérez','Badaya 5, Vitoria'); -- 10
insert into proveedores values (default,'Stores sinluz','Vanesa Fernandez','San Cristobal 34, Vitoria'); -- 11
insert into proveedores values (default,'Carpintería Metálica Paco','Paco Menendez','Dato 56, Vitoria'); -- 12
insert into proveedores values (default,'Cristalería Adurza','Luis Salazar','Adurza 12, Vitoria'); -- 13
insert into proveedores values (default,'Fontanería del Norte','Álvaro Minguez','Avenida Gasteiz 67, Vitoria'); -- 14
insert into proveedores values (default,'Tarimas madera José','José Rico','Laurel 70, Logroño'); -- 15
insert into proveedores values (default,'Leroy Merlín','Jacinto Fdz.','El boulevard, Vitoria'); -- 16
insert into proveedores values (default,'Electricidad Txisposo','Raúl Trulo','Dato 34, Vitoria'); -- 17


-- ************************************************************************************************************* PROYECTOS **********************************************************************************************************


insert into proyectos values (default, 'Montar mesa', 'Vitoria'); -- 1
insert into proyectos values (default, 'Montar armario', 'Vitoria'); -- 2
insert into proyectos values (default, 'Montar silla', 'Vitoria'); -- 3
insert into proyectos values (default, 'Montar Lámparas y electricidad', 'Vitoria'); -- 4
insert into proyectos values (default, 'Pintar habitación', 'Bilbao'); -- 5
insert into proyectos values (default, 'Montar cama', 'Vitoria'); -- 6
insert into proyectos values (default, 'Montar cuarto de baño', 'Vitoria'); -- 7
insert into proyectos values (default, 'Montar puertas', 'Pamplona'); -- 8
insert into proyectos values (default, 'Montar tarima madera', 'Pamplona'); -- 9
insert into proyectos values (default, 'Montar espejo', 'Logroño'); -- 10
insert into proyectos values (default, 'Montar estor', 'Logroño'); -- 11
insert into proyectos values (default, 'Montar ventanas aluminio', 'Logroño'); -- 12


-- ************************************************************************************************************** GESTION **************************************************************************************************************

-- ID, idpieza, idproveedor, idproyecto, cantidad
-- mesa 1
insert into gestion values (default, 1,1,1,50); -- 1
insert into gestion values (default, 2,2,1,4); -- 2
insert into gestion values (default, 3,3,1,1); -- 3


-- armario 2

insert into gestion values (default, 4,16,2,2); -- 4
insert into gestion values (default, 5,3,2,1); -- 5
insert into gestion values (default, 6,6,2,4); -- 6
insert into gestion values (default, 7,1,2,30); -- 7
insert into gestion values (default, 8,2,2,8); -- 8
insert into gestion values (default, 9,2,2,1); -- 9
insert into gestion values (default, 10,16,2,2); -- 10


 -- silla 3
insert into gestion values (default, 11,3,3,4); -- 11
insert into gestion values (default, 12,3,3,1); -- 12
insert into gestion values (default, 13,9,3,10); -- 13
insert into gestion values (default, 7,1,3,16); -- 14

-- lampara 4
insert into gestion values (default, 14,17,4,4); -- 15
insert into gestion values (default, 15,5,4,1); -- 16
insert into gestion values (default, 16,17,4,3); -- 17
insert into gestion values (default, 1,1,4,4); -- 18


-- pintar habitación 5
insert into gestion values (default, 17,4,5,1); -- 19
insert into gestion values (default, 18,16,5,3); -- 20
insert into gestion values (default, 19,16,5,1); -- 21
insert into gestion values (default, 20,16,5,4); -- 22
insert into gestion values (default, 21,16,5,1); -- 23


-- montar cama 6

insert into gestion values (default, 22,2,6,2); -- 24
insert into gestion values (default, 23,2,6,1); -- 25
insert into gestion values (default, 24,2,6,1); -- 26
insert into gestion values (default, 25,2,6,1); -- 27
insert into gestion values (default, 26,3,6,4); -- 28
insert into gestion values (default, 27,9,6,1); -- 29
insert into gestion values (default, 1,1,6,50); -- 30


-- montar cuarto baño 7
insert into gestion values (default, 28,1,7,20); -- 31
insert into gestion values (default, 29,10,7,10); -- 32
insert into gestion values (default, 30,14,7,2); -- 33
insert into gestion values (default, 31,16,7,12); -- 34
insert into gestion values (default, 32,14,7,1); -- 35
insert into gestion values (default, 33,14,7,1); -- 36
insert into gestion values (default, 34,16,7,5); -- 37

-- montar puertas 8


-- ********************************************************************************************************* PRUEBAS *********************************************************************************************************************************


select * from proyectos;
select * from piezas;
select * from proveedores;
select * from gestion;

drop table gestion;
drop table proveedores;
drop table piezas;
drop table proyectos;

-- alter table proveedores rename column apeProv to responsableVentas;

-- Para resetear el autoincrement, SOLO PARA LOS ULTIMOS VALORES, porque si es para valores intermedios que hemos borrado, luego van a coincidir las IDs, mejor no tocar
alter table proveedores AUTO_INCREMENT=4;
delete from proveedores where id_proveedor=4;

-- ******** CONSULTAS *********

select a.ID_gestion, b.proyecto, c.proveedor, d.pieza, a.cantidad from gestion a, proyectos b, proveedores c, piezas d where a.ID_gestion_proyecto= b.id_proyecto and a.ID_gestion_proveedor=c.id_proveedor and a.ID_gestion_pieza=d.id_pieza;





--  ********************************************************************************************************** SUMINISTROS PROVEEDORES  *************************************************************************************************************

-- Número de piezas suministradas por el proveedor
select * from Gestion where ID_gestion_proveedor=15;
select count(*) from Gestion where ID_gestion_proveedor=15; -- BUENA


-- Número de Proyectos a los que ha suministrado un proveevedor en concreto (Agrupamos por proyectos)
select * from Gestion where ID_gestion_proveedor=15;
select count(*) from Gestion where ID_gestion_proveedor=15 group by ID_gestion_proyecto; -- FILAS
select count(*) from(select count(ID_gestion_proveedor) from gestion where ID_gestion_proveedor=15 group by ID_gestion_proyecto) as g; -- Número de proyectos en los que está el proveedor BUENA

 -- tabla
select * from Gestion where ID_gestion_proveedor=1; -- Esta la coge bien Hibernate, pasáandole lo objetos


--  ********************************************************************************************************** SUMINISTROS PIEZAS  *****************************************************************************************************************


-- Número de proyectos a los que se ha suministrado la pieza elegida (Agrupamos por proyecto)
select * from Gestion where ID_gestion_pieza=7;
select count(*) from Gestion where ID_gestion_pieza=7 group by ID_gestion_proyecto; -- FILAS
select count(*) from( select count(ID_gestion_pieza) from gestion where ID_gestion_pieza=7 group by ID_gestion_proyecto) as g; -- Número de proyectos en los que está el proveedor --> ESTA ES LA BUENA


-- Número de proveedores distintos que suministran una  pieza en concreto
select  count(distinct (ID_gestion_proveedor))  from gestion where ID_gestion_pieza=7; -- ESTA ES LA BUENA
select  count(*)  from (select count(distinct(ID_gestion_proveedor)) from  gestion where ID_gestion_pieza=7 group by ID_gestion_proveedor ) as t;


-- Cantidad total que se ha suministrado de la pieza elegida
select sum(cantidad) from Gestion; -- SUMA TODAS LAS CANTIDADES
select sum(cantidad) from Gestion where ID_gestion_pieza=1; -- SUMA LA PIEZA ELEGIDA --> ESTA ES LA BUENA


-- ************************************************************************************************************* ESTADISTICAS PROYECTOS ************************************************************************************************************


-- Nombre de la pieza que mas cantidad se ha suministrado y cantidad total se está utilizando en descendente (Agrupamos por piezas), saco solo el primer resultado
select sum(cantidad) from Gestion group by ID_gestion_pieza order by sum(cantidad) DESC ;
select z.pieza, sum(g.cantidad) as sum from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza  group by g.ID_gestion_pieza order by sum DESC Limit 1; -- ESTA ES LA BUENA


-- Nombre de la pieza  y número de proyectos que más cantidad se ha suministrado en proyectos , ponemos Distinct para quitar duplicados
select  z.pieza,max(g.cantidad),count(g.ID_gestion_proyecto) from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza group by ID_gestion_proyecto,pieza  order by max(cantidad) DESC LIMIT 1; -- (agrupamos por proyectos y piezas)
select  z.pieza,max(g.cantidad),count(g.ID_gestion_proyecto) from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza group by ID_gestion_pieza  order by count(g.ID_gestion_proyecto) DESC ; -- (agrupamos por piezas)
select  z.pieza,max(g.cantidad),count(g.ID_gestion_proyecto) from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza group by ID_gestion_pieza  order by count(g.ID_gestion_proyecto) DESC LIMIT 1; -- (agrupamos por piezas) ESTA ES LA BUENA


-- Resultados TABLA

-- (Agrupamiento por piezas) este es de Pruebas
select distinct z.id_pieza, z.pieza, z.descripcion , count(z.pieza) as 'Numero piezas',sum(g.cantidad) as 'Cantidad suministrada', count(g.ID_gestion_proveedor) as 'Numero Proyectos' from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza  group by g.ID_gestion_proyecto,g.ID_gestion_pieza order by 'Numero piezas' DESC;
select  z.id_pieza, z.pieza, z.descripcion,count(g.ID_gestion_pieza) as 'Numero Piezas', sum(g.cantidad) as 'Cantidad suministrada', count(g.ID_gestion_proyecto) as 'Numero Proyectos' from Gestion g, piezas z where g.ID_gestion_pieza=z.id_pieza  group by g.ID_gestion_pieza order by 'Número Piezas' DESC;

-- (Agrupamiento por proyectos) ESTA ES LA BUENA
select  p.id_proyecto, p.proyecto, p.ciudad,count(g.ID_gestion_pieza) as 'Numero Piezas', sum(g.cantidad) as 'Cantidad suministrada', count(distinct g.ID_gestion_proveedor) as 'Numero Proveedores' from Gestion g, proyectos p where g.ID_gestion_proyecto=p.id_proyecto  group by g.ID_gestion_proyecto order by 'Numero Piezas' DESC;


-- ************************************************************************************************************** ESTADISTICAS PROVEEDORES ************************************************************************************************************


-- Nombre del proveedor que mas cantidad de piezas está suministrando  y Número de cantidad de piezas suministradas en descendente (Agrupamos por proveedores), saco solo el primer resultado
select sum(cantidad) from Gestion  group by ID_gestion_proveedor order by sum(cantidad) DESC ;
select  p.proveedor, sum(g.cantidad) from Gestion g, proveedores p where g.ID_gestion_proveedor= p.id_proveedor group by g.ID_gestion_proveedor order by sum(g.cantidad) DESC limit 1 ; -- ESTA ES LA BUENA


-- Nombre del proveedor que ha suministrado a más proyectos y Número de Proyectos DISTINTOS (Agrupamos por proveedores) ordenado por cantidad de proyectos
select  p.proveedor ,  sum(g.cantidad) as 'Cantidad total de piezas', count(g.ID_gestion_proyecto) as 'Numero proyectos' from Gestion g, proveedores p where g.ID_gestion_proveedor=p.id_proveedor group by g.ID_gestion_proveedor order by count(g.ID_gestion_proyecto) DESC;
select  p.proveedor ,  sum(g.cantidad) as 'Cantidad total de piezas', count(distinct g.ID_gestion_proyecto) as 'Numero proyectos' from Gestion g, proveedores p where g.ID_gestion_proveedor=p.id_proveedor group by g.ID_gestion_proveedor order by count(distinct g.ID_gestion_proyecto) DESC limit 1; -- ESTA ES LA BUENA

-- Nombre del proveedor que ha suministrado más piezas  y Número total de cantidad de piezas suministradas  y Número de proyectos DISTINTOS en los que está(Agrupamos por proveedores) Es el mismo que el anterior pero oredenado por suma de cantidad de piezas

select  p.proveedor, sum(g.cantidad), count(distinct g.ID_gestion_proyecto) from Gestion g, proveedores p where g.ID_gestion_proveedor= p.id_proveedor group by g.ID_gestion_proveedor order by sum(g.cantidad) DESC limit 1; -- ESTA ES LA BUENA

-- TABLA
-- (Agrupamiento por proveedor)
select  p.id_proveedor, p.proveedor, p.responsableVentas,  count(distinct g.ID_gestion_proyecto) as 'Numero Proyectos', sum(g.cantidad) as 'Cantidad suministrada', count(g.ID_gestion_pieza) as 'Numero Piezas' from Gestion g, proveedores p where g.ID_gestion_proveedor=p.id_proveedor  group by g.ID_gestion_proveedor order by 'Numero Piezas' DESC; -- ESTA ES LA BUENA









-- ******************************************************************* INSERCIONES ANTIGUAS  (SOLO AL PRINCIPIO PARA PRUEBAS)*********************************************************************

-- Insertar registro en el siguiente orden: piezas, proveedores, proyectos y al final gestion

-- inserciones PIEZAS
insert into  piezas values (default,'Tornillo',0.25,'amarrar mesa');
insert into  piezas values (default,'Pata',10,'pata de la mesa');
insert into  piezas values (default,'Tablero',20,'tablero de la mesa');

insert into  piezas values (default,'Pata',4,'Pata de silla');
insert into  piezas values (default,'Asiento',5,'Asiento de la silla');

insert into  piezas values (default,'Paneles',6,'Paneles para estanteria');
insert into  piezas values (default,'Patas',6,'Patas para estanteria');


-- inserciones proveedores
insert into proveedores values (default,'Tornillería Martinez','Alonso Martinez','Aligobeo 12, Vitoria');
insert into proveedores values (default,'Ikea','Dpto. Ventas Ikea','Ikea Barakaldo');
insert into proveedores values (default,'Carpintería López','Luis López','Uritiasolo 20, Vitoria');


-- inserciones proyectos
insert into proyectos values (default, 'Montar mesa', 'Vitoria');
insert into proyectos values (default, 'Montar silla', 'Barakaldo');
insert into proyectos values (default, 'Montar estanteria', 'Pamplona');


-- inserciones gestiones
insert into gestion values (default, 1,1,1,50);
insert into gestion values (default, 2,2,1,4);
insert into gestion values (default, 3,3,1,1);

insert into gestion values (default, 1,1,2,50);
insert into gestion values (default, 4,15,2,4);
insert into gestion values (default, 5,3,2,1);

insert into gestion values (default, 1,1,3,50);
insert into gestion values (default, 6,15,3,10);
insert into gestion values (default, 7,15,3,6);


