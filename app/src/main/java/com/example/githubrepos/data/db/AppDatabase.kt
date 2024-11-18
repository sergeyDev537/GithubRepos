package com.example.githubrepos.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubrepos.data.db.dao.RepoDao
import com.example.githubrepos.data.db.dto.DownloadedRepoDto

@Database(
    entities = [DownloadedRepoDto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepoDao

}