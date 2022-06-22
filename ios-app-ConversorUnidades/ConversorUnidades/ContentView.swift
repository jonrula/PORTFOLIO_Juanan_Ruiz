//
//  ContentView.swift
//  ConversorUnidades
//
//  Created by Ion Jaureguialzo Sarasola on 18/12/20.
//

import SwiftUI

enum Internacional: String, CaseIterable, Identifiable {
    case Centimetros
    case Metros
    case Kilometros

    var id: String { self.rawValue }
}

enum Ingles: String, CaseIterable, Identifiable {
    case Pulgadas
    case Pies
    case Millas

    var id: String { self.rawValue }
}


struct ContentView: View {

    // Variables relacionadas con el picker
    @State private var seleccionInternacional = Internacional.Metros
    @State private var seleccionIngles = Ingles.Pies

    // Variables relacionadas con el slider
    @State private var conversion = 50.0
    @State private var isEditing = false

    var body: some View {

        VStack {

            Picker("SistemaInternacional", selection: $seleccionInternacional) {
                ForEach(Internacional.allCases, id: \.self) { Internacional in
                    Text(Internacional.rawValue.capitalized)
                }
            }
                .pickerStyle(SegmentedPickerStyle())
            
            Spacer()
                .frame(height: 30)

            Slider(
                value: $conversion,
                in: 0...100,
                step: 0.01,
                onEditingChanged: { editing in
                    isEditing = editing
                },
                minimumValueLabel: Text("0"),
                maximumValueLabel: Text("100")
            ) {
                Text("")
            }
            
            Spacer()
                .frame(height: 30)


            Picker("SistemaIngles", selection: $seleccionIngles) {
                ForEach(Ingles.allCases, id: \.self) { Ingles in
                    Text(Ingles.rawValue.capitalized)
                }
            }
                .pickerStyle(SegmentedPickerStyle())

            Spacer()
                .frame(height: 100)

            Circulo(medidaInternacional: $seleccionInternacional, conversion: $seleccionIngles, medidaIngles: $conversion)

        }

            .padding()

    }
}

struct Circulo: View {

    @Binding var medidaInternacional: Internacional // Coge los valores Internacional (centimetros, Metros, Kilometros)
    @Binding var conversion: Ingles // Coge los valores de conversion
    @Binding var medidaIngles: Double


    var body: some View {
        
        ZStack {
	
            Circle()
                .frame(width: 350, height: 350, alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
                .foregroundColor(.purple)

            VStack {
                Text(String(format: "%.2f",medidaIngles) + " " + medidaInternacional.rawValue + " son:")
                    .font(.system(size: 20, weight: .light, design: .serif))

                Spacer()
                    .frame(height: 30)

                Text(String(format:"%.6f",convertir(conversion: conversion, seleccionInternacional:medidaInternacional, dato:medidaIngles)))
                    .font(.system(size: 50, weight: .light, design: .serif))

                Spacer()
                    .frame(height: 30)

                Text(conversion.rawValue)
                    .font(.system(size: 20, weight: .light, design: .serif))

            }

        }

    }

}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

func convertir(conversion: Ingles, seleccionInternacional: Internacional, dato: Double) -> Double {
    var resultado: Double = 0.0
    
    switch seleccionInternacional {
    case .Centimetros:
        
        if conversion == Ingles.Pulgadas {
            resultado = dato / 2.54
        }
        if conversion == Ingles.Pies {
            resultado = dato / 30.48
        }
        if conversion == Ingles.Millas {
            resultado = dato / 160934
        }
    
    case .Metros:
        if conversion == Ingles.Pulgadas {
            resultado = dato * 39.37
        }
        if conversion == Ingles.Pies {
            resultado = dato / 3.281
        }
        if conversion == Ingles.Millas {
            resultado = dato / 1609
        }
    case .Kilometros:
        if conversion == Ingles.Pulgadas {
            resultado = dato * 39370
        }
        if conversion == Ingles.Pies {
            resultado = dato * 3281
        }
        if conversion == Ingles.Millas {
            resultado = dato / 1.609
        }

 
    }
    
    return resultado


}

