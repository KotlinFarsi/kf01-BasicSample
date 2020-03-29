package com.kotlinfarsi.basicsample.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kotlinfarsi.basicsample.AppExecutors
import com.kotlinfarsi.basicsample.db.converter.DateConverter
import com.kotlinfarsi.basicsample.db.dao.CommentDao
import com.kotlinfarsi.basicsample.db.dao.ProductDao
import com.kotlinfarsi.basicsample.db.entity.CommentEntity
import com.kotlinfarsi.basicsample.db.entity.ProductEntity
import com.kotlinfarsi.basicsample.db.entity.ProductFtsEntity

@Database(entities = [ProductEntity::class, ProductFtsEntity::class, CommentEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun commentDao(): CommentDao

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
                            val comments =
                                DataGenerator.generateCommentsForProducts(products)

                            insertData(database, products, comments)

                            database.setDatabaseCreated()
                        }
                    }
                })
                .addMigrations(MIGRATION_1_2)
                .build()
        }

        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
            }
        }

        private fun insertData(
            database: AppDatabase,
            products: List<ProductEntity>,
            comments: List<CommentEntity>
        ) {
            database.runInTransaction {
                database.productDao().insertAll(products)
                database.commentDao().insertAll(comments)
            }
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE VIRTUAL TABLE IF NOT EXISTS `productsFts` USING FTS4("
                            + "`name` TEXT, `description` TEXT, content=`products`)"
                )
                database.execSQL(
                    "INSERT INTO productsFts (`rowid`, `name`, `description`) "
                            + "SELECT `id`, `name`, `description` FROM products"
                )
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
