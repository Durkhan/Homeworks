package com.example.homework2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class Solution : AppCompatActivity() {
    private val state = MutableStateFlow("empty")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }

    private fun main() {
        runSync()
//        runAsync()

        // subscribe to flow updates and print state.value to logcat.
        lifecycleScope.launch {
//          observe state updates
            state.collect {
                if (it != "empty")
                    Log.d("Tag", it)
            }
        }
    }

    private fun runSync() {
        println("runSync method.")
        //  launch 1000 coroutines, Invoke doWork(index/number of coroutine) one after another. Example 1, 2, 3, 4, 5, etc.

/*
Dispatchers.Main-starts in main Thread,perform the UI operations within the coroutine
 */
                repeat(1000)
                    {
                    lifecycleScope.launch(Dispatchers.Main){
                        doWork((it+1).toString())
                    }

                }

    }

    private fun runAsync() {
        println("runAsync method.")
        //launch 1000 coroutines.Invoke doWork(index/number of coroutine) in async way. Example 1, 2, 5, 3, 4, 8, etc.

        /*
        Planning to do Complex and long-running calculations,
        which can block the main thread and freeze the UI.
        It uses a shared background pool of threads
          */
                repeat(1000){
                      lifecycleScope.launch(Dispatchers.Default) {
                          doWork((it+1).toString())
                      }
                  }
    }
    private suspend fun doWork(name: String) {
        delay(500)
        state.update { "$name completed." }
    }
}