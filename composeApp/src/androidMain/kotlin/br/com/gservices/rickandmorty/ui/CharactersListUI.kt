package br.com.gservices.rickandmorty.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.gservices.rickandmorty.data.models.Character
import br.com.gservices.rickandmorty.viewmodels.CharactersViewModel
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharactersListUI(navigate: (objectId: Long) -> Unit) {
    val viewModel: CharactersViewModel = koinViewModel()
    val characters by viewModel.characters.collectAsStateWithLifecycle()

    AnimatedContent(characters.isNotEmpty()) { objectsAvailable ->
        if (objectsAvailable) {
            CharactersGrid(
                characters = characters,
                onClick = navigate,
            )
        } else {
            EmptyScreenContent(Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun CharactersGrid(
    characters: List<Character>,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
        items(characters, key = { it.id }) { chars ->
            CharacterFrame(
                char = chars,
                onClick = { onClick(chars.id) },
            )
        }
    }
}

@Composable
private fun CharacterFrame(
    char: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = char.image,
            contentDescription = char.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.LightGray),
        )

        Spacer(Modifier.height(2.dp))

        Text(char.name, style = MaterialTheme.typography.titleMedium)
        Text(char.location.name, style = MaterialTheme.typography.bodyMedium)
        Text(char.status, style = MaterialTheme.typography.bodySmall)
    }
}
