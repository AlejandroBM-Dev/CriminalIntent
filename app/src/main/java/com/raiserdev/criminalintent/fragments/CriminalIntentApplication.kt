package com.raiserdev.criminalintent.fragments

import android.app.Application
import com.raiserdev.criminalintent.CrimeRepository

class CriminalIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}