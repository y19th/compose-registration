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

    @Query("select * from user where :typedName == user_name")
    fun getUserByName(typedName: String) : User

    @Query("select * from user where :typedName == user_email")
    fun getUserByEmail(typedName: String) : User

    @Query("update user set user_password = :userPassword where id == :userId")
    fun setUserPassword(userPassword: String, userId: Int)

    @Insert
    fun insertUser(vararg users: User)

    @Delete
    fun deleteUser(user: User)
}