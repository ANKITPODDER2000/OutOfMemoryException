package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

private const val TAG = "MainViewModel"
class MainViewModel: ViewModel() {

    private val listOfFiles = arrayListOf<File>()

    private fun createFileList(file: File?) {
        file?.listFiles()?.let {
            for(f in it) {
                if(f.isFile) {
                    Log.d(TAG, "createFileList: File name : ${f.name}")
                    listOfFiles.add(f)
                }
                else if(f.isDirectory) createFileList(f)
            }
            Log.d(TAG, "createFileList: file size : ${listOfFiles.size}")
        }
    }

    private fun getFileContent(file: File): ByteArray {
        return file.readBytes()
    }

    fun handleButtonClick(rootPath: String) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "handleButtonClick: rootPath: $rootPath")
            val rootFile = File(rootPath)
            createFileList(rootFile)
            val deferredList = arrayListOf<Deferred<Int>>()
            for(file in listOfFiles) {
                val content = getFileContent(file)
                val defObj = printContentDetails(file, content)
                deferredList.add(defObj)
            }
            deferredList.awaitAll()
            Log.d(TAG, "handleButtonClick: Completed..................")
        }
    }
    
    private fun printContentDetails(file: File, content: ByteArray): Deferred<Int> {
        return CoroutineScope(Dispatchers.IO).async {
            delay(20 * 1000)
            Log.d(TAG, "handleButtonClick: file name : ${file.name} size : ${content.size}")
            1
        }
    }
}