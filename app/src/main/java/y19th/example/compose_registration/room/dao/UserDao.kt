package y19th.example.compose_registration.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import y19th.example.compose_registration.room.entity.User

@Dao
interface UserDao {

    @Query("select * from user")
    fun getUsers() : List<User>

    @Query("select user_password from user where " +
            ":typedPassword == user_password " +
            "and :userEmail == user_email " +
            "and :userName == user_name")
    fun getUserPassword(typedPassword: String, userEmail: String, userName: String): String

    @Query("select * from user where :typedName == user_name")
    fun getUserByName(typedName: String) : User

    @Query("select * from user where :typedName == user_email")
    fun getUserByEmail(typedName: String) : User

    @Insert
    fun insertUser(vararg users: User)

    @Delete
    fun deleteUser(user: User)
}