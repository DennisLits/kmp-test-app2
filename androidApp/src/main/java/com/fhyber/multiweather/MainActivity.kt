package com.fhyber.multiweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fhyber.shared.utils.createApplicationScreenMessage
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvExampleText.text = createApplicationScreenMessage()

    }
}
