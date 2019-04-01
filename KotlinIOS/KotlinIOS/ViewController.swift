//
//  ViewController.swift
//  KotlinIOS
//
//  Created by Dennis on 3/29/19.
//  Copyright © 2019 Dennis. All rights reserved.
//

import UIKit
import SharedCode

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        NSLog(CoolUtilsKt.createApplicationScreenMessage())
        
    }

    @IBAction func buttonClickKotlinTest(sender: UIButton) {
        NSLog("THIS IS A TEST LOG!!!")
        
        NSLog(CoolUtilsKt.createApplicationScreenMessage() + " hhhaaaa ")
    }
    
    
    
}

