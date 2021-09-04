package com.example.notekeeper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesRecyclerAdapter(private  val context: Context,private val notes: List<NoteInfo>) :
    RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = layoutInflater.inflate(R.layout.item_note_list,parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.textCourse.text = note.course?.courseTitle
        holder.textTitle.text = note.text
        holder.noteposition= position
    }

    override fun getItemCount(): Int {
        return  notes.size
        //this code has a lot of boilerplate. Short hand with equal sign.
    }
    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val textCourse = itemView.findViewById<TextView>(R.id.textCourse)
        val textTitle = itemView.findViewById<TextView>(R.id.textTitle)
        var noteposition = 0;
        init {
            itemView.setOnClickListener {
                val intent = Intent(context,MainActivity::class.java)
                intent.putExtra(EXTRA_NOTE_POSITION,noteposition)
                context.startActivity(intent)
            }
        }
    }
}