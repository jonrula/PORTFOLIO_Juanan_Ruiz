//
//  viewModel.swift
//  ProyectoCovid19
//
//  Created by Juanan on 05/02/2021.
//

/*
 Diferentes formas de cargar el picker, con diccionarios (al final este no vale, pues lo que reconoce el picker es un 'id' del array
 
 OPCION A --> Diccionario
 
@Published var datos: [String: Datos] = [:]
Inicializo 'diaSeleccionado' como un diccionario de tipo [String : Datos], y lo inicializo a 0, como sino se hubiese escogido nada.
@Published var diaSeleccionado = ["Ningún día": 0]

@Published var diaSeleccionado2 = ["Ningún día": Datos (id: "Ninguno",pcrTestCount: 0, serologyTestCount: 0, uniquePersonCount: 0, pcrUniquePersonCount: 0, pcrUniquePersonCountByMillionPeople: 0, pcrPositiveCount: 0, serologyPositiveCount: 0, totalPositiveCount: 0, pcrPositiveCountAraba: 0, pcrPositiveCountBizkaia: 0, pcrPositiveCountGipuzkoa: 0, pcrPositiveCountOther: 0, recoveredCount: 0, notRecoveredCount: 0, deceasedCount: 0, newHospitalAdmissionsWithPCRCount: 0, hospitalAdmissionsWithPCRCount: 0, icuPeopleCount: 0, r0: 0, goneNegative: 0, hospitalReleasedCount: 0, icuNewAdmissionsCountLast14Days: 0, by100ThousandPeoplePositiveRateAR: 0, by100ThousandPeoplePositiveRateBIZ: 0, by100ThousandPeoplePositiveRateGI: 0)]
 
    Declaro un diccionario vacío:
    var diasSpiker: [String: Datos] = [:]
 
    let dias = json["byDate"][0] // Me coge todos los dias del documento
 
    Recorro todos los 'dias' con un 'for'y los voy metiendo en un diccionario, con la clave de fecha y el valor:
            for _ in dias {
                let dia = json["byDate"][contador]["date"].stringValue
                let pcr1 = json["byDate"][contador]["pcrTestCount"].intValue
                .
                .
                .
            }
 
    Voy instanciando objetos 'Datos', por cada vuelta del foreach (día) --> fecha: json["byDate"][0]["date"]
 
    let datosLeidos = Datos(id: dia,
                    pcrTestCount: pcr1,
                    serologyTestCount: pcr2)
   
    Lo voy añadiendo los Valores al diccionario los 'datosleidos' que es un objeto de tipo Dato:
    diasSpyker[dia del Json] = datosleidos
 
    Ahora lleno el diccionario que he declarado fuera de la función y que estaba vacío -->  '@Published var datos: [String: Datos] = [:]'  --> lo relleno con los datos dediccionario diasSpyker:
 
    self.datos = diasSpiker
 */


import Foundation
import Alamofire
import SwiftyJSON
import Charts
import UTMConversion
import CoreLocation


// Clase de tipo 'Datos' que corresponde a los objetos que se compone 'Días' en el picker
struct Datos: Identifiable, Hashable, CustomStringConvertible {

    var id: String // Fecha

    var description: String {
        return "\(id)"
    }
    
    var posicion: Int

    var pcrTestCount: Int
    var serologyTestCount: Int
    var uniquePersonCount: Int
    var pcrUniquePersonCount: Int
    var pcrUniquePersonCountByMillionPeople: Int
    var pcrPositiveCount: Int
    var serologyPositiveCount: Int
    var totalPositiveCount: Int
    var pcrPositiveCountAraba: Int
    var pcrPositiveCountBizkaia: Int
    var pcrPositiveCountGipuzkoa: Int
    var pcrPositiveCountOther: Int
    var recoveredCount: Int
    var notRecoveredCount: Int
    var deceasedCount: Int
    var newHospitalAdmissionsWithPCRCount: Int
    var hospitalAdmissionsWithPCRCount: Int
    var icuPeopleCount: Int
    var r0: Double
    var goneNegative: Int
    var hospitalReleasedCount: Int
    var icuNewAdmissionsCountLast14Days: Int
    var by100ThousandPeoplePositiveRateAR: Double
    var by100ThousandPeoplePositiveRateBIZ: Double
    var by100ThousandPeoplePositiveRateGI: Double
}


