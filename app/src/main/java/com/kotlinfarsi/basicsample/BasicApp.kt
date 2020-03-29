package com.kotlinfarsi.basicsample

import android.app.Application
import com.kotlinfarsi.basicsample.db.AppDatabase

class BasicApp: Application() {

    private lateinit var appExecutors: AppExecutors

    override fun onCreate() {
        super.onCreate()

        appExecutors = AppExecutors()
    }

    fun getDatabase(): AppDatabase {
        //TODO: Step 3 - (4) modifying here
        return AppDatabase()
    }

    fun getRepository(): DataRepository {
        return DataRepository(getDatabase())
    }
}