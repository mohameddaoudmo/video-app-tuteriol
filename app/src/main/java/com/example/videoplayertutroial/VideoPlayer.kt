package com.example.videoplayertutroial

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayer(
    youtubeVideoId: String, lifecycleOwner: LifecycleOwner
) {
    AndroidView(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(16.dp)),
        factory = { context ->
            YouTubePlayerView(context = context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(youtubeVideoId, 0f)
                    }
                })
            }
        })
}

@Composable
fun VideoPlayer(
    videoUri: Uri
) {
    AndroidView(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(16.dp)),
        factory = { context ->
            VideoView(context).apply {
                setVideoURI(videoUri)

                val myController = MediaController(context)
                myController.setAnchorView(this)
                setMediaController(myController)

                setOnPreparedListener {
                    start()
                }
            }
        })
}

@Composable
fun ScreenForShowVideo() {
    val videoUri =
        Uri.parse("android.resource://com.example.realmdatabase/raw/sample")

    Column {
        YouTubePlayer(
            youtubeVideoId = "kShAS6aafOU",
            lifecycleOwner = LocalLifecycleOwner.current
        )
        Spacer(modifier = Modifier.height(16.dp))
        VideoPlayer(videoUri =videoUri)
//        VideoPlayerExo(videoUrl =videoUrl)
    }




}