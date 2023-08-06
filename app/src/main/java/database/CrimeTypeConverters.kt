package database

import androidx.room.TypeConverter
import java.util.*

//конвертер явно добавляется в CrimeDatabase в виде аннотации
class CrimeTypeConverters {
    //Date и UUID по умолчанию не знает как хранить
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

}