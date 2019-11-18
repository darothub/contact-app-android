package com.example.newcontactapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newcontactapp.R
import com.example.newcontactapp.db.ContactEntity
import com.example.newcontactapp.db.MyDatabase
import com.example.newcontactapp.helpers.ToastHelper
import kotlinx.android.synthetic.main.fragment_register.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RegisterFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment() : Fragment() {

    private var contact: ContactEntity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {

            contact = ContactEntity(
                it.getString( "fName").toString(),
                it.getString( "lName").toString(),
                R.drawable.maleavatar,
                it.getString( "email").toString(),
                it.getString( "phone").toString(),
                it.getString( "address").toString()
            )

            contact?.let { view ->
                editFirstName.setText(view.firstName)
                editLastName.setText(view.lastName)

                editEmail.setText(view.email)
                editPhone.setText(view.phone)
                editAddress.setText(view.address)

            }



        }
        ToastHelper(context!!, "onAct: $contact")


        nav_back_reg.setOnClickListener {
            val backIntent = Intent(context, MainActivity::class.java)
            startActivity(backIntent)
        }


        submitBtn.setOnClickListener {
            val firstName = editFirstName.text.toString().trim()
            val lastName = editLastName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val address = editAddress.text.toString().trim()

            if(TextUtils.isEmpty(firstName)) {

                editFirstName.error = "First name is required"
                return@setOnClickListener
            }
            if(lastName.isEmpty() ) {
                editLastName.error = "Last name is required"
                return@setOnClickListener
            }
            if(phone.isEmpty()){
                editPhone.error = "Phone number is required"
                return@setOnClickListener

            }


            context?.let {

                val newContact = ContactEntity(
                    firstName,
                    lastName,
                    R.drawable.maleavatar,
                    phone,
                    email,
                    address
                )
                if(contact == null){

                    MyDatabase(it).getContactDao().add(newContact)
                    ToastHelper(it, "contact saved")

                }
                else{
//                    var newChanges = MyDatabase(it).getContactDao().getAllContacts()
//                    var adapter = ContactAdapter(,)
                    newContact.id = contact?.id!!
                    MyDatabase(it).getContactDao().updateContact(newContact)
                    ToastHelper(it, "contact updated $newContact")

                }
                val intent = Intent(it, MainActivity::class.java)
                startActivity(intent)

            }




        }

    }







}
