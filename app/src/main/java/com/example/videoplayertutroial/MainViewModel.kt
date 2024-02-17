package com.example.videoplayertutroial

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val player: Player,
    private val metaDataReader: MetaDataReader) :
    ViewModel() {
    private val videoURIs = savedStateHandle.getStateFlow("videoUri", emptyList<Uri>())
    val videoItems = videoURIs.map {
        it.map {
            VideoItem(contentUri = it, mediaitem = MediaItem.fromUri(it), name = metaDataReader.getMetaDataFromUri(it)?.fileName ?: "No name")
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        player.prepare()
    }

    fun addvideoUri(uri: Uri) {
        savedStateHandle["videoUri"] = videoURIs.value + uri
        player.addMediaItem(MediaItem.fromUri(uri))
    }
    fun playVideo(uri: Uri) {
        player.setMediaItem(
            videoItems.value.find { it.contentUri == uri }?.mediaitem ?: return
        )
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}