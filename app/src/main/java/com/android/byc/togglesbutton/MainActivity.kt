package com.android.byc.togglesbutton

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private var switchView: MyViewGroup? = null
    //private var ssWitchView: ToggleSwitchView? = null
    private var openState = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switchView = findViewById(R.id.toggleView)
        //ssWitchView = findViewById(R.id.switchView)
        switchView?.setOnClickListener {
            switchView?.toOnState(!openState)
            openState = !openState
        }
        //ssWitchView?.setOnClickListener {
        //    ssWitchView?.toOnState(!openState)
          //  openState = !openState
        //}
    }
}

