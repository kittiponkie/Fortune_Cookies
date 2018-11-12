package com.egco428.a13343.fortunecookies

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_new_fortune_cookies.*

class NewFortuneCookies : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_fortune_cookies)

        toolbar_new.setTitle("Fortune Cookies")
        toolbar_new.setTitleTextColor(Color.BLACK)
        toolbar_new.setTitleMargin(200,0,0,0)
        toolbar_new.setNavigationIcon(R.drawable.back_arrow)
        setSupportActionBar(toolbar_new)
        toolbar_new.setNavigationOnClickListener{
            finish()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_new, menu)
        return true
    }

    fun actionSetting(item: MenuItem){
        val intent = Intent(this,Setting::class.java)
        startActivity(intent)
    }
}
