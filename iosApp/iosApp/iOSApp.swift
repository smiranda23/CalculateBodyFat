import SwiftUI
import ComposeApp
import IQKeyboardManager

@main
struct iOSApp: App {
    
    init(){
        MainViewControllerKt.doInitKoin()
        IQKeyboardManager.shared().isEnabled = true
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
