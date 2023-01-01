package com.example.homework2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

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
        state.onEach {
            if (it != "empty")
                Log.d("Tag", it)
        }.launchIn(GlobalScope)

    }

    private fun runSync() {
        println("runSync method.")
        //  launch 1000 coroutines, Invoke doWork(index/number of coroutine) one after another. Example 1, 2, 3, 4, 5, etc.

           GlobalScope.launch(Dispatchers.Main){
                        repeat(1000) {
                       async(Dispatchers.Default){
                           doWork((it+1).toString())
                        }.await()
                            // await() will wait for the completion
                    }

                }

    }

    private fun runAsync() {
        println("runAsync method.")
        //launch 1000 coroutines.Invoke doWork(index/number of coroutine) in async way. Example 1, 2, 5, 3, 4, 8, etc.

        /*

      Dispatchers.Default ---> Planning to do Complex and long-running calculations,
        which can block the main thread
        It uses a shared background pool of threads
          */
            repeat(1000){
                GlobalScope.async(Dispatchers.Default) {
                    doWork((it+1).toString())
                }

            }

    }


    private suspend fun doWork(name: String) {
        delay(500)
        state.update { "$name completed." }
    }
}