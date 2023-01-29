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
//        runSync()
        runAsync()

        // subscribe to flow updates and print state.value to logcat.
        runBlocking {
            launch {
                state.collect { Log.d("Tag", it) }
            }
        }


    }


        //  launch 1000 coroutines, Invoke doWork(index/number of coroutine) one after another. Example 1, 2, 3, 4, 5, etc.

        private fun runSync() = runBlocking {
            println("runSync method.")
            for (i in 1..1000) {
                doWork("Coroutine $i")
            }
        }


    //  launch 1000 coroutines. Invoke doWork(index/number of coroutine) in async way. Example 1, 2, 5, 3, 4, 8, etc.
    private fun runAsync() = runBlocking {
        println("runAsync method.")
        GlobalScope.launch {
            for (i in 1..1000) {
                launch { doWork("Coroutine $i") }
            }
        }
    }

    private suspend fun doWork(name: String) {
        delay(500)
        state.update { "$name completed." }
    }
}