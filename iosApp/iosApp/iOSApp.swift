import SwiftUI
import shared
@main
struct iOSApp: App {
//     init(){
//         AppModuleKt.doInitKoin()
//     }
	var body: some Scene {
		WindowGroup {
		    ZStack {
		        Color.white.ignoresSafeArea(.all) // status bar color
			    ContentView()
			}.preferredColorScheme(.light)
		}
	}
}
