package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.notekeeper.databinding.ActivityNoteListBinding
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager

class NoteListActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_note_list2)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            val activityIntent = Intent(this,MainActivity::class.java)
            startActivity(activityIntent)
        }
//        binding.contentList.listNotes.adapter=ArrayAdapter(this, android.R.layout.simple_list_item_1,
//        DataManager.notes)

//        binding.contentList.listNotes.setOnItemClickListener{parent,view,position,id->
//            val activityIntent = Intent(this, MainActivity::class.java)
//            activityIntent.putExtra(EXTRA_NOTE_POSITION,position)
//            startActivity(activityIntent)
//        }
        binding.contentList.listitems.layoutManager= LinearLayoutManager(this)
        binding.contentList.listitems.adapter=NotesRecyclerAdapter(this,DataManager.notes)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_note_list2)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        binding.contentList.listitems.adapter?.notifyDataSetChanged()
        //when a note is added, and we're now resuming to Notelist.
        //(binding.contentList.listNotes.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()
    }
}