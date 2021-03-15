package com.example.drinkify.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import com.example.drinkify.R

class PreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val seekBar = findViewById<SeekBar>(R.id.seekBarBeer)
        val textView = findViewById<TextView>(R.id.textViewLevel)

        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress>0 && progress<=25)
                {
                    textView.setText("None")
                }
                else if (progress>25 && progress<=50)
                {
                    textView.setText("Little")
                }
                else if(progress>50 && progress<=75)
                {
                    textView.setText("Very")
                }
                else if (progress>75 && progress<=100)
                {
                    textView.setText("Mucho")
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }
        })
    }
}