for $disco in (/Valoraciones/DatosValoracion) where $disco/persona/DatosPersona[nombrePersona='Juanan']
return concat ('ID: ', $disco/IDEscucha, ',  Usuario: ',$disco/persona/DatosPersona/nombrePersona/text(),', Disco: ','"',$disco/disco/DatosDisco/nombreDisco/text(),'", Puntuación: ', $disco/puntuacionDisco)