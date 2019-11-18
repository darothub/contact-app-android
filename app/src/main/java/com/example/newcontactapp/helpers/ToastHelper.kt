package com.example.newcontactapp.helpers

import android.content.Context
import android.widget.Toast

class ToastHelper {

    companion object{
        operator fun invoke(context: Context, message:String){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

}

//fun Context.toast(message: String) =
//    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()