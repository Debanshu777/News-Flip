package com.debanshu777.newsFlip.dataSource.db

import androidx.room.TypeConverter
import com.debanshu777.newsFlip.dataSource.models.Source

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
