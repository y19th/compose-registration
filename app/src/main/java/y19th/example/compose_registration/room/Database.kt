package y19th.example.compose_registration.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import y19th.example.compose_registration.room.dao.UserDao
import y19th.example.compose_registration.room.entity.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}