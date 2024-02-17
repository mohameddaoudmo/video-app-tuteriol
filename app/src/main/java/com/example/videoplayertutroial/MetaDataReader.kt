package com.example.videoplayertutroial

import android.app.Application
import android.net.Uri
import android.provider.MediaStore


data class MetaData(
    val fileName: String
)

interface MetaDataReader {
    fun getMetaDataFromUri(contentUri: Uri): MetaData?
}

class MetaDataReaderImp(private val app: Application) : MetaDataReader {
    override fun getMetaDataFromUri(contentUri: Uri): MetaData? {
        val fileName = app.contentResolver.query(
            contentUri,
            arrayOf(MediaStore.Video.VideoColumns.DISPLAY_NAME),
            null,null,null
        )?.use {
            val index = it.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
            it.moveToFirst()
            it.getString(index)
        }
        return fileName.let {
            MetaData(fileName= Uri.parse(it).lastPathSegment?:return null)
        }
    }
}