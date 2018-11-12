package com.egco428.a13343.fortunecookies

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.*
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.egco428.a13343.fortunecookies.Model.Comment

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_setting.*
import kotlinx.android.synthetic.main.sublist.view.*
import java.lang.reflect.Type
import java.util.*
import android.app.Activity
import android.R.attr.data
import android.R.attr.data
import kotlinx.android.synthetic.main.sublist_pic.view.*


class Main : AppCompatActivity() {

    private var mycustom = myCustomAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setTitle("Fortune Cookies")
        toolbar.setTitleTextColor(Color.BLACK)
        toolbar.setTitleMargin(340,0,0,0)
        setSupportActionBar(toolbar)

        var listView = findViewById<ListView>(R.id._listFortune)
        listView.adapter = mycustom
        listView.setOnItemClickListener { adapterView, view, position, id ->
            mycustom.delData(position)
            //Toast.makeText(this, "$position", Toast.LENGTH_SHORT).show()
            listView.adapter = mycustom
        }



    }

    private class myCustomAdapter(context: Context): BaseAdapter(){
        private val mContext: Context
        private var Data = arrayListOf<String>()
        private var Type = arrayListOf<String>()
        private var Date = arrayListOf<String>()
        init{
            mContext = context
        }
        public fun addData(x:String,y:String,z:String){
            Data.add(x)
            Type.add(y)
            Date.add(z)
        }

        public fun delData(position: Int){
            //Log.d("Kie",Data.toString())
            Data.removeAt(position)
            Type.removeAt(position)
            Date.removeAt(position)
            //Log.d("Kie",Data.toString())
        }

        override fun getCount(): Int {
            return Data.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return Data[position]
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val rowMain: View
            if(convertView == null){
                val layoutInflator = LayoutInflater.from(viewGroup!!.context)
                rowMain = layoutInflator.inflate(R.layout.sublist_pic, viewGroup, false)
                val viewHolder = ViewHolder(rowMain.titleT, rowMain.subT)
                rowMain.tag = viewHolder
            } else {
                rowMain = convertView
            }
            val viewHolder = rowMain.tag as ViewHolder
            viewHolder.data.text = Data.get(position)
            viewHolder.date.text = "Date : "+Date.get(position)
            if(Type.get(position) == "Positive") viewHolder.data.setTextColor(Color.BLUE)
            else if(Type.get(position) == "Negative") viewHolder.data.setTextColor(Color.rgb(243, 156, 18))

            return rowMain
        }

        private class ViewHolder(val data: TextView, val date: TextView){

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun actionAdd(item: MenuItem){
        val intent = Intent(this,NewFortuneCookies::class.java)
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode === 1) {
            if (resultCode === Activity.RESULT_OK) {
                val bundle = data!!.extras
                if (bundle != null) {
                    val listView = findViewById<ListView>(R.id._listFortune)
                    mycustom.addData(bundle.getString("cData"),bundle.getString("cType"),bundle.getString("cDate"))
                    listView.adapter = mycustom
                }
            }
            if (resultCode === Activity.RESULT_CANCELED) {
                val listView = findViewById<ListView>(R.id._listFortune)
                listView.adapter = mycustom
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }
}
