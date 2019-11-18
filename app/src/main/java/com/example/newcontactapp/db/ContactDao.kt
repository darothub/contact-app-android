package com.example.newcontactapp.db

import androidx.room.*
import androidx.room.Dao

@Dao
interface ContactDao {
    @Query("SELECT * FROM contactentity ORDER BY id ASC")
    fun getAllContacts():List<ContactEntity>
    @Insert
    fun add(contact: ContactEntity)
    @Update
    fun updateContact(contact: ContactEntity)
    @Delete
    fun delete(contact: ContactEntity)
}