// Clase de tipo 'Datos2' que corresponde a los objetos que se compone 'Provincias' en el picker

struct Datos2: Identifiable, Hashable, CustomStringConvertible {

    var id: String // Fecha

    var description: String {
        return "\(id)"
    }
    
    var posicion: Int

    var positiveCount: Int
    var aggregatedIncidence: Double
    var aggregatedIncidenceAR: Double
    var aggregatedIncidenceBIZ: Double
    var aggregatedIncidenceGI: Double

}

// Clase de tipo 'Datos3' que corresponde a los objetos que se compone 'Fallecidos' en el picker

struct Datos3: Identifiable, Hashable, CustomStringConvertible {

    var id: String // Municipio

    var description: String {
        return "\(id)"
    }
    
    var posicion: Int
    var deceasedCount: Int


}


// Clase de tipo 'Datos4' que corresponde a los objetos que se compone 'Municipios' en el picker

struct Datos4: Identifiable, Hashable, CustomStringConvertible {
    

    var id: String // Municipio

    var description: String {
        return "\(id)"
    }
  
    var dia : String
    var officialName: String
    var positiveCount: Int
    
    
    var coordenadaX: Double
    var coordenadaY: Double
    

}



class ViewModel: ObservableObject {


    // OPCION B --> Array, declaro arrays y los lleno con los datos del array que me devuelve Alamofire (para tenerlos en local y n llamar continuamente a internet)
    
    // Declaro un array para los gráficos 'datosGrafico', otro array de tipo 'Datos' para cuando arranque la app y crear una variable de tipo 'Datos' que se irá cargando con lo que seleccionamos en el 'picker'
    @Published var datosGrafico = [Bar(entries: [])]
    @Published var datos = [Datos] ()
    @Published var diaSeleccionado = Datos(id: "Ninguno",
        posicion: 1,
        pcrTestCount: 0,
        serologyTestCount: 0,
        uniquePersonCount: 0,
        pcrUniquePersonCount: 0,
        pcrUniquePersonCountByMillionPeople: 0,
        pcrPositiveCount: 0,
        serologyPositiveCount: 0,
        totalPositiveCount: 0,
        pcrPositiveCountAraba: 0,
        pcrPositiveCountBizkaia: 0,
        pcrPositiveCountGipuzkoa: 0,
        pcrPositiveCountOther: 0,
        recoveredCount: 0,
        notRecoveredCount: 0,
        deceasedCount: 0,
        newHospitalAdmissionsWithPCRCount: 0,
        hospitalAdmissionsWithPCRCount: 0,
        icuPeopleCount: 0,
        r0: 0,
        goneNegative: 0,
        hospitalReleasedCount: 0,
        icuNewAdmissionsCountLast14Days: 0,
        by100ThousandPeoplePositiveRateAR: 0,
        by100ThousandPeoplePositiveRateBIZ: 0,
        by100ThousandPeoplePositiveRateGI: 0)
    
    // Declaro un array para los gráficos 'datosGrafico2', otro array de tipo 'Datos2' para cuando arranque la app y crear una variable de tipo 'Datos2' que se irá cargando con lo que seleccionamos en el 'picker'
    @Published var datosGrafico2 = [Bar(entries: [])]
    @Published var datos2 = [Datos2] ()
    @Published var diaSeleccionado2 = Datos2(id: "Ninguno",
                                             posicion: 1,
                                             positiveCount: 0,
                                             aggregatedIncidence: 0,
                                             aggregatedIncidenceAR: 0,
                                             aggregatedIncidenceBIZ: 0,
                                             aggregatedIncidenceGI: 0 )
    
    
    // Declaro un array para los gráficos 'datosGrafico3', otro array de tipo 'Datos3' para cuando arranque la app y crear una variable de tipo 'Datos3' que se irá cargando con lo que seleccionamos en el 'picker'
    @Published var datosGrafico3 = [Bar(entries: [])]
    @Published var datos3 = [Datos3] ()
    @Published var diaSeleccionado3 = Datos3(id: "Ninguno",
                                             posicion: 1,
                                             deceasedCount: 0)
    
