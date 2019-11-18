package com.example.newcontactapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ContactEntity (


//    @ColumnInfo(name = "first_name")
    var firstName:String,
    var lastName: String,
    var image:Int,
    var phone:String,
    var email:String,
    var address: String
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
//   constructor(id:Int, firstName: String?,):this(id, firstName)
}



