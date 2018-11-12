package com.egco428.a13343.fortunecookies

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar_setting.setTitle("Add/Delete Fortune Cookies")
        toolbar_setting.setTitleTextColor(Color.BLACK)
        toolbar_setting.setTitleMargin(100,0,0,0)
        toolbar_setting.setNavigationIcon(R.drawable.back_arrow)
        setSupportActionBar(toolbar_setting)
        toolbar_setting.setNavigationOnClickListener{
            finish()
        }
    }
}