    // Declaro un array de tipo 'Datos4' para cuando arranque la app y crear una variable de tipo 'Datos4' que se irá cargando con lo que seleccionamos en el 'picker'
    
    
    @Published var datos4 = [Datos4] ()
    @Published var diaSeleccionado4 = Datos4(id: "Ninguno",
                                             dia: "",
                                             officialName: "",
                                             positiveCount: 0,
                                             coordenadaX:42.8524,
                                             coordenadaY: -2.38946) // Coordenadas de Agurain
    
    
    // Declaro una variable booleana para controlar el momento en que se cargan los datos y sacar un Progressview mientras tanto...
    @Published var cargandoDatos = false

    
    // FUNCIONES PARA CARGAR LOS DATOS DE LOS 4 FICHEROS JSON:
  

    func cargarDatos() {
        
        /*
         Comprobacions 'PhpStorm':
         /byDate/118/by100ThousandPeoplePositiveRateAR
         / Voy a contar a partir del día 11, donde empieza haber datos -->  368 días totales -11 = 250 (empiezo a contar aquí)
         
         
         */
        
  

      

        AF.request("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/covid_19_2020/opendata/generated/covid19-epidemic-status.json", method: .get).validate().responseJSON { response in

           

            switch response.result {
            case .success(let value):
                let json = JSON(value)
                let todosLosdias = json["byDate"]  // --> Me coge todos los días del documento Json --> 368 días
                
                /*
                 Comprobaciones:
                 
                 let dias = json["byDate"][0] // Me coge los datos que hay en el primer día, que son 26 datos
                 let dia = json["byDate"][0]["date"]  // --> Me coge la fecha del primer día
                 print("Datos PCR: \(dias)") // Esto datos me salen solo en la consola
                 print(todosLosdias.count) --> 368 días
                 print(dias.count) --> 26 datos
                
                 */




                // OPCION B --> Array ( con esta opción , el picker me reconoce el id del array, con el diccionario, no)
                // La variable 'contador', va ir recorriendo cada elemento del array, que son los días qu le he limitado con un rango, para que no salgan muchos --> 'dias.count-10..<dias.count'

                var diasCovid19 = [Datos] ()
    
                var datosCharts = [Bar(entries: [])]
                var nuevaPosicion: Int = 0
               
                for contador in todosLosdias.count-250..<todosLosdias.count-240 {
                    
                    nuevaPosicion += 1
                   
                    let dia = json["byDate"][contador]["date"].stringValue
                    
                    let pcr1 = json["byDate"][contador]["pcrTestCount"].intValue
                    let pcr2 = json["byDate"][contador]["serologyTestCount"].intValue
                    let pcr3 = json["byDate"][contador]["uniquePersonCount"].intValue
                    let pcr4 = json["byDate"][contador]["pcrUniquePersonCount"].intValue
                    let pcr5 = json["byDate"][contador]["pcrUniquePersonCountByMillionPeople"].intValue
                    let pcr6 = json["byDate"][contador]["pcrPositiveCount"].intValue
                    let pcr7 = json["byDate"][contador]["serologyPositiveCount"].intValue
                    let pcr8 = json["byDate"][contador]["totalPositiveCount"].intValue
                    let pcr9 = json["byDate"][contador]["pcrPositiveCountAraba"].intValue
                    let pcr10 = json["byDate"][contador]["pcrPositiveCountBizkaia"].intValue
                    let pcr11 = json["byDate"][contador]["pcrPositiveCountGipuzkoa"].intValue
                    let pcr12 = json["byDate"][contador]["pcrPositiveCountOther"].intValue
                    let pcr13 = json["byDate"][contador]["recoveredCount"].intValue
                    let pcr14 = json["byDate"][contador]["notRecoveredCount"].intValue
                    let pcr15 = json["byDate"][contador]["deceasedCount"].intValue
                    let pcr16 = json["byDate"][contador]["newHospitalAdmissionsWithPCRCount"].intValue
                    let pcr17 = json["byDate"][contador]["hospitalAdmissionsWithPCRCount"].intValue
                    let pcr18 = json["byDate"][contador]["icuPeopleCount"].intValue
                    let pcr19 = json["byDate"][contador]["r0"].doubleValue
                    let pcr20 = json["byDate"][contador]["goneNegative"].intValue
                    let pcr21 = json["byDate"][contador]["hospitalReleasedCount"].intValue
                    let pcr22 = json["byDate"][contador]["icuNewAdmissionsCountLast14Days"].intValue
                    let pcr23 = json["byDate"][contador]["by100ThousandPeoplePositiveRateAR"].doubleValue
                    let pcr24 = json["byDate"][contador]["by100ThousandPeoplePositiveRateBIZ"].doubleValue
                    let pcr25 = json["byDate"][contador]["by100ThousandPeoplePositiveRateGI"].doubleValue


                    // Instancio un objeto de tipo 'Datos' y le voy metiendo todos sus atributos que me pide la clase 'Datos' por cada vuelta del for (que es cada día,limitado por un rango que marca el contador):
                    // let datosLeidos = Datos (dia, pcr1,pcr2,pcr3....)


                    let datosLeidos = Datos(id: dia,
                        posicion:  nuevaPosicion,
                        pcrTestCount: pcr1,
                        serologyTestCount: pcr2,
                        uniquePersonCount: pcr3,
                        pcrUniquePersonCount: pcr4,
                        pcrUniquePersonCountByMillionPeople: pcr5,
                        pcrPositiveCount: pcr6,
                        serologyPositiveCount: pcr7,
                        totalPositiveCount: pcr8,
                        pcrPositiveCountAraba: pcr9,
                        pcrPositiveCountBizkaia: pcr10,
                        pcrPositiveCountGipuzkoa: pcr11,
                        pcrPositiveCountOther: pcr12,
                        recoveredCount: pcr13,
                        notRecoveredCount: pcr14,
                        deceasedCount: pcr15,
                        newHospitalAdmissionsWithPCRCount: pcr16,
                        hospitalAdmissionsWithPCRCount: pcr17,
                        icuPeopleCount: pcr18,
                        r0: pcr19,
                        goneNegative: pcr20,
                        hospitalReleasedCount: pcr21,
                        icuNewAdmissionsCountLast14Days: pcr22,
                        by100ThousandPeoplePositiveRateAR: pcr23,
                        by100ThousandPeoplePositiveRateBIZ: pcr24,
                        by100ThousandPeoplePositiveRateGI: pcr25)


                    // Voy añadiendo los 'datosLeidos' por cada vuelta del for (día) al array 'diascovid19'
                    // Le paso los valores  del array 'diascovid19' al array datos, que he declarado fuera de la función, porque no me deja retornar los valores de 'diasCovid19'
                    
  
                    let datosLeidosGrafico = Bar(entries: [
                                //x - position of a bar, y - height of a bar
                                BarChartDataEntry(x: 1, y: pcr23),
                                BarChartDataEntry(x: 2, y: pcr24),
                                BarChartDataEntry(x: 3, y: pcr25)
                    ])
              
                    
                    datosCharts.append(datosLeidosGrafico)
                    self.datosGrafico = datosCharts
                    
                    
                
                    diasCovid19.append(datosLeidos)
                    self.datos = diasCovid19
                    
                
               
                }
            


            case .failure(let error):
                print(error)
            }

        }

    }


