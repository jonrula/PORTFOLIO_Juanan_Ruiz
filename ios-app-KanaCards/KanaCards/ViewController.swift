//
//  ViewController.swift
//  KanaCards
//
//  Created by Ion Jaureguialzo Sarasola on 26/09/2020.
//

import UIKit

class ViewController: UIViewController {


    // Outlets
    @IBOutlet weak var selector: UISegmentedControl!
    @IBOutlet weak var simbolo: UILabel!
    @IBOutlet weak var opcion1: UIButton!
    @IBOutlet weak var opcion2: UIButton!
    @IBOutlet weak var opcion3: UIButton!
    @IBOutlet weak var correctas: UILabel!
    @IBOutlet weak var incorrectas: UILabel!
    
    // Variables de estado
    
    var buenas = 0
    var malas = 0
    var aleatorio = -1


    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        actualizarPantalla()
        
    }
    
    // Método extraido
    
    fileprivate func actualizarPantalla() {
        // Declaramos un Array de 3 posiciones aleatorias que coje de la función func romaji, que saca valores aleatorios del diccionario Master
        
        let silabas = romaji(aleatorios: 3)
        aleatorio = Int.random(in: 0...2)
        
        if (selector.selectedSegmentIndex == 0){
            simbolo.text = convertir(romaji: silabas[aleatorio], .hiragana)
        }else{
            simbolo.text = convertir(romaji: silabas[aleatorio], .katakana)
        }
        
        opcion1.setTitle(silabas[0], for: .normal)
        
        opcion2.setTitle(silabas[1], for: .normal)
        
        opcion3.setTitle(silabas[2], for: .normal)
        
        correctas.text = String(buenas)
        incorrectas.text = String(malas)
    }
    
    
    @IBAction func Selector(_ sender: Any) {
        
        actualizarPantalla()
    }
    
    @IBAction func botonPulsado(_ sender: UIButton) {

        if aleatorio == sender.tag {
            buenas += 1
        } else {
            malas += 1
        }

        actualizarPantalla()
    }

    // REF: Detectar el gesto de agitar: https://stackoverflow.com/a/56146245/14378620
    
    override func motionEnded(_ motion: UIEvent.EventSubtype, with event: UIEvent?) {

        if motion == .motionShake {
            buenas = 0
            malas = 0
            actualizarPantalla()
        }
    }
    
}
