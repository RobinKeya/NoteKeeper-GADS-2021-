package com.example.notekeeper

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.notekeeper.databinding.ActivityMainBinding
import com.example.notekeeper.databinding.ContentMainBinding

//import kotlinx.android.synthetic.main.layout.day_row.view.*
class MainActivity : AppCompatActivity() {
    private  var notePosition = POSITION_NOT_SET
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

       // val navController = findNavController(R.id.nav_host_fragment_content_main)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)


        val adapterCourses = ArrayAdapter<CourseInfo>(this,android.R.layout.simple_spinner_item,DataManager.courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.content.spinnerCourses.adapter = adapterCourses

        notePosition=savedInstanceState?.getInt(EXTRA_NOTE_POSITION,POSITION_NOT_SET)?:
            intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET)
            displayNote()
        else{
            createNewNote()
        }
    }

    private fun createNewNote() {
        DataManager.notes.add(NoteInfo())
        notePosition = DataManager.notes.lastIndex
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putInt(EXTRA_NOTE_POSITION,notePosition)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        binding.content.textNoteTitle.setText(note.title)
        binding.content.textNoteText.setText(note.text)

        val coursePostion = DataManager.courses.values.indexOf(note.course)
        binding.content.spinnerCourses.setSelection(coursePostion)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next->{
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    //override fun onSupportNavigateUp(): Boolean {
    //val navController = findNavController(R.id.nav_host_fragment_content_main)
    // return navController.navigateUp(appBarConfiguration)
    //       || super.onSupportNavigateUp()
    //}
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if(notePosition >= DataManager.notes.lastIndex){
            val menuItem= menu?.findItem(R.id.action_next)
            if(menuItem !=null){
                menuItem.icon = getDrawable(R.drawable.ic_baseline_block_24)
                menuItem.isEnabled=false
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {

        val note = DataManager.notes[notePosition]
        note.title=binding.content.textNoteTitle.text.toString()
        note.text = binding.content.textNoteText.text.toString()
        note.course=binding.content.spinnerCourses.selectedItem as CourseInfo
    }
}