    func cargarDatos2() {
        
        /*
         
         Rutas sacadas del 'PhpStorm'
         /byDate/9/aggregatedIncidence
         /byDate/0/aggregatedIncidence
         /byDate/0/date
         /byDate
         
         */

  
        
        AF.request("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/covid_19_2020/opendata/generated/covid19-pcr.json", method: .get).validate().responseJSON { response in


            switch response.result {
            case .success(let value):
                let json = JSON(value)
                let dias = json["byDate"] // Me coge todos los dias del documento
                
                /*
                 Comprobaciones:
                 
                 let dia = json["byDate"][0] // Me coge los datos del primer día del documento
                 print("Datos Provincias: \(dias)") // Esto datos me salen solo en la consola
                 print(dia.count) --> 6 días
                 print(dias.count) --> 362 días
                 Voy a recorrer a partir del día 353 hasta 343
                 
                 */
    


                var diasCovid19 = [Datos2] ()
                
                var datosCharts = [Bar(entries: [])]
                var nuevaPosicion: Int = 0
             

                for contador in dias.count-353..<dias.count-343 {
                    
                    nuevaPosicion += 1

                    let dia = json["byDate"][contador]["date"].stringValue // este es el id, que es la fecha

                    let pcr1 = json["byDate"][contador]["positiveCount"].intValue
                    let pcr2 = json["byDate"][contador]["aggregatedIncidence"].doubleValue
                    let pcr3 = json["byDate"][contador]["aggregatedIncidenceAR"].doubleValue
                    let pcr4 = json["byDate"][contador]["aggregatedIncidenceBIZ"].doubleValue
                    let pcr5 = json["byDate"][contador]["aggregatedIncidenceGI"].doubleValue
                  


                    let datosLeidos = Datos2(id: dia, // id --> le paso al 'id' la fecha, que es el que se carga en el picker
                        posicion: nuevaPosicion,
                        positiveCount: pcr1,
                        aggregatedIncidence: pcr2,
                        aggregatedIncidenceAR: pcr3,
                        aggregatedIncidenceBIZ: pcr4,
                        aggregatedIncidenceGI: pcr5
                      
                    )
                    
                    let datosLeidosGrafico = Bar(entries: [
                                //x - position of a bar, y - height of a bar
                                BarChartDataEntry(x: 1, y: Double(pcr1)),
                                BarChartDataEntry(x: 2, y: pcr2),
                                BarChartDataEntry(x: 3, y: pcr3),
                                BarChartDataEntry(x: 4, y: pcr4),
                                BarChartDataEntry(x: 5, y: pcr5)
                    ])
              
                    
                    datosCharts.append(datosLeidosGrafico)
                    self.datosGrafico2 = datosCharts
                    
                    

                    diasCovid19.append(datosLeidos)
                    self.datos2 = diasCovid19

                }


            case .failure(let error):
                print(error)
            }

        }

    }
    
