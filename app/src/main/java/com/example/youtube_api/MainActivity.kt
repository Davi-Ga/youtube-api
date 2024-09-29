package com.example.youtube_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.youtube_api.ui.theme.YoutubeapiTheme
import com.example.youtube_api.viewmodel.YoutubeViewModel
import com.example.youtube_api.repository.YoutubeRepository
import com.example.youtube_api.viewmodel.YoutubeViewModelFactory
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri


class MainActivity : ComponentActivity() {
    private val youtubeViewModel: YoutubeViewModel by viewModels {
        YoutubeViewModelFactory(YoutubeRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YoutubeapiTheme {
                SearchScreen(youtubeViewModel)
            }
        }
    }
}


@Composable
fun SearchScreen(viewModel: YoutubeViewModel) {
    var query by remember { mutableStateOf("") }
    var apiKey by remember { mutableStateOf("AIzaSyCWfRtmENPPTcVODqnftpQ-LSnDueQnWDw") }
    val videos by viewModel.videos.collectAsState()
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        BasicTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        Button(onClick = {
            viewModel.searchVideos(query, apiKey)
        }) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(videos) { video ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = video.snippet.title)
                    Image(
                        painter = rememberAsyncImagePainter(video.snippet.thumbnails.default.url),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${video.id.videoId}"))
                                context.startActivity(intent)
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
            item {
                LaunchedEffect(Unit) {
                    viewModel.loadNextPage(query, apiKey)
                }
            }
        }
    }
}