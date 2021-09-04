package com.example.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notekeeper.databinding.ActivityItemListBinding
import com.google.android.material.snackbar.Snackbar

class ItemList : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityItemListBinding

    private val noteLayoutMagager by lazy { LinearLayoutManager(this) }

    private val notesRecyclerAdapter by lazy { NotesRecyclerAdapter(this, DataManager.notes) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarItemList.toolbar)

        binding.appBarItemList.fab.setOnClickListener { view ->
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_item_list)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_notes, R.id.nav_courses, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
       binding.appBarItemList.appBarItemList.listItems.layoutManager= noteLayoutMagager
        binding.appBarItemList.appBarItemList.listItems.adapter=notesRecyclerAdapter
    }

//    override fun onBackPressed() {
//        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
//            drawer_layout.closeDrawer(GravityCompat.START)
//        }else{
//            super.onBackPressed()
//        }
//
//    }

    override fun onResume() {
        super.onResume()
        binding.appBarItemList.appBarItemList.listItems.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.item_list, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_item_list)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_notes->{
                handleSelection("notes")
            }
            R.id.nav_courses->{
                handleSelection("courses")
            }
            R.id.nav_share->{
                handleSelection("Don't you think you've shared enough!")
            }
            R.id.nav_send->{
                handleSelection("send")
            }
        }
        return true
    }

    private fun handleSelection(message: String) {
        Snackbar.make(binding.appBarItemList.appBarItemList.listItems,message,Snackbar.LENGTH_LONG).show()
    }

}