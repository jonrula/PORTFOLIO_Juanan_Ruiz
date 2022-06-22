#!/bin/bash
# menu.sh
# Autor: Juanan Ruiz

clear

# Declaramos diccionario e inicializamos con unos valores
declare -A Egibide
#Egibide[Juanan]="Juanan/Ruiz/49/SI/SGE/PDM"
#Egibide[JI]="JI/Ocariz/44/SI"


# ************************************  FUNCIONES **************************************

function ejemploBuscarPalabraCadena(){
SI=$(echo ${Egibide[Juanan]} | grep -o SI)

if [[ "$SI" == "SI" ]]; then
echo "Ya estas matriculado en la asignatura de SI"
else
echo "No estás matriculado"
fi
}



function comprobarAlumno () {
echo "Comprobando si existe $1 ... "
	if [ -v Egibide[$1] ]; then 
		echo "Ya existe el alumno/a $1"
		return 0
	elif [[ Egibide[$1] -eq "" ]]; then
		echo "No está el alumno $1"
		return 1
	else
		echo "No existe"
		return 1
	fi
}


function comprobar () {
for clave in ${!Egibide[@]}
#for key in ${!Egibide[@]} para recorrer las claves del diccionario
#for value in ${Egibide[@]} para recorrer los valores del diccionario
do
	echo "Mirando si la clave $clave coincide con la nueva $1 ... "
if [ "$clave" == "$1" ]; then
	echo "Ya existe el alumno $1"
	return 0
fi
done

echo "No existe $1"
return 1
}



function añadir(){

        Egibide[$1]="$1/$2/$3"
        echo "El alumno/a: $1 $2 con la edad de $3 años, ha sido añadido correctamente a Egibide"
	echo ""
}



function nuevosAlumnos(){

alumnos=0

while [[ $alumnos  -eq 0 ]]; do
echo "Bienvenido a Egibide"
read -p "Cuantos alumnos/as quieres añadir ? : " alumnos

es_numero='^[1-9]$'
comprobar_edad='^([1-9][1-9])$'

if ! [[ $alumnos =~ $es_numero ]] ; then
        echo "ERROR: No es un número"
                read -p "Cuantos alumnos/as quieres añadir: ? " alumnos 
        else

                for ((i = 1; i <= alumnos; i++)); do
                        read -p "Nombre alumno/a $i: " nombre
                        while [[ -z $nombre ]]; do
	                        if [[ -z $nombre ]]; then
                                        echo "ERROR, introduce un nombre !!"
                                fi
                        read -p "Nombre alumno/a $i: " nombre
                        done

                        while comprobarAlumno $nombre; do
                                echo ""
				#break
				read -p "Introduce de nuevo el nombre del alumno/a $i: " nombre

			done
	                        echo ""
                                echo "Introduce los apellidos y la edad del nuevo/a alumno/a $nombre..."
                                echo ""

                        read -p "Apellidos de $nombre: " apellidos
                        while [[ -z $apellidos ]]; do
                                 echo "ERROR, no has introducido ningún apellido !!"
                                 read -p "Apellidos de $nombre: " apellidos
                        done

                        read -p "Edad de $nombre: " edad
                        while [[ -z $edad ]] || ! [[ $edad =~ $comprobar_edad ]]; do
                                  echo "ERROR, no has introducido ningúna edad !!"
                                  read -p "Edad de $nombre: " edad
                        done
                                añadir $nombre $apellidos $edad


                done
        fi
done


}



function  tipoAsignatura(){

read -p  "$1" respuesta
while [[ $respuesta != "s" ]] && [[ $respuesta != "n" ]]; do
        read -p "Error !!, $1" respuesta
done
        if [[ $respuesta = "s" ]]; then
        	resultado+=$2
	        echo "$3"

        fi
}


function asignaturas(){

echo ""
read -p "Elige alumno/a al que quieres añadir asignaturas: " nombre
while [[ -z $nombre ]]; do
        echo "ERROR, introduce un nombre !!"
        read -p "Elige alumno/a al que quieres añadir asignaturas: " nombre
done

if comprobarAlumno $nombre; then


resultado=""
echo -e "Elige entre estas asignaturas: \n"
echo -e "\tSistemas Informáticos"
echo -e "\tDesarrollo de Interfaces"
echo -e "\tSistemas Gestión Empresarial"
echo -e "\tLenguaje de marcas"
echo -e "\tProgramación dispositivos móviles"
echo -e "\tInglés\n"

tipoAsignatura "Quieres añadir SI (s/n)?: " "/SI" "SI añadido correctamente"
tipoAsignatura "Quieres añadir DI (s/n)?: " "/DI" "DI añadido correctamente"
tipoAsignatura "Quieres añadir SGE (s/n)?: " "/SGE" "SGE añadido correctamente"
tipoAsignatura "Quieres añadir LM (s/n)?: " "/LM" "LM añadido correctamente"
tipoAsignatura "Quieres añadir PDM (s/n)?: " "/PDM" "PDM añadido correctamente"
tipoAsignatura "Quieres añadir Inglés (s/n): ? " "/Inglés" "Inglés añadido correctamente"

echo "Asignaturas elegidas: $resultado"

Egibide[$nombre]+="$resultado"
echo ${Egibide[$nombre]}

fi
}




