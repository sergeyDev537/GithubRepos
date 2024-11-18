package com.example.githubrepos.data.impl

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.CookieManager
import android.webkit.URLUtil
import com.example.githubrepos.R
import com.example.githubrepos.data.db.dao.RepoDao
import com.example.githubrepos.data.mappers.ReposMapper
import com.example.githubrepos.data.network.ApiInterface
import com.example.githubrepos.domain.entities.RepoEntity
import com.example.githubrepos.domain.repositories.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiInterface: ApiInterface,
    private val mapper: ReposMapper,
    private val reposDao: RepoDao,
) : UserRepository {

    private var localRepos: List<RepoEntity> = listOf()

    override suspend fun getListRepository(username: String): List<RepoEntity> {
        val resultApi = apiInterface.getListRepositories(username = username).body()
        val result = resultApi?.let {
            mapper.mapListNetworkModelToListEntity(it)
        } ?: run {
            listOf()
        }
        localRepos = result
        return result
    }

    override fun getDownloadedListRepository(): Flow<List<RepoEntity>> {
        val result = reposDao.getDownloadedRepos().map {
            mapper.mapListDbModelToListEntity(it)
        }
        return result
    }

    override suspend fun downloadRepositoryUseCase(repoId: Int): Long {
        val repo = getRepo(repoId = repoId)
        val response = apiInterface.inspectDownload(
            repo.owner.login,
            repo.name,
        )
        val url = response.raw().request.url.toString()
        val headers = response.headers()
        val mimeType = getMimeType(ZIPBALL)
        val filename: String =
            URLUtil.guessFileName(
                url,
                headers[CONTENT_DISPOSITION],
                mimeType
            )
        val uri = Uri.parse(url)
        val cookies = CookieManager.getInstance().getCookie(url)

        val downloadManagerRequest = DownloadManager.Request(uri)
        downloadManagerRequest.setMimeType(mimeType)
        downloadManagerRequest.addRequestHeader(COOKIE_HEADER, cookies)
        downloadManagerRequest.addRequestHeader(ACCEPT_HEADER, ACCEPT_VALUE)
        downloadManagerRequest.setDescription(context.getString(R.string.downloading))
        downloadManagerRequest.setTitle(filename)
        downloadManagerRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadManagerRequest.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            filename
        )
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return downloadManager.enqueue(downloadManagerRequest)
    }

    private fun getMimeType(archiveFormat: String): String {
        return APPLICATION + if (archiveFormat == ZIPBALL) ZIP else TAR
    }

    override suspend fun addDownloadedRepository(repoId: Int) {
        val repo = getRepo(repoId = repoId)
        val repoDbDto = mapper.mapEntityToDbModel(repo)
        reposDao.addRepo(repoDbDto)
    }

    private fun getRepo(repoId: Int) = localRepos.first { it.id == repoId }

    companion object {

        private const val CONTENT_DISPOSITION = "Content-Disposition"
        private const val APPLICATION = "application/"
        private const val ZIPBALL = "zipball"
        private const val ZIP = "zip"
        private const val TAR = "tar+gzip"
        private const val COOKIE_HEADER = "cookie"
        private const val ACCEPT_HEADER = "Accept"
        private const val ACCEPT_VALUE = "application/vnd.github+json"

    }

}