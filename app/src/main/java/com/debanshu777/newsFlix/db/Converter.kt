package com.debanshu777.newsFlix.db

import androidx.room.TypeConverter
import com.debanshu777.newsFlix.models.Source

class Converter {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}