function asignatura(){
$1=$(echo ${Egibide[$nombre]} | grep -o $1)

if [[ $($1) == "$1" ]]; then
echo "$nombre, ya estas matriculado en la asignatura de $1"
else
echo "$nombre, no estás matriculado en $1"
tipoAsignatura "Quieres añadir $1 (s/n)?: " "/$1" "$1 añadido correctamente"
echo "Asignaturas elegidas: $resultado"
Egibide[$nombre]+="$resultado"
echo ${Egibide[$nombre]}
fi


}


function comprobarAsignaturas(){
resultado=""

echo ""
read -p "Elige alumno/a al que quieres añadir asignaturas: " nombre
while [[ -z $nombre ]]; do
        echo "ERROR, introduce un nombre !!"
        read -p "Elige alumno/a al que quieres añadir asignaturas: " nombre
done


if comprobarAlumno $nombre; then

SI=$(echo ${Egibide[$nombre]} | grep -o SI)
DI=$(echo ${Egibide[$nombre]} | grep -o DI)
SGE=$(echo ${Egibide[$nombre]} | grep -o SGE)
LM=$(echo ${Egibide[$nombre]} | grep -o LM)
PDM=$(echo ${Egibide[$nombre]} | grep -o PDM)
Ingles=$(echo ${Egibide[$nombre]} | grep -o Inglés)

if [[ "$SI" == "SI" ]]; then
echo "$nombre, ya estas matriculado en la asignatura de SI"
else
echo "$nombre, no estás matriculado en DI"
tipoAsignatura "Quieres añadir SI (s/n)?: " "/SI" "SI añadido correctamente"
fi

if [[ "$DI" == "DI" ]]; then
echo "$nombre, ya estás matriculado en la asignatura de DI"
else
echo "$nombre, no estás matriculado en DI"
tipoAsignatura "Quieres añadir DI (s/n)?: " "/DI" "DI añadido correctamente"
fi

if [[ "$SGE" == "SGE" ]]; then
echo "$nombre, ya estás matriculado en la asignatura de SGE"
else
echo "$nombre, no estás matriculado en SGE"
tipoAsignatura "Quieres añadir SGE (s/n)?: " "/SGE" "SGE añadido correctamente"
fi

if [[ "$LM" == "LM" ]]; then
echo "$nombre, ya estás matriculado en la asignatura de LM"
else
echo "$nombre, no estás matriculado en LM"
tipoAsignatura "Quieres añadir LM (s/n)?: " "/LM" "LM añadido correctamente"
fi

if [[ "$PDM" == "PDM" ]]; then
echo "$nombre, ya estás matriculado en la asignatura de PDM"
else
echo "$nombre, no estás matriculado en PDM"
tipoAsignatura "Quieres añadir PDM (s/n)?: " "/PDM" "PDM añadido correctamente"
fi

if [[ "$Ingles" == "Inglés" ]]; then
echo "$nombre, ya estás matriculado en la asignatura de Inglés"
else
echo "$nombre, no estás matriculado en Inglés"
tipoAsignatura "Quieres añadir Inglés (s/n)?: " "/Inglés" "Inglés añadido correctamente"
fi


echo "Asignaturas elegidas: $resultado"
Egibide[$nombre]+="$resultado"
echo "Los datos actualizados de $nombre -->  ${Egibide[$nombre]}"


fi

}



function borrar(){
read -p "Nombre: " nombre
while [[ -z $nombre ]]; do
	echo "ERROR, introduce un nombre !!"
	read -p "Nombre $i: " nombre
done

if comprobarAlumno $nombre; then

unset Egibide[$nombre]
echo "El alumno/a $nombre ha sido borrado/a de Egibide"
fi

}




function modificar() {

read -p "Nombre del alumno/a que quieres modificar sus valores: " nombre
while [[ -z nombre ]]; do
	echo "Error !! introduce un nombre correcto"
	read -p "Nombre del alumno/a: " nombre
done

if comprobarAlumno $nombre; then
echo ""
echo "Introduce los nuevos valores para el alumno/a $nombre: "
echo ""
read -p "Apellidos: " apellidos
while [[ -z $apellidos ]]; do
	echo "ERROR, no has introducido ningún apellido !!"
	read -p "Nombre: " apellidos
done

read -p "Edad: " edad
while [[ -z $edad ]] || ! [[ $edad =~ $comprobar_edad ]]; do
      	echo "ERROR, no has introducido ningúna edad !!"
        read -p "Edad: " edad
done

read -p "Quieres añadir asignaturas a $nombre ? (s/n) ?" respuesta
while [[ $respuesta != "s" ]] && [[ $respuesta != "n" ]]; do
        read -p "Error!! Quieres añadir asignaturas a $nombre ? (s/n) ?" respuesta

done

if [[ $respuesta = "s" ]]; then

resultado=""

echo "Se borran todas las matriculaciones previas y se matricula de nuevo: "
echo ""

echo -e "Elige entre estas asignaturas: \n"
echo -e "\tSistemas Informáticos"
echo -e "\tDesarrollo de Interfaces"
echo -e "\tSistemas Gestión Empresarial"
echo -e "\tLenguaje de marcas"
echo -e "\tProgramación dispositivos móviles"
echo -e "\tInglés\n"

tipoAsignatura "Quieres añadir SI (s/n)?: " "/SI" "SI añadido correctamente"
tipoAsignatura "Quieres añadir DI (s/n)?: " "/DI" "DI añadido correctamente"
tipoAsignatura "Quieres añadir SGE (s/n)?: " "/SGE" "SGE añadido correctamente"
tipoAsignatura "Quieres añadir LM (s/n)?: " "/LM" "LM añadido correctamente"
tipoAsignatura "Quieres añadir PDM (s/n)?: " "/PDM" "PDM añadido correctamente"
tipoAsignatura "Quieres añadir Inglés (s/n): ? " "/Inglés" "Inglés añadido correctamente"

echo "Asignaturas elegidas: $resultado"


Egibide[$nombre]="$nombre/$apellidos/$edad$resultado"
echo "Los nuevos datos actualizados de $nombre -->  ${Egibide[$nombre]}"

else

Egibide[$nombre]="$nombre/$apellidos/$edad"
echo "Los nuevos datos actualizados de $nombre -->  ${Egibide[$nombre]}"

fi
fi

}



function visualizarAlumnos(){
echo ""
echo "Estos son los alumnos/as que hay en Egibide: "
echo ""

echo -e "NOMBRE  APELLIDOS EDAD ASIGNATURAS"
echo ""
for alumno in ${Egibide[@]}; do
	echo $alumno
done
}

function visualizarAlumno(){

echo ""
read -p "Nombre: " nombre
while [[ -z $nombre ]]; do
        echo "ERROR, introduce un nombre !!"
        read -p "Nombre $i: " nombre
done

if comprobarAlumno $nombre; then
echo ""
echo -e "Estos son los datos del alumno/a $nombre: "
echo ""
echo -e "NOMBRE  APELLIDOS EDAD ASIGNATURAS"
echo ${Egibide[$nombre]}
echo ""

fi
}



# ************************* INICIO MENÜ **********************************

echo ""
echo -e "Elige una opción del menú: \n "
echo -e "\t1 Matricular alumnos/as"
echo -e "\t2 Añadir asignaturas a alumno/a"
echo -e "\t3 Modificar alumno/a"
echo -e "\t4 Borrar alumno/a"
echo -e "\t5 Visualizar todo alumnado"
echo -e "\t6 Visualizar datos alumno/a"
echo -e "\t7 Salir\n"

read -p "Opción: " opcion
opciones=^-?[1-7]$''
if ! [[ $opcion =~ $opciones ]] ; then
   echo "ERROR: No es una opción correcta !"
fi


while  [[ $opcion  -ne 7 ]]; do


	case $opcion in

		1)

		nuevosAlumnos

		;;
		2)

		comprobarAsignaturas

		;;
		3)

		modificar 


		;;
		4)

		borrar

		;;
                5)

		visualizarAlumnos
                ;;
                6)

                visualizarAlumno
                ;;
		*)

		echo "Error !!... elige una opción correcta"

	esac

echo ""
echo -e "Elige una opción del menú: \n "
echo -e "\t1 Matricular alumnos/as"
echo -e "\t2 Añadir asignaturas a alumno/a"
echo -e "\t3 Modificar alumno/a"
echo -e "\t4 Borrar alumno/a"
echo -e "\t5 Visualizar todo alumnado"
echo -e "\t6 Visualizar datos alumno/a"
echo -e "\t7 Salir\n"

read -p "Opcion: " opcion


done

echo ""
echo "Adios... te esperamos el próximo curso, pero sin mascarilla"
echo ""
