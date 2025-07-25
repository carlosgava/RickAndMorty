import SwiftUI
import KMPObservableViewModelSwiftUI
import shared


struct ContentView: View {
    @StateViewModel var viewModel = CharactersViewModel(repository: KoinDependencies().repository)
           
    let columns = [
        GridItem(.adaptive(minimum: 120), alignment: .top)
    ]

    var body: some View {
        ZStack {
            if !viewModel.characters.isEmpty {
                NavigationStack {
                    ScrollView {
                        LazyVGrid(columns: columns, alignment: .leading, spacing: 20) {
                            ForEach(viewModel.characters, id: \.self) { item in
                                NavigationLink(destination: DetailView(characterId: item.id)) {
                                    ObjectFrame(obj: item)
                                }
                                .buttonStyle(PlainButtonStyle())
                            }
                        }
                        .padding(.horizontal)
                    }
                }
            } else {
                Text("No data available")
            }
        }
        .padding(.top, 15)
        .padding(.leading, 5)
        .padding(.trailing, 5)
        .padding(.bottom, 5)
    }
}

struct ObjectFrame: View {
    let obj: Character

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            GeometryReader { geometry in
                AsyncImage(url: URL(string: obj.image)) { phase in
                    switch phase {
                    case .empty:
                        ProgressView()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                    case .success(let image):
                        image
                            .resizable()
                            .scaledToFill()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                            .clipped()
                            .aspectRatio(1, contentMode: .fill)
                    default:
                        EmptyView()
                            .frame(width: geometry.size.width, height: geometry.size.width)
                    }
                }
            }
            .aspectRatio(1, contentMode: .fit)

            Text(obj.name)
                .font(.headline)

            Text(obj.location.name)
                .font(.subheadline)

            Text(obj.status)
                .font(.caption)
        }
    }
}
