package com.example.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class Solution : AppCompatActivity() {
    private val list = mutableListOf<String>()
    private val state = MutableStateFlow("empty")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }

    private fun main() {
        runSync()
        runAsync()



        lifecycleScope.launch {
//          observe state updates
            state.collect {
                // subscribe to flow updates and print state.value to logcat.
                if (it!="empty")
                Log.d("Tag",it)


            }
        }
    }

    private fun runSync() {
        println("runSync method.")
        //  launch 1000 coroutines. Invoke doWork(index/number of coroutine) one after another. Example 1, 2, 3, 4, 5, etc.



            CoroutineScope(IO).launch {

                repeat(1000) {
                  launch{
                        list.add(it.toString())

                    }
                    delay(300)
                    doWork(list[it])
                }

            }


    }

    private fun runAsync() {
        println("runAsync method.")

        //  launch 1000 coroutines. Invoke doWork(index/number of coroutine) in async way. Example 1, 2, 5, 3, 4, 8, etc.
        lifecycleScope.launch {

            async(IO) {

                repeat(1000) {
                   async(IO){
                        delay(500)
                        list.add(it.toString())
                    }

                }

                repeat(1000) {
                    delay(1000)
                    doWork(list[it])

                }

            }

        }
    }

    private suspend fun doWork(name: String) {
        delay(500)
        state.update { "$name completed." }
    }
}