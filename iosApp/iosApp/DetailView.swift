//
// Created by Carlos Henrique Gava on 20/07/25.
//

import Foundation
import SwiftUI
import shared
import KMPObservableViewModelSwiftUI

struct DetailView: View {
    let characterId: Int64
    @StateViewModel var viewModel = CharacterDetailViewModel(repository: KoinDependencies().repository)

    var body: some View {
        VStack {
            if let char = viewModel.character {
                ObjectDetails(character: char)
            }
        }
        .onAppear {
            viewModel.setId(characterId: characterId)
        }
    }
}

struct ObjectDetails: View {
    var character: Character

    var body: some View {
        ScrollView {
            VStack {
                AsyncImage(url: URL(string: character.image)) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                            .clipped()
                    default:
                        EmptyView()
                    }
                }

                VStack(alignment: .leading, spacing: 6) {
                    Text(character.name)
                        .font(.title)

                    LabeledInfo(label: "Localização", data: character.location.name)
                    LabeledInfo(label: "Status", data: character.status)
                    LabeledInfo(label: "Especie", data: character.species)
                    LabeledInfo(label: "Genero", data: character.gender)
                    
                    List {
                        ForEach(character.episode, id: \.self) { exercise in
                            Text(exercise)
                        }
                    }
                    .listStyle(.inset)
                }
                .padding(16)
                
            }
        }
    }
}

struct LabeledInfo: View {
    var label: String
    var data: String

    var body: some View {
        Spacer()
        Text("**\(label):** \(data)")
    }
}
