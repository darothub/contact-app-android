package com.example.newcontactapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.example.newcontactapp.db.ContactDao
import com.example.newcontactapp.db.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao

    companion object{
        @Volatile private var instance: MyDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, MyDatabase::class.java, "contactdatabase").allowMainThreadQueries().build()

    }

//    companion object{
//
//        private var instance: MyDatabase?= null
//
//        fun getInstance(context: Context): MyDatabase{
//
//
//            if(instance == null){
//                instance = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "AnyDatabase").allowMainThreadQueries().build()
//                return instance!!
//            }
//            return instance!!
//
//
//        }
//    }
}