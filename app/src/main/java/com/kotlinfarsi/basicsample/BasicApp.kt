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
        return AppDatabase()
    }

    fun getRepository(): DataRepository {
        return DataRepository(getDatabase())
    }
}