//
//  ViewController.swift
//  ColorSelector
//
//  Created by Ion Jaureguialzo Sarasola on 13/11/20.
//

import UIKit

class ViewController: UIViewController {
    
    // Outlets
    
    @IBOutlet weak var resultado: UILabel!
    @IBOutlet weak var color: UILabel!
    
    @IBOutlet weak var red: UISlider!
    @IBOutlet weak var green: UISlider!
    @IBOutlet weak var blue: UISlider!
    


    @IBOutlet weak var labelRojo: UILabel!
    @IBOutlet weak var labelVerde: UILabel!
    @IBOutlet weak var labelAzul: UILabel!
    
    // Actions
    
    @IBAction func sliderMovido(_ sender: UISlider) {
        
        let colorFondo = UIColor(red: CGFloat(red.value), green: CGFloat(green.value), blue: CGFloat (blue.value), alpha: 1.0)
        
        color.backgroundColor=colorFondo
        
        let valorRojo = Int(red.value*255)
        let valorVerde = Int(green.value*255)
        let valorAzul = Int(blue.value*255)
        
        labelRojo.text = String (valorRojo)
        labelVerde.text = String (valorVerde)
        labelAzul.text = String (valorAzul)
        
        
        resultado.text = String(format:"%02X", valorRojo) + String(format:"%02X", valorVerde) + String(format:"%02X", valorAzul)

    
    }
    
 

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        
    }
  

}

