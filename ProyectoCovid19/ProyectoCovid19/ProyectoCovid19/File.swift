//
//  File.swift
//  ProyectoCovid19
//
//  Created by Juanan on 26/02/2021.
//

//import Foundation

import Charts
import SwiftUI





struct Bar : UIViewRepresentable {
    
    
    let azulClaro = UIColor(red: 119/255, green: 181/255, blue: 254/255, alpha: 1.0)
    let verdeEsmeralda = UIColor(red: 0, green: 153/255, blue: 117/255, alpha: 1.0)
    

    
    //Bar chart accepts data as array of BarChartDataEntry objects
    var entries : [BarChartDataEntry]
    // this func is required to conform to UIViewRepresentable protocol
    func makeUIView(context: Context) -> BarChartView {
        
        // Aquí defino las características de la vista del gráfico:
        let chart = BarChartView()
    
        chart.drawValueAboveBarEnabled = true
        chart.animate(yAxisDuration: 2, easingOption: .linear)
        chart.legend.font = UIFont(name: "Verdana", size: 16.0)!
        chart.xAxis.drawGridLinesEnabled = false
        chart.drawGridBackgroundEnabled = false
        chart.xAxis.drawAxisLineEnabled = false
        chart.leftAxis.drawAxisLineEnabled = false
        chart.xAxis.labelPosition = .bottom
        chart.xAxis.enabled = false
        chart.rightAxis.enabled = false
        chart.leftAxis.enabled = false
        chart.drawBordersEnabled = false
        chart.minOffset = 10

        chart.data = addData()

        return chart
    }
    
    // this func is required to conform to UIViewRepresentable protocol
    func updateUIView(_ uiView: BarChartView, context: Context) {
        //when data changes chartd.data update is required
        uiView.data = addData()
    }
    
    func addData() -> BarChartData{
        

        let dataSet = BarChartDataSet(entries: entries)
        dataSet.valueFont = UIFont(name: "Verdana", size: 16.0)!
        dataSet.setColor(UIColor.brown)
        
        
        let data = BarChartData()
        data.setDrawValues(true)

        //BarChartDataSet is an object that contains information about your data, styling and more
        
        // change bars color to green
        dataSet.colors = [verdeEsmeralda]
      
        //change data label
        dataSet.label = "Datos Covid-19"
        dataSet.drawValuesEnabled = true
        dataSet.drawIconsEnabled = true
        //dataSet.colors = ChartColorTemplates.colorful()
        data.addDataSet(dataSet)
        return data
    }
    
    typealias UIViewType = BarChartView
    
}



struct Bar_Previews: PreviewProvider {
    static var previews: some View {
        Bar(entries: [
            //x - position of a bar, y - height of a bar
            BarChartDataEntry(x: 1, y: 1),
            BarChartDataEntry(x: 2, y: 1),
            BarChartDataEntry(x: 3, y: 1),
            BarChartDataEntry(x: 4, y: 1),
            BarChartDataEntry(x: 5, y: 1)

        ])
    }
}