    func cargarDatos3() {
        
        /*
         
         Rutas sacada del 'PhpStorm':
         
         //byDate/0/date
         /byDate/39/deceasedCount
         
         */
        
        AF.request("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/covid_19_2020/opendata/generated/covid19-deceasedPeopleCount.json", method: .get).validate().responseJSON { response in


            switch response.result {
            case .success(let value):
                let json = JSON(value)
                let dias = json["byDate"] // Me coge todos los dias del documento
                
                /*
                 Comprobaciones:
                 
                 print("Datos Fallecidos: \(dias)") // Esto datos me salen solo en la consola
                 print(dias.count) // --> 351 días
                 
                 */
                
            
                var diasCovid19 = [Datos3] ()
            
                var datosCharts = [Bar(entries: [])]
                var datoChartDataEntry : [BarChartDataEntry] = []
                var nuevaPosicion: Int = 0
             
                

                for contador in dias.count-310..<dias.count-300 {
                    
                    nuevaPosicion += 1

                    let dia = json["byDate"][contador]["date"].stringValue // este es el id, que es la fecha
                    let fallecidos = json["byDate"][contador]["deceasedCount"].intValue
                    let datosLeidos = Datos3(id: dia,  // id --> le paso al 'id' la fecha, que es el que se carga en el picker
                                             posicion: nuevaPosicion,
                                             deceasedCount: fallecidos)
                    
                    let datos = BarChartDataEntry(x: Double(nuevaPosicion), y: Double(fallecidos))
                    datoChartDataEntry.append(datos)
                    
    
                    let datosLeidosGrafico = Bar(entries: datoChartDataEntry)
                    
                    datosCharts.append(datosLeidosGrafico)
                    self.datosGrafico3 = datosCharts
                    
                    
                    diasCovid19.append(datosLeidos)
                    self.datos3 = diasCovid19

                }
      
                

            case .failure(let error):
                print(error)
            }

        }

    }
    
