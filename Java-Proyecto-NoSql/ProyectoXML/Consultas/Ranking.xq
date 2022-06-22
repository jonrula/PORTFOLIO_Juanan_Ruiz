for $disco in /Valoraciones
return

    for $payment in $disco/DatosValoracion
    let $nombre:= $payment/disco/DatosDisco/nombreDisco
    group by $nombre

    order by sum($payment/puntuacionDisco) descending

    return concat($nombre,'        ',sum($payment/puntuacionDisco))