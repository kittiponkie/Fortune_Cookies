package com.egco428.a13343.fortunecookies

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.egco428.a13343.fortunecookies.DataSource.CommentDataSource
import com.egco428.a13343.fortunecookies.Model.Comment

import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.content_setting.*
import java.util.*

class Setting : AppCompatActivity() {

    private var dataSource:CommentDataSource? = null

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

        var list_spin = ArrayList<String>()
        list_spin.add("Positive")
        list_spin.add("Negative")
        var adapter_spin = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list_spin)
        adapter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter_spin



        dataSource = CommentDataSource(this)
        dataSource!!.open()
        val values = dataSource!!.allComments

        val adapter = ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1,values)
        _listMessage.adapter = adapter
        _listMessage.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position)
            Toast.makeText(this, "delete ${item} at position $position", Toast.LENGTH_SHORT).show()

            var comment : Comment? = null
            if(_listMessage.adapter.getCount() > 0){
                comment = _listMessage.adapter.getItem(position) as Comment
                dataSource!!.deleteComment(comment!!)
                adapter.remove(comment)
            }
            adapter.notifyDataSetChanged()
        }
    }

    fun addMessage(view: View){
        //aaaa.text = spinner.selectedItem.toString()
    }

    fun OnClick(view: View){
        val adapter = _listMessage.adapter as ArrayAdapter<Comment>
        var comment : Comment? = null
        when(view.getId()){
            R.id.t1->{
                val comments = arrayOf("Summer","Winter","Spring","Fall","Raining")
                val nextInt = Random().nextInt(5)
                comment = dataSource!!.createComment(comments[nextInt],"1")
                adapter.add(comment)
            }
            R.id.t2->{
                if(_listMessage.adapter.getCount() > 0){
                    comment = _listMessage.adapter.getItem(0) as Comment
                    dataSource!!.deleteComment(comment!!)
                    adapter.remove(comment)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        dataSource!!.open()
    }

    override fun onPause() {
        super.onPause()
        dataSource!!.close()
    }
}
