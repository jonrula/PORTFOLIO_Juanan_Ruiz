for $disco in (/Valoraciones/DatosValoracion) where $disco/persona/DatosPersona[nombrePersona='Ana']
return concat ('Discos escuchados por ',$disco/persona/DatosPersona/nombrePersona/text(),': "',$disco/disco/DatosDisco/nombreDisco/text(),'", Puntuación: ', $disco/puntuacionDisco)
