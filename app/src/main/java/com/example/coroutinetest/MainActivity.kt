package com.example.coroutinetest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutinetest.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        async_button.setOnClickListener {
            launch(Dispatchers.IO) {

                getList()
            }
        }
        sequential_button.setOnClickListener {
            launch {
                getListSequential()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    fun CoroutineScope.getList(): List<String> {
        launch(Dispatchers.Main) {
            right_textView.text = ""
            left_textView.text = ""
            async(Dispatchers.Main) {
                for (i in 0..10000000 step 5) {
                    delay(400)
                    if (i % 20 == 0) {
                        left_textView.text = ""
                    }
                    left_textView.text = left_textView.text.toString() + i.toString() + "\n"
                }
            }
            async(Dispatchers.IO){
                for (i in 0..10000000000000) {
                    delay(10)
                    Log.i("TestIO","$i")
                }
            }
            async(Dispatchers.Main) {
                for (i in 0..1000000000){
                    delay(100)
                    if (i % 20 == 0) {
                        right_textView.text = ""
                    }
                    right_textView.text = right_textView.text.toString() + i.toString() + "\n"
                }
            }
        }
        return emptyList()
    }

    @SuppressLint("SetTextI18n")
    fun CoroutineScope.getListSequential(): List<String> {
        launch(Dispatchers.Main) {
            right_textView.text = ""
            left_textView.text = ""
            withContext(Dispatchers.Main) {
                for (i in 0..10) {
                    delay(500)
                    left_textView.text = left_textView.text.toString() + i.toString() + "\n"
                }
            }
            withContext(Dispatchers.IO){
                for (i in 0..10) {
                    delay(500)
                    Log.i("TestIO","$i")
                }
            }
            withContext(Dispatchers.Main) {
                for (i in 0..10) {
                    delay(500)
                    right_textView.text = right_textView.text.toString() + i.toString() + "\n"
                }
            }
        }
        return emptyList()
    }


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}
