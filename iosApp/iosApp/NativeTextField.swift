//
//  NativeTextField.swift
//  iosApp
//
//  Created by Sebastián Miranda Rivera on 31/5/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import ComposeApp
import SwiftUI
import UIKit


class IOSNativeTextField: NativeTextViewFactory {
    static var shared = IOSNativeTextField()
    
    func createTextFieldView(valor: String, onValueChange: @escaping (String) -> Void, label: String,backgroundColor: UInt64) -> UIViewController {
        
        let view = NativeTextField(valor: valor, action: onValueChange, label: label)

        let controller = UIHostingController(rootView: view)
        
        controller.view.backgroundColor = UIColor { traitCollection in
                    if traitCollection.userInterfaceStyle == .dark {
                        // Modo oscuro
                        return UIColor(red: 63.0 / 255.0,green: 63.0 / 255.0,blue: 63.0 / 255.0,alpha: 1.0)
                    } else {
                        // Modo claro (tu gris claro habitual)
                        return UIColor(red: 229 / 255.0, green: 229 / 255.0, blue: 229 / 255.0, alpha: 1.0)
                    }
                }
        return controller
        
    }
}

struct NativeTextField: View {
    
    @State var valor: String
    var action: (String) -> Void
    var label: String
    
    var body: some View {
            VStack(alignment: .leading) {
                Text(label)
                    .font(.caption)
                    .foregroundColor(Color(UIColor.label)) // Similar a `onBackground`

                TextField("", text: Binding(
                    get: { valor },
                    set: { newValue in
                        valor = newValue
                        action(newValue)
                    }
                ))
                .keyboardType(.numberPad) // ⌨️ equivalente a KeyboardType.Number
                .padding(12)
                .background(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(Color.gray, lineWidth: 1) // Borde gris como `focusedIndicatorColor`
                )
                .foregroundColor(Color(UIColor.label)) // Color del texto y cursor
                .frame(height: 44)
            }
            .padding(.horizontal)
        }
}
