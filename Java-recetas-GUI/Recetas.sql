
----------------------- CREACI�N DE TABLAS ------------------

-- TABLA PADRE--

CREATE TABLE RECETA(
ID NUMBER(2) PRIMARY KEY,
RECETA VARCHAR2(100),
INSTRUCCIONES VARCHAR2(1000)
);


-- TABLA HIJA 1 --

CREATE TABLE INGREDIENTE(
ID NUMBER(2) PRIMARY KEY,
INGREDIENTE VARCHAR2(50)
);


-- TABLA HIJA 2 --

CREATE TABLE INGREDIENTE_RECETA(
ID NUMBER(2) PRIMARY KEY,
CANTIDAD VARCHAR2(50),
INGREDIENTE_ID NUMBER(2) CONSTRAINT INGREC_ING_FK REFERENCES INGREDIENTE(ID) ON DELETE CASCADE,
RECETA_ID NUMBER(2) CONSTRAINT INGREC_REC_FK REFERENCES RECETA(ID) ON DELETE CASCADE
);



---------- INSERCI�N DE DATOS ------- 

INSERT INTO RECETA VALUES(1,'Receta 1','Prueba receta 1');
INSERT INTO RECETA VALUES(2,'Receta 2','Prueba receta 2');

INSERT INTO INGREDIENTE VALUES(1,'Huevos camperos');
INSERT INTO INGREDIENTE VALUES(2,'Patatas gallegas');
INSERT INTO INGREDIENTE VALUES(3,'Aceite de oliva virgen extra');
INSERT INTO INGREDIENTE VALUES(4,'Cebolla');
INSERT INTO INGREDIENTE VALUES(5,'Sal');

INSERT INTO INGREDIENTE_RECETA VALUES(1,'8',1,1);
INSERT INTO INGREDIENTE_RECETA VALUES(2,'1 kgr.',2,1);
INSERT INTO INGREDIENTE_RECETA VALUES(3,'0.5 litros',3,1);
INSERT INTO INGREDIENTE_RECETA VALUES(4,'1',4,2);
INSERT INTO INGREDIENTE_RECETA VALUES(5,'Al gusto',5,2);

-- Autoincrementales para los campos 'ID' de las tablas ingrediente, receta e Ingrediente-Receta

CREATE SEQUENCE SEQ_ING
START WITH 1
INCREMENT BY 1;

drop SEQUENCE SEQ_ING;



CREATE SEQUENCE SEQ_REC
START WITH 1
INCREMENT BY 1;

drop SEQUENCE SEQ_REC;


CREATE SEQUENCE SEQ_INGREC
START WITH 1
INCREMENT BY 1;

drop SEQUENCE SEQ_INGREC;


-------------------- TRIGGERS ------------------------

--  QUIERO AUTOCOMPLETAR ANTES LOS CAMPOS 'ID' DE LA TABLA RECETA E 'ID' DE LA TABLA 'INGREDIENTE', 
--  PARA LUEGO PODER HACER LA INSERCI�N DE REGISTROS EN LA TABLA 'INGREDIENTE_RECETA' QUE HE CONSEGUIDO ANTERIORMENTE 

CREATE OR REPLACE TRIGGER TR_ING_REC
BEFORE INSERT OR UPDATE OR DELETE
ON INGREDIENTE_RECETA
FOR EACH ROW

 BEGIN
    INSERT INTO RECETA (ID) VALUES(SEQ_REC.NEXTVAL);  -- actualizo el valor de 'id' en la tabla 'receta'
    INSERT INTO INGREDIENTE (ID) VALUES (SEQ_ING.NEXTVAL); -- actualizo el valor de 'id en la tabla 'ingrediente'
 END TR_ING_REC;
 

 
 
CREATE TRIGGER TRIG_CREAR_ID_RECETA
BEFORE INSERT ON RECETA
FOR EACH ROW
BEGIN
SELECT SEQ_REC.NEXTVAL INTO :NEW.ID FROM DUAL;
END;

CREATE TRIGGER TRIG_CREAR_ID_INGREDIENTE
BEFORE INSERT ON INGREDIENTE
FOR EACH ROW
BEGIN
SELECT SEQ_ING.NEXTVAL INTO :NEW.ID FROM DUAL;
END;

CREATE TRIGGER TRIG_CREAR_ID_ING_REC
BEFORE INSERT ON INGREDIENTE_RECETA
FOR EACH ROW
BEGIN
SELECT SEQ_INGREC.NEXTVAL INTO :NEW.ID FROM DUAL;
END;

alter trigger TR_ING_REC disable;
alter trigger TRIG_CREAR_ID_RECETA disable;
alter trigger TRIG_CREAR_ID_ING_REC disable;
alter trigger TRIG_CREAR_ID_INGREDIENTE disable;

 
SELECT trigger_name, status FROM ALL_TRIGGERS;
drop trigger TR_ING_REC;


 
 
 


--- CAMBIOS ---

ALTER TABLE RECETA MODIFY INSTRUCCIONES VARCHAR2(1600); -- He modificado el tama�o del varchar2
ALTER TABLE RECETA MODIFY RECETA VARCHAR2(100);
ALTER TABLE INGREDIENTE MODIFY INGREDIENTE VARCHAR2(50);

--ALTER TABLE hr.personas
ALTER TABLE RECETA ALTER COLUMN RECETA [DROP NOT NULL];
--ALTER TABLE INGREDIENTE_RECETA DROP CONSTRAINT INGREC_ING_FK;
--ALTER TABLE INGREDIENTE_RECETA ADD CONSTRAINT INGREC_ING_FK FOREIGN KEY (INGREDIENTE_ID) REFERENCES INGREDIENTE(ID) ON DELETE CASCADE;
--ALTER TABLE INGREDIENTE_RECETA DROP CONSTRAINT INGREC_REC_FK;
--ALTER TABLE INGREDIENTE_RECETA ADD CONSTRAINT INGREC_REC_FK FOREIGN KEY (RECETA_ID) REFERENCES RECETA(ID) ON DELETE CASCADE;







 
