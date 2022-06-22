//
//  ViewController.swift
//  TicTacToe
//
//  Created by Ion Jaureguialzo Sarasola on 30/10/2020.
//

import UIKit

class ViewController: UIViewController {
    // Aquí se carga todos los outlet y actions

    @IBOutlet weak var boton00: UIButton!
    @IBOutlet weak var boton01: UIButton!
    @IBOutlet weak var boton02: UIButton!
    @IBOutlet weak var boton03: UIButton!
    @IBOutlet weak var boton04: UIButton!
    @IBOutlet weak var boton05: UIButton!
    @IBOutlet weak var boton07: UIButton!
    @IBOutlet weak var boton06: UIButton!
    @IBOutlet weak var boton08: UIButton!
    
    @IBOutlet var botones: [UIButton]!
    

    @IBOutlet weak var jugadorX: UIView!
    @IBOutlet weak var jugadorO: UIView!
    
    var contadorO:Int = 0
    var contador1:Int = 0
    

    @IBOutlet weak var ResultadoX: UIStackView!
    
    @IBOutlet weak var ResulX: UILabel!
    @IBOutlet weak var ResulO: UILabel!
    
  
   
    
    @IBAction func BotonPulsado(_ sender: UIButton) {
        // Aquí es lo que va hacer cada vez que se pulsa un botón, durante todo el programa:
       //El sender me coge el valor del "tag", que es el botón que he pulsado
        
        let ficha = tirada(tag: sender.tag) // --> la función tirada() me retorna un String
        //let ficha = tiradaRandom(tag: sender.tag)
        
        
        sender.setTitle(ficha, for: .normal) // --> Le pega al botón el valor de ficha (x, o, (vacio))
        vaciarBotones() // --> Compruebo si hay ganador() y si existe o hay empate, saca ventana de información, se vacía y se sigue jugando.
    }
    
    
    
    override func viewDidLoad() { // Esto se ejecuta la primera vez, que se carga el programa
        super.viewDidLoad()
        reset() // Inicializo el tablero
        vaciarBotones() //--> Compruebo si hay ganador() y si existe o hay empate, saca ventana de información, se vacía y se sigue jugando.
        
        
    }
        
    
        
        
        // Con esta función pongo vacío todos los botones después de que gane alguien o haya un empate.
        func  vaciarBotones() {
            if (hayGanador() == 0) {
                alerta(titulo:"Mensaje", mensaje: "Has ganado jugador 0")
                reset() // Inicializo el array tablero a "-1", lo pongo vacío
                botonesVacios() // Pongo los botones vacíos
                contadorO += 1
                ResulX.text = String(contadorO)
                
            }
            if (hayGanador() == 1) {
                alerta(titulo: "Mensaje", mensaje: "Has ganado jugador 1")
                reset()
                botonesVacios()
                contador1 += 1
                ResulO.text = String(contador1)
            }
            if (empate()) {
                alerta(titulo: "Mensaje", mensaje: "Empate !!")
                reset()
                botonesVacios()
            }
        }

        func botonesVacios() {
            botones[0].setTitle("", for: .normal)
            botones[1].setTitle("", for: .normal)
            botones[2].setTitle("", for: .normal)
            botones[3].setTitle("", for: .normal)
            botones[4].setTitle("", for: .normal)
            botones[5].setTitle("", for: .normal)
            botones[6].setTitle("", for: .normal)
            botones[7].setTitle("", for: .normal)
            botones[8].setTitle("", for: .normal)
        }
        
    fileprivate func alerta(titulo: String, mensaje: String) {

        // REF: How to use UIAlertController in Swift: https://medium.com/ios-os-x-development/how-to-use-uialertcontroller-in-swift-70143d7fbede
        let alertController = UIAlertController(title: titulo, message: mensaje, preferredStyle: .alert)

        let accion = UIAlertAction(title: "Ok", style: .default) { (action: UIAlertAction) in
            // Acciones cuando se pulsa en OK
        }

        alertController.addAction(accion)

        self.present(alertController, animated: true, completion: nil)
    }

    }