    func cargarDatos4() {
        
        /*
         Comprobaciones de rutas sacadas del 'PhpStorm'
         
          /newPositivesByDateByMunicipality/0/date
          /newPositivesByDateByMunicipality/0/items/0/geoMunicipality/officialName
          /newPositivesByDateByMunicipality/0/items/0/positiveCount
          /newPositivesByDateByMunicipality/0/items/0/geoMunicipality/geoPosition2D/x
          /newPositivesByDateByMunicipality/0/items/0/geoMunicipality/geoPosition2D/y
          /newPositivesByDateByMunicipality/0/items/4/geoMunicipality/officialName
          /newPositivesByDateByMunicipality/0/items/4/positiveCount
          /newPositivesByDateByMunicipality/0/items/6/geoMunicipality/geoPosition2D/x
         
        
         */
        

        
        cargandoDatos = true

        AF.request("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/covid_19_2020/opendata/generated/covid19-bymunicipality.json", method: .get).validate().responseString{ response in
            
            self.cargandoDatos = false
            
            switch response.result {
            case .success(let value):
                
                /*
                 // Si Alamofire nos devuelve un error = 3840 , tiene que ver con la codificación, hay que cambiar 'reponseJSON' por 'responseString' y añadir las 2 líneas siguientes:
                 let dataFromString = value.data(using: .utf8, allowLossyConversion: false)
                 let json = try JSON(data: dataFromString!)
                 
                 */
    
                
                do {
                    let dataFromString = value.data(using: .utf8, allowLossyConversion: false)
                    let json = try JSON(data: dataFromString!)
              
                
                    let municipios = json["newPositivesByDateByMunicipality"][0]["items"]// Me coge todos los municipios del mismo dia, solo voy a recorrer este array, el de los municipios del primer día
                    
                    /*
                     
                     Comprobaciones:
                     let dias = json["newPositivesByDateByMunicipality"][0] // Me coge el primer dia del documento, con todos sus municipios
                     
                     // Esto es para sacar ejemplos por pantalla, a ver si lee algo:
                    
                     let municipio1 = json["newPositivesByDateByMunicipality"][0]["items"][0]["geoMunicipality"]["officialName"].stringValue
                     print("Primer municipio: \(municipio1)")
                     print("Número de municipios: \(municipios.count)")
                     print("Datos Municipios: \(municipios)") // Esto datos me salen solo en la consola
                     
                     */
                    
       

                    var diasCovid19 = [Datos4] ()
                   
                    // Solo voy a leer los resultados del primer día de todos los municipios, porque el archivo es muy grande
                 
                    for contadorMunicipios in municipios.count-250..<municipios.count-245{
                       
                        let dia = json["newPositivesByDateByMunicipality"][0]["date"].stringValue

                        let municipio = json["newPositivesByDateByMunicipality"][0]["items"][contadorMunicipios]["geoMunicipality"]["officialName"].stringValue
                        let positivos = json["newPositivesByDateByMunicipality"][0]["items"][contadorMunicipios]["positiveCount"].intValue
                        let x = json["newPositivesByDateByMunicipality"][0]["items"][contadorMunicipios]["geoMunicipality"]["geoPosition2D"]["x"].doubleValue
                        let y = json["newPositivesByDateByMunicipality"][0]["items"][contadorMunicipios]["geoMunicipality"]["geoPosition2D"]["y"].doubleValue
                        
                        
                        /*
                         Para hacer la conversión de las coordenadas UTM que recojemos del archivo Json a coordenadas geográficas: latitud y longitud.
                         https://cocoapods.org/pods/UTMConversion
                         
                         */
                    
                        
                        let utmCoordinate = UTMCoordinate(northing: y, easting: x, zone: 30, hemisphere: .northern)
                        let coordinate = utmCoordinate.coordinate()
                        
                        /*
                         Comprobaciones de las coordenadas si están bien:
                         print(coordinate)
                         
                         Me va listando los municipios y coordenadas:
                         print("Municipio\(contadorMunicipios+1): \(municipio) UTM X: \(x) UTM Y: \(y)  Latitud: \(coordinate.latitude) Longitud: \(coordinate.longitude)")
                         
                         */
                        
                   
                        let datosLeidos = Datos4(id: municipio, // id --> Municipio, lo que se carga en el picker
                                                 dia: dia,
                                                 officialName: municipio,
                                                 positiveCount: positivos,
                                                 coordenadaX: coordinate.latitude,
                                                 coordenadaY: coordinate.longitude)
                        
                
                        diasCovid19.append(datosLeidos)
                        self.datos4 = diasCovid19
                        
                    
                    }
                    
                   
              

                } catch {
                    print("Error")
                }
                
                

            case .failure(let error):
               
            
                print(error)
                
            }

        }

    }
}
