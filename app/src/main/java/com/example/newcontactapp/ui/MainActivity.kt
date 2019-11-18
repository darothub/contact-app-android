package com.example.newcontactapp.ui

import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.newcontactapp.R
import com.example.newcontactapp.db.ContactEntity
import com.example.newcontactapp.db.MyDatabase
import com.example.newcontactapp.helpers.ToastHelper
import icepick.Icepick

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_row.*
import kotlinx.android.synthetic.main.content_header.*

class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Icepick.restoreInstanceState(this, savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)


//        fab.show()
//        val draw = OvalShape()
        val TAG = "MainActivity"
//        val contacts = List<ContactEntity>()

//        val db = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "ContactDB").build()


        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(applicationContext)

        val contacts = MyDatabase(applicationContext).getContactDao().getAllContacts()

        var adapter = ContactAdapter(contacts, this)

        recycler.adapter = adapter

        Log.i("contacts", "$contacts")
//            Toast.makeText(this, "$contacts", Toast.LENGTH_SHORT).show()


        searchBox.visibility = View.GONE
        searchicon.setOnClickListener { view ->
//            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
            if(searchBox.visibility  == View.GONE){
                searchBox.visibility  = View.VISIBLE
                contact_list.visibility = View.INVISIBLE
//                val searchContacts = MyDatabase(this).getContactDao().getAllContacts()
                if(searchBox.text.toString().isEmpty() && !searchBox.hasFocus()){
                    adapter = ContactAdapter(contacts, this)
                    recycler.adapter = adapter

                }



                searchBox.doOnTextChanged { text, start, count, after ->
//                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                     val result = contacts.filter {
                            it -> it.firstName.contains(text.toString(), true)
                    }
//                    result.plus(contacts.filter{ it -> it.lastName.equals(searchBox.text.toString(), true)})
                    adapter = ContactAdapter(result, this)
                    recycler.adapter = adapter
                    return@doOnTextChanged
                }


            }
            else{
                searchBox.visibility  = View.GONE
                contact_list.visibility = View.VISIBLE

                if (searchBox.text.toString().isNotEmpty()){
//                    val searchContacts = MyDatabase(this).getContactDao().getAllContacts()


                    val result = contacts.filter {
                            it -> it.firstName.contains(searchBox.text.toString(), true)
                    }
//                    result.plus(contacts.filter{ it -> it.lastName.equals(searchBox.text.toString(), true)})
                    adapter = ContactAdapter(result, this)
                    recycler.adapter = adapter


                    ToastHelper(this, "$result")
                }
                else{

                    adapter = ContactAdapter(contacts, this)

                    recycler.adapter = adapter

                }


            }
        }





        fab.setOnClickListener { view ->
            addFragment(RegisterFragment())
//            Log.d(TAG, "value" + contacts)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }


    }

    fun showPopup(v: View){
        val popup = PopupMenu(this, v)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.menu_main)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.edit -> Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show()
            R.id.delete ->Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
            else -> return false
        }
        return true
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
            R.id.delete -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        fab.show()
        if(supportFragmentManager.backStackEntryCount <= 1){
            rootView.visibility = View.VISIBLE
        }
        super.onBackPressed()
    }

    fun addFragment(arg: Fragment){
        rootView.visibility = View.INVISIBLE
        fab.hide()
        supportFragmentManager.beginTransaction()
            .replace(R.id.registerFragment, arg)
            .addToBackStack("fragments")
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Icepick.saveInstanceState(this, outState);
    }


}
