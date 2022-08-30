package com.raiserdev.criminalintent.database

import android.database.SQLException
import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.raiserdev.criminalintent.models.Crime

private const val TAG = "CrimeDatabase"
@Database(entities = [Crime::class], version = 3, exportSchema = false)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase() {
    abstract fun crimeDao():CrimeDao
}

val migration_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        try {
            database.execSQL(
                "ALTER TABLE Crime ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
            )
        }catch (e : SQLException){
            Log.e(TAG, " ERROR IN migration_1_2 : $e ")
        }

    }
}

val migration_2_3 = object : Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        try {
            database.execSQL(
                "ALTER TABLE Crime ADD COLUMN photoFileName TEXT"
            )
        }catch (e:SQLException){
            Log.e(TAG, " ERROR IN migration_2_3 : $e ")
        }

    }
}

// MIGRATIONS
/**
 *
 * val MIGRATION_1_2: Migration = object : Migration(1, 2) {
override fun migrate(database: SupportSQLiteDatabase) {
// Create the new table
database.execSQL(
"CREATE TABLE IF NOT EXISTS VehicleDetailsEntityTmp (vehicleId TEXT NOT NULL, updatedOn TEXT, updatedBy TEXT,vehicleClientId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL )"
)

// Copy the data
database.execSQL(
"INSERT INTO VehicleDetailsEntityTmp (vehicleId, updatedOn, updatedBy ,vehicleClientId) SELECT vehicleId, updatedOn, updatedBy ,vehicleClientId FROM VehicleDetailsEntity ")

// Remove the old table
database.execSQL("DROP TABLE VehicleDetailsEntity")

// Change the table name to the correct one
database.execSQL("ALTER TABLE VehicleDetailsEntityTmp RENAME TO VehicleDetailsEntity")
}
}
 *
 * */