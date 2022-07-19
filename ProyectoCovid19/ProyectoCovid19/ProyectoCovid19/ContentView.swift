//
//  ContentView.swift
//  ProyectoCovid19
//
//  Created by Juanan on 29/01/2021.
//


/*
 
 OPCION A --> DICCIONARIO (cargar las claves  del diccionario en el picker, pero no los reconoce, solo funciona con arrays)
 Cargo el diccionario 'datos' que tengo en el view model -->   '@Published var datos: [String: Datos] = [:]' que ya lo he cargado con todos los días al apretar el boton del emoticono --> v.cargarDatos()
 Con el foreach recorro todos los días del diccionario 'datos[dias:Datos]' en este caso , le digo al picker que me muestre las claves (\keys), que son los días del diccionario
 Picker("Día seleccionado", selection: $vm.diaSeleccionado) {

     ForEach(vm.datos, id: \.self) { dato in

         Text(\keys)
        
     }
 }
 
 */



// Importamos las librerías que vamos a utilizar:

import SwiftUI
import Charts
import MapKit
import CoreLocation
import UTMConversion

// Variables globales, los colores corporativos que voy a utilizar en el proyecto:

let azulClaro = UIColor(red: 119 / 255, green: 181 / 255, blue: 254 / 255, alpha: 1.0)
let verdeEsmeralda = UIColor(red: 0, green: 153 / 255, blue: 117 / 255, alpha: 1.0)


// Los diferentes casos del primer picker:
enum Tema: String, CaseIterable, Identifiable {
    case Dias
    case Provincias
    case Fallecidos
    case Municipios

    var id: String { self.rawValue }
}


struct ContentView: View {
    @State private var selectedTema = Tema.Dias
    @ObservedObject var vm = ViewModel()

    @State private var graficoVisible = false


    /*
     
     // MapKit de ejemplo con un valor fijo (coordenadas Agurain), más abjo por variables binding)
     
     @State private var region = MKCoordinateRegion(center: CLLocationCoordinate2D(latitude: 42.8524, longitude: -2.38946), span: MKCoordinateSpan(latitudeDelta: 0.05, longitudeDelta: 0.05))
     */




    var body: some View {


        //REF:https://opendata.euskadi.eus/contenidos/ds_informes_estudios/covid_19_2020/opendata/generated/covid19-epidemic-status.json


        ZStack {

            // Color de fondo
            Color.init(azulClaro)
                .ignoresSafeArea()


            VStack {
                Spacer()

                Picker("Día", selection: $selectedTema) {
                    Text("Días").tag(Tema.Dias)
                    Text("Provincias").tag(Tema.Provincias)
                    Text("Fallecidos").tag(Tema.Fallecidos)
                    Text("Municipios").tag(Tema.Municipios)
                    
                }
             


                // REF: https://stackoverflow.com/a/58676925
                .onReceive([self.selectedTema].publisher.first()) { (value) in
                    print(value)
                }.pickerStyle(SegmentedPickerStyle())
                    .padding()
                  
                
              
                
               

                Spacer()

                if vm.cargandoDatos {
                    ProgressView("Cargando...")



                }



                VStack {


                    if selectedTema == Tema.Dias {
                        Picker("Día seleccionado", selection: $vm.diaSeleccionado) {

                            ForEach(vm.datos, id: \.self) { dato in
                                Text(dato.description.prefix(10)) // 'prefix(10)' Solo aparecen los primeros 10 primeros carácteres de la cadena
                                .background(Color.init(verdeEsmeralda))


                            }
                        }.frame(height: 150)



                    }



                    if selectedTema == Tema.Provincias {
                        Picker("Día seleccionado", selection: $vm.diaSeleccionado2) {
                            ForEach(vm.datos2, id: \.self) { dato in
                                Text(dato.description.prefix(10))
                                    .background(Color.init(verdeEsmeralda))


                            }
                        }.frame(height: 150)


                    }




                    if selectedTema == Tema.Fallecidos {
                        Picker("Día seleccionado", selection: $vm.diaSeleccionado3) {
                            ForEach(vm.datos3, id: \.self) { dato in
                                Text(dato.description.prefix(10))
                                    .background(Color.init(verdeEsmeralda))

                            }
                        }.frame(height: 150)
                        

                    }



                    if selectedTema == Tema.Municipios {
                        Picker("Día seleccionado", selection: $vm.diaSeleccionado4) {
                            ForEach(vm.datos4, id: \.self) { dato in
                                Text(dato.description)
                                    .background(Color.init(verdeEsmeralda))
                            }
                        }

                    }


                 
            
                    if selectedTema == Tema.Dias {
                        

                        if graficoVisible == true {
                            Text("Casos por 100.000 en ARABA, BIZKAIA y GIPUZKOA")
                            Text("Día seleccionado: \(vm.diaSeleccionado.description)".prefix(28))
                            let indice: Int = vm.diaSeleccionado.posicion
                            Grafico(relacionTema: $selectedTema, dias: $vm.datosGrafico[indice])
                            
                        } else {
                            Text("Día seleccionado: \(vm.diaSeleccionado.description)".prefix(28)) // 'prefix(28)' Solo aparecen los primeros 28 primeros carácteres de la cadena
                            
                            ZStack {
                                Resultado(relacionTema: $selectedTema)
                                VStack (alignment: .leading) { // Alineo el Vstack a la izquierda
                                    Text("Tests : \(vm.diaSeleccionado.pcrTestCount)")
                                    Text("Tests serológicos: \(vm.diaSeleccionado.serologyTestCount)")
                                    Text("Personas : \(vm.diaSeleccionado.uniquePersonCount)")
                                    Text("PCR por personas : \(vm.diaSeleccionado.uniquePersonCount)")
                                    Text("Casos por millón : \(vm.diaSeleccionado.pcrUniquePersonCountByMillionPeople)")
                                    Text("PCR positivos: : \(vm.diaSeleccionado.pcrPositiveCount)")
                                    Text("Total positivos: \(vm.diaSeleccionado.totalPositiveCount)")
                                    Text(String(format:"Casos/10⁵ en ARABA: %.2f",  vm.diaSeleccionado.by100ThousandPeoplePositiveRateAR))
                                    Text(String(format:"Casos/10⁵ en BIZKAIA: %.2f",vm.diaSeleccionado.by100ThousandPeoplePositiveRateBIZ))
                                    Text(String(format:"Casos/10⁵ GIPUZKOA: %.2f",vm.diaSeleccionado.by100ThousandPeoplePositiveRateGI))
                                }


                            }

                        }


                    }


                    if selectedTema == Tema.Provincias {
                       
                        if graficoVisible == true {
                            Text("Positivos e incidencias en ARABA, BIZKAIA y GIPUZKOA ")
                            Text("Día seleccionado: \(vm.diaSeleccionado2.description)".prefix(28))
                            
                            let indice: Int = vm.diaSeleccionado2.posicion
                            Grafico(relacionTema: $selectedTema, dias: $vm.datosGrafico2[indice])
                            
                            
                        }else{
                            Text("Día seleccionado: \(vm.diaSeleccionado2.description)".prefix(28))
                            ZStack {
                                Resultado(relacionTema: $selectedTema)
                                VStack (alignment: .leading) {
                                    Text("Positivos : \(vm.diaSeleccionado2.positiveCount)")
                                    Text(String(format:"Nuevas Incidencias: %.2f", vm.diaSeleccionado2.aggregatedIncidence))
                                    Text(String(format:"Incidencias Araba: %.2f",vm.diaSeleccionado2.aggregatedIncidenceAR))
                                    Text(String(format:"Incidencias Bizkaia: %.2f",vm.diaSeleccionado2.aggregatedIncidenceBIZ))
                                    Text(String(format:"Incidencias Gipuzkoa: %.2f",vm.diaSeleccionado2.aggregatedIncidenceGI))


                                }

                            }
                            
                            
                        }
                        
                    }

                  
                    if selectedTema == Tema.Fallecidos {
          
                    
                        if graficoVisible == true {
                          
                            Text("Fallecidos entre el 2020-04-18 al \(vm.diaSeleccionado3.description)".prefix(44))
                            Text("")
                            
                            let indice: Int = vm.diaSeleccionado3.posicion
                            Grafico(relacionTema: $selectedTema, dias: $vm.datosGrafico3[indice])
                            
                        }else{
                            Text("Día seleccionado: \(vm.diaSeleccionado3.description)".prefix(28))
                            ZStack {
                                Resultado(relacionTema: $selectedTema)
                                VStack (alignment: .leading) {
                                    Text("Fallecidos : \(vm.diaSeleccionado3.deceasedCount)")

                                }

                            }
                            
                            
                            
                        }


                    }



                    if selectedTema == Tema.Municipios {
                        Text("Municipio seleccionado: \(vm.diaSeleccionado4.description)".prefix(47))

                        ZStack {

                            // REF: https://www.hackingwithswift.com/forums/swiftui/swiftui-map-for-ios14/2633

                            MapView(lat: $vm.diaSeleccionado4.coordenadaX, lon: $vm.diaSeleccionado4.coordenadaY)

                            VStack {
                                Spacer()
                                ZStack {
                                    RoundedRectangle(cornerRadius: 10.0)
                                        .fill(Color.init(verdeEsmeralda))
                                        .frame(width: 250, height: 75, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                                        .opacity(0.7)
                                        .shadow(radius: 5)

                                    VStack (alignment: .leading) {
                                        Text("Día : \(vm.diaSeleccionado4.dia)".prefix(16))
                                        Text("Municipio : \(vm.diaSeleccionado4.officialName)")
                                        Text("Positivos : \(vm.diaSeleccionado4.positiveCount)")
                                    }

                                }
                                Spacer()
                                    .frame(width: /*@START_MENU_TOKEN@*/100/*@END_MENU_TOKEN@*/, height: 20, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)


                            }

                        }
                    }



                } // Aquí acaba el Vstack




                Spacer()




                VStack {

                    if selectedTema == Tema.Fallecidos {
                        Button("😔") {
                            graficoVisible = !graficoVisible

                        }
                            .font(.system(size: 150, weight: .medium))



                    }
                    if selectedTema == Tema.Municipios {
                        Text("🗺️")

                            .font(.system(size: 150, weight: .medium))



                    }
                    if selectedTema == Tema.Provincias {
                        Button("🏠") {
                            graficoVisible = !graficoVisible

                        }
                            .font(.system(size: 150, weight: .medium))



                    }



                    if selectedTema == Tema.Dias {
                        Button("😷") {
                            graficoVisible = !graficoVisible
                        }


                            .font(.system(size: 150, weight: .medium))
                            .hoverEffect(.highlight)

                    }



                }



            } // Aqui acaba el Vstack







        } // Aquí acaba el Zstack


        // Se cargan los datos al arrancar la app y los meto en los respectivos array: Datos1, Datos2, Datos3 y Datos4
            .onAppear() {

            vm.cargarDatos()
            vm.cargarDatos2()
            vm.cargarDatos3()
            vm.cargarDatos4()
                
        }




    }





}


struct Resultado: View {

    @Binding var relacionTema: Tema


    var body: some View {

        ZStack {

            RoundedRectangle(cornerRadius: 10.0)
                .fill(Color.init(verdeEsmeralda))
                .frame(width: 300, height: 250, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                .shadow(radius: 5)
            VStack {
                // Aquí quiero que salgan los resultados y relacionarlos con el contentview, me faltan @bindind y el @state)


            }

        }


    }


}

struct Grafico: View {
    
    @Binding var relacionTema: Tema
    @Binding var dias: Bar
    var body: some View {

        ZStack {

            dias

        }


    }


}







struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()

    }
}


// Para relacionar los binding con MapKit, cuando lo seleccionamos en el picker:

struct MapView: View {


    @Binding private var lat: Double
    @Binding private var lon: Double

    private let initialLatitudinalMetres: Double = 250
    private let initialLongitudinalMetres: Double = 250

    @State private var span: MKCoordinateSpan?

    init(lat: Binding<Double>, lon: Binding<Double>) {
        _lat = lat
        _lon = lon
    }

    private var region: Binding<MKCoordinateRegion> {
        Binding {

            let centre = CLLocationCoordinate2D(latitude: lat, longitude: lon)

            return MKCoordinateRegion(center: centre, span: MKCoordinateSpan(latitudeDelta: 0.2, longitudeDelta: 0.2))

        } set: { region in
        }
    }

    var body: some View {
        Map(coordinateRegion: region)
    }
}

