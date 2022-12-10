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

    @Published var theme: Common_entityThemeEntity = Common_entityThemeEntity(colors: [:], shapes: [:], typography: [:])

    private let repository: HangoverRepository

    init(repository: HangoverRepository) {
        self.repository = repository
    }

    func observe() async {
        do {
            let stream = asyncStream(for: repository.observeStyleNative())
            for try await data in stream {
                self.theme = data
            }
        } catch {
            print("Failed with error: \(error)")
        }
    }
}
