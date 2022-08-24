package com.raiserdev.criminalintent

import android.content.Context
import androidx.room.Room
import com.raiserdev.criminalintent.database.CrimeDatabase
import com.raiserdev.criminalintent.database.migration_1_2
import com.raiserdev.criminalintent.models.Crime
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.util.*

private const val DATABASE_NAME = "crime-database"

class CrimeRepository @OptIn(DelicateCoroutinesApi::class)
private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope){

    private val database : CrimeDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CrimeDatabase::class.java,
            DATABASE_NAME
        ).addMigrations(migration_1_2)
        //.createFromAsset(DATABASE_NAME)
        .build()

    fun getCrimes() : Flow<List<Crime>> = database.crimeDao().getCrimes()
    fun getCrime(id:UUID) : Crime = database.crimeDao().getCrime(id)

    fun updateCrime(crime: Crime){
        coroutineScope.launch(Dispatchers.IO) {
            database.crimeDao().updateCrime(crime)
        }
    }

    fun addCrime(crime: Crime){
        coroutineScope.launch(Dispatchers.IO) {
            database.crimeDao().addCrime(crime)
        }
    }

    companion object{
        private var INSTANCE : CrimeRepository ?= null
        fun initialize(context: Context){
            if (INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository{
            return INSTANCE ?:
            throw  IllegalStateException("CrimeRepository must be initialized")
        }
    }

}