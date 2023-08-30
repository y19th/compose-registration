package y19th.example.compose_registration.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_name") val name: String = "auto_name",
    @ColumnInfo(name = "user_email")val email: String = "auto_email",
    @ColumnInfo(name = "user_password")val password: String = "auto_password"
)
