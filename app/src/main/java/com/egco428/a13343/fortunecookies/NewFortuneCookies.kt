package com.egco428.a13343.fortunecookies

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.egco428.a13343.fortunecookies.DataSource.CommentDataSource
import com.egco428.a13343.fortunecookies.Model.Comment

import kotlinx.android.synthetic.main.activity_new_fortune_cookies.*
import kotlinx.android.synthetic.main.content_new_fortune_cookies.*
import kotlinx.android.synthetic.main.content_new_fortune_cookies.view.*
import kotlinx.android.synthetic.main.content_setting.*
import java.util.*
//import jdk.nashorn.internal.objects.NativeDate.getTime



class NewFortuneCookies : AppCompatActivity(), SensorEventListener {

    private var dataSource: CommentDataSource? = null
    private var sensorManager: SensorManager? = null
    //private var lastUpdate: Long = 0
    private var check = false
    private var comment :Comment? = null
    private var currentTime = Calendar.getInstance().time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_fortune_cookies)


        toolbar_new.setTitle("Fortune Cookies")
        toolbar_new.setTitleTextColor(Color.BLACK)
        toolbar_new.setTitleMargin(200,0,0,0)
        toolbar_new.setNavigationIcon(R.drawable.back_arrow)
        setSupportActionBar(toolbar_new)
        toolbar_new.setNavigationOnClickListener{
            val intent = Intent(this,Main::class.java)
            setResult(Activity.RESULT_CANCELED,intent);
            finish()
        }

        dataSource = CommentDataSource(this)
        dataSource!!.open()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //textView.setBackgroundColor(Color.GREEN)
        //lastUpdate = System.currentTimeMillis()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new, menu)
        return true
    }

    fun actionSetting(item: MenuItem){
        val intent = Intent(this,Setting::class.java)
        startActivity(intent)
    }

    fun OnClick(view: View){
        if(check){
            val intent = Intent(this,Main::class.java)
            intent.putExtra("cData", comment!!.commentData)
            intent.putExtra("cType", comment!!.commentType)
            intent.putExtra("cDate", currentTime.toString())
            setResult(Activity.RESULT_OK,intent);
            finish()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event)
        }
    }

    private fun getAccelerometer(event: SensorEvent?){
        val value = event!!.values
        val x = value[0]
        val y = value[1]
        val z = value[2]

        val accel = (x*x+y*y+z*z)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH)
        //val actualTime = System.currentTimeMillis()
        if(accel>=2){
            //if(actualTime-lastUpdate < 200) {
            //    return
            //}
            //lastUpdate = actualTime
            Toast.makeText(this,"Device was shuffled", Toast.LENGTH_SHORT).show()
            if(!check){
                shakeBtn.text = "Save"
                val res = resources.getIdentifier("opened_cookie", "drawable", packageName)
                imageView.setImageResource(res)
                check = !check

                val values = dataSource!!.allComments
                val nextInt = Random().nextInt(values.size)
                comment = dataSource!!.getData(nextInt)
                resultText.text = "Result : " + comment!!.commentData
                currentTime = Calendar.getInstance().time
                dateText.text = "Date : "+currentTime.toString()
            }

        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
        dataSource!!.close()
    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL)
        dataSource!!.open()
    }
}
