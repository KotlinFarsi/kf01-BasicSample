package com.kotlinfarsi.basicsample.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kotlinfarsi.basicsample.AppExecutors
import com.kotlinfarsi.basicsample.db.dao.ProductDao
import com.kotlinfarsi.basicsample.db.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        const val DATABASE_NAME = "basic-sample-db"

        private val mIsDatabaseCreated = MutableLiveData<Boolean>()

        @Volatile
        private var instance: AppDatabase? = null
        private var LOCK = AppDatabase::class

        operator fun invoke(context: Context, executors: AppExecutors): AppDatabase {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = buildDatabase(context.applicationContext, executors)
                        instance?.updateDatabaseCreated(context.applicationContext)
                    }
                }
            }

            return instance!!
        }

        private fun buildDatabase(appContext: Context, executors: AppExecutors): AppDatabase {
            return Room
                .databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        executors.diskIO.execute {
                            addDelay()

                            // Generate the data for pre-population
                            val database =
                                AppDatabase(appContext, executors)
                            val products =
                                DataGenerator.generateProducts()
                            //TODO: Part 5 - (6) modifying database to produce comments too

                            insertData(database, products)

                            database.setDatabaseCreated()
                        }
                    }
                })
                .build()
        }

        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
            }
        }

        private fun insertData(database: AppDatabase, products: List<ProductEntity>) {
            database.runInTransaction {
                database.productDao().insertAll(products)
                //TODO: Part 5 - (5) modifying database to produce comments too
            }
        }

    }


    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    fun getDatabaseCreated(): LiveData<Boolean> {
        return mIsDatabaseCreated
    }

}
