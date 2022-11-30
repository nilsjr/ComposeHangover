//
//  HangoverViewModel.swift
//  iosApp
//
//  Created by Nils Druyen on 30.11.22.
//

import Foundation
import ComposeHangoverCore
import KMPNativeCoroutinesAsync

@MainActor
final class HangoverViewModel: ObservableObject {

    @Published var color: String = "empty"

    func observe() async {
        do {
            let stream = asyncStream(for: ClientApi().observeColorNative())
            for try await data in stream {
                self.color = data
            }
        } catch {
            print("Failed with error: \(error)")
        }
    }
}
