package co.tiagoaguiar.fitnesstracker.model

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong != null) Date(dateLong) else null
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

}