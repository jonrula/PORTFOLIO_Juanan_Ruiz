#!/bin/bash
# menu.sh
# Autor: Juanan Ruiz
clear 
declare -A Egibide
Egibide[Juanan]="Juanan Ruiz 49"


function borrar(){
#if [[ ${#$1[*]} -eq 0 ]]; then

unset Egibide["$1"]
echo "El alumno/ $1 ha sido borrado/a de Egibide"
echo $a
#else
#echo "No hay alumnos/as para borrar en Egibide"

#fi

}

function modificar() {
read -p "Nuevos valores para el alumno/a $1: " valores
while [[ -z valores ]]; do
        echo "Error !! introduce valores"
        read -p "Nuevos valores para el alumno/a $1: " valores
done
Egibide[$1]=$valores

}

function visualizar(){
echo ""
echo "Estos son los alumnos/as que hay en Egibide: "
for alumno in ${Egibide[@]}; do
        echo $alumno
done
}

echo ""
echo -e "Elige una opción del menu: \n "
echo -e "\t1 Matricular alumnos/as"
echo -e "\t2 Añadir asignaturas a alumno/a"
echo -e "\t3 Modificar alumno/a"
echo -e "\t4 Borrar alumno/a"
echo -e "\t5 Visualizar alumnos/as"
echo -e "\t6 Salir\n"

read -p "Opción: " opcion
opciones=^-?[1-6]$''
if ! [[ $opcion =~ $opciones ]] ; then
   echo "ERROR: No es una opción correcta !"
fi
while  [[ $opcion  -ne 5 ]]; do
	case $opcion in
		1)
	 	#./añadirAlumnos.sh
		;;
		2)
		#./eligeAsignaturas.sh
		;;
		3)
		echo "menu3"
		;;
		4)
	 	./borrarAlumno.sh
		;;
		5)
		visualizar
		;;
		6)
		echo "Adios"
		;;
		*)

		echo "Error !!"
	esac

echo ""
echo -e "Elige una opción del menu: \n "
echo -e "\t1 Matricular alumnos/as"
echo -e "\t2 Añadir asignaturas a alumno/a"
echo -e "\t3 Modificar alumno/a"
echo -e "\t4 Borrar alumno/a"
echo -e "\t5 Visualizar alumnos/as"
echo -e "\t6 salir\n"

read -p "Opcion: " opcion

done
