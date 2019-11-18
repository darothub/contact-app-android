package com.example.newcontactapp.ui

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newcontactapp.R
import com.example.newcontactapp.db.ContactEntity
import com.example.newcontactapp.db.MyDatabase
import com.example.newcontactapp.helpers.ToastHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.launch

class ContactAdapter (data:List<ContactEntity>, internal var activity: MainActivity):RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(){

    internal var data : List<ContactEntity> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        val layout = LayoutInflater.from(activity).inflate(R.layout.contact_row, parent, false)
        return ContactViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position:Int){

            holder.fName.text = data[position].firstName
            holder.lName.text = data[position].lastName
            holder.phoneNumber.text = data[position].phone
            holder.image.text = data[position].firstName.first().toString()


            val color = arrayListOf<Int>(Color.parseColor("#9C27B0"), Color.parseColor("#4CAF50"), Color.parseColor("#FF9800"), Color.RED, Color.GREEN, Color.BLUE)
            val range = color.size-1
            val rand = (0..range).shuffled().last()

            val filter:ColorFilter = PorterDuffColorFilter(color[rand], PorterDuff.Mode.SRC_IN)
            holder.circle.colorFilter = filter
            holder.image.background = holder.circle

//            holder.image.setBackgroundColor(Color.parseColor(color[rand]))




        holder.moreMenu.visibility = View.GONE

        holder.moreBtn.setOnClickListener {
//            Toast.makeText(activity, "$data[position]", Toast.LENGTH_SHORT).show()
            if(holder.moreMenu.visibility == View.GONE){
                holder.moreMenu.visibility = View.VISIBLE
            }
            else{
                holder.moreMenu.visibility = View.GONE
            }

        }
        holder.editIcon.setOnClickListener {

            activity.rootView.visibility = View.INVISIBLE
            activity.fab.hide()
            activity.addFragment(
                RegisterFragment().apply {
                    arguments = Bundle().apply {
                        putString("fName", data[position].firstName)
                        putString("lName", data[position].lastName)
                        putString("email", data[position].email)
                        putString("address", data[position].address)
                        putString("phone", data[position].phone)
                    }
                }
            )

       //            activity.startActivity(intent)
//




        }

        holder.deleteIcon.setOnClickListener {
            AlertDialog.Builder(holder.deleteIcon.context).apply {
                setTitle("Are you sure?")
                setMessage("You cannot undo this operation")
                setPositiveButton("Yes"){_, _ ->
                    context.let {
                        MyDatabase(it).getContactDao().delete(data[position])
                        ToastHelper(it, "Deleted")
                        val intent = Intent(it, MainActivity::class.java)
                        it.startActivity(intent)

                    }
                }
                setNegativeButton("No"){_, _ ->

                }


            }.create().show()

        }

//        holder.email.text = data[position].email
//        holder.address.text = data[position].address

        holder.card.setOnClickListener {

            Toast.makeText(activity, data[position].firstName, Toast.LENGTH_SHORT).show()

            val intent = Intent(activity, ProfileActivity::class.java)
            intent.putExtra("fName", data[position].firstName)
            intent.putExtra("lName", data[position].lastName)
            intent.putExtra("email", data[position].email)
            intent.putExtra("address", data[position].address)
            intent.putExtra("phone", data[position].phone)
            intent.putExtra("image_url", data[position].image)
            activity.startActivity(intent)

//            val profilePage = Dialog(activity)
//            profilePage.setContentView(R.layout.activity_profile)
////            profilePage.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL)
//            profilePage.setTitle("Profile page")
//
//            val profileName = profilePage.findViewById<TextView>(R.id.profileName)
//            val profileEmail = profilePage.findViewById<TextView>(R.id.profileEmail)
//            val profileImage = profilePage.findViewById<ImageView>(R.id.profileImage)
//            val nav_back = profilePage.findViewById<ImageView>(R.id.nav_back)
//
//            profileName.text = data[position].name
//            profileEmail.text = data[position].phone
//            profileImage.setImageResource(data[position].image)
//
//            profilePage.show()
//            nav_back.setOnClickListener {
//                profilePage.dismiss()
//            }

        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

    @TargetApi(21)
    class ContactViewHolder(contactView: View): RecyclerView.ViewHolder(contactView){
        internal var fName: TextView = contactView.findViewById(R.id.firstName)
        internal var lName: TextView = contactView.findViewById(R.id.lastName)
//        internal var email: TextView = contactView.findViewById(R.id.profileEmail)
//        internal var address: TextView = contactView.findViewById(R.id.profileAddress)
        internal var image: TextView = contactView.findViewById(R.id.contactImage)
        internal var moreBtn:ImageView = contactView.findViewById(R.id.moreBtn)
        internal var phoneNumber: TextView = contactView.findViewById(R.id.contactPhone)
        internal var moreMenu:ViewGroup = contactView.findViewById(R.id.deleteEditVG)
        internal var card: ViewGroup = contactView.findViewById(R.id.contactCard);
        internal var deleteIcon:ImageView = contactView.findViewById(R.id.deleteIcon)
        internal var editIcon:ImageView = contactView.findViewById(R.id.editIcon)
        internal var circle = contactView.resources.getDrawable(R.drawable.circle, null)

    }

    private fun deleteNote(context:Context){

    }
}