package com.example.newcontactapp.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newcontactapp.R
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.profile_page_body.*
import kotlinx.android.synthetic.main.profile_page_header.*


class ProfileActivity : AppCompatActivity(){
    private  var tag = "ProfileActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        getIncomingIntent()

        callicon.setOnClickListener {
            val phone = intent.getStringExtra( "phone")
            makePhoneCall(phone!!)
        }
        chaticon.setOnClickListener {
            val phone = intent.getStringExtra( "phone")
            sendSms(phone!!)
        }
        emailicon.setOnClickListener{
            val email = intent.getStringExtra( "email")
            sendEmail(email!!)
        }

    }
    private fun getIncomingIntent(){
        if(intent.getStringExtra( "fName") != null && intent.getStringExtra( "phone") != null){
            Log.d(tag, "incoming intent found")

            val imageUrl = intent.getIntExtra( "image_url", 0)
            val firstName = intent.getStringExtra( "fName")
            val lastName = intent.getStringExtra( "lName")
            val email = intent.getStringExtra( "email")
            val address = intent.getStringExtra( "address")
            val phone = intent.getStringExtra( "phone")



            setProfileDetails(imageUrl, firstName!!, lastName!!, email!!, address!!, phone!!)
        }
        else{
            Toast.makeText(this, "No contact found", Toast.LENGTH_SHORT).show()
        }

        nav_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setProfileDetails(image:Int, fName:String, lName:String, email:String, address:String, phone:String){

        profileImage.setImageResource(image)
        cardfName.text = fName
        cardlName.text = lName
        cardEmail.text = email
        bodyFirstName.text = fName
        bodyLastName.text = lName
        bodyEmail.text = email
        bodyPhone.text = email
        bodyAddress.text = address
        bodyPhone.text = phone


    }

    private fun Context.makePhoneCall(number: String) : Boolean {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            startActivity(intent)

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun sendSms(number: String):Boolean{
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null))
            startActivity(intent)

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun sendEmail(email: String):Boolean{
        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
            startActivity(intent)

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }


}