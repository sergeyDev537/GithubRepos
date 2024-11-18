package com.example.githubrepos.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubrepos.data.db.dto.DownloadedRepoDto

@Dao
interface RepoDao {

    @Query("SELECT * FROM downloaded_repos")
    fun getDownloadedRepos(): LiveData<List<DownloadedRepoDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepo(repositoryDbModel: DownloadedRepoDto)

}