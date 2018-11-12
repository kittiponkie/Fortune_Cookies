package com.egco428.a13343.fortunecookies

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.egco428.a13343.fortunecookies.DataSource.CommentDataSource
import com.egco428.a13343.fortunecookies.Model.Comment

import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.content_setting.*
import kotlinx.android.synthetic.main.sublist.view.*
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
        var values = dataSource!!.allComments
        var adapter = CourseArrayAdapter(this, 0, values!!)
        _listMessage.adapter = adapter
        _listMessage.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position)
            Toast.makeText(this, "delete ${item} at position $position", Toast.LENGTH_SHORT).show()

            var comment : Comment? = null
            if(_listMessage.adapter.getCount() > 0){
                comment = _listMessage.adapter.getItem(position) as Comment
                dataSource!!.deleteComment(comment!!)
                //adapter.remove(comment)
            }
            values = dataSource!!.allComments
            adapter = CourseArrayAdapter(this, 0, values!!)
            _listMessage.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    fun addMessage(view: View){
        dataSource!!.createComment(messageText.text.toString(),spinner.selectedItem.toString())
        val values = dataSource!!.allComments
        val adapter = CourseArrayAdapter(this, 0, values!!)
        _listMessage.adapter = adapter
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

    private class CourseArrayAdapter(var context: Context, resource: Int, var objects: List<Comment>): BaseAdapter(){
        override fun getCount(): Int {
            return objects.size
        }

        override fun getItem(position: Int): Any {
            return objects[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val course = objects[position]
            val rowMain:View
            if(convertView == null){
                val inflator = context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                rowMain  = inflator.inflate(R.layout.sublist, null)
                val viewHolder = ViewHolder(rowMain.titleText,rowMain.subText)
                rowMain.tag = viewHolder
            }
            else{
                rowMain = convertView
            }

            val viewHolder = rowMain.tag as ViewHolder
            viewHolder.title.text = course.commentData
            viewHolder.sub.text = course.commentType
            if(course.commentType.toString() == "Positive") viewHolder.title.setTextColor(Color.BLUE)
            else if(course.commentType.toString() == "Negative") viewHolder.title.setTextColor(Color.rgb(243, 156, 18))

            return rowMain
        }

        private class ViewHolder(val title: TextView, val sub: TextView){

        }
    }
}
