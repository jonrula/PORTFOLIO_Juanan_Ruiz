for $disco in (/Valoraciones/DatosValoracion) where $disco/disco/DatosDisco[nombreDisco='Thriller']
return concat ('Personas que han escuchado "',$disco/disco/DatosDisco/nombreDisco/text(),'": ', $disco/persona/DatosPersona/nombrePersona/text(),', Puntuación: ', $disco/puntuacionDisco)
