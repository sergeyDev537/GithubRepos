package com.example.githubrepos.data.mappers

import com.example.githubrepos.data.db.dto.DownloadedRepoDto
import com.example.githubrepos.data.network.dto.RepoDto
import com.example.githubrepos.domain.entities.OwnerEntity
import com.example.githubrepos.domain.entities.RepoEntity

class ReposMapper {

    fun mapListNetworkModelToListEntity(list: List<RepoDto>) = list.map {
        mapNetworkModelToEntity(it)
    }

    fun mapNetworkModelToEntity(repositoryNetworkModel: RepoDto): RepoEntity {
        val ownerEntity = OwnerEntity(
            avatarUrl = repositoryNetworkModel.owner?.avatar_url ?: "",
            htmlUrl = repositoryNetworkModel.owner?.html_url ?: "",
            id = repositoryNetworkModel.owner?.id ?: -1,
            login = repositoryNetworkModel.owner?.login ?: "",
            nodeId = repositoryNetworkModel.owner?.node_id ?: "",
            type = repositoryNetworkModel.owner?.type ?: "",
            url = repositoryNetworkModel.owner?.url ?: "",
        )
        return RepoEntity(
            archiveUrl = repositoryNetworkModel.archive_url ?: "",
            cloneUrl = repositoryNetworkModel.clone_url ?: "",
            description = repositoryNetworkModel.description ?: "",
            fullName = repositoryNetworkModel.full_name ?: "",
            gitUrl = repositoryNetworkModel.git_url ?: "",
            htmlUrl = repositoryNetworkModel.html_url ?: "",
            id = repositoryNetworkModel.id ?: -1,
            language = repositoryNetworkModel.language ?: "",
            name = repositoryNetworkModel.name ?: "",
            nodeId = repositoryNetworkModel.node_id ?: "",
            owner = ownerEntity,
            isPrivate = repositoryNetworkModel.private ?: false,
            sshUrl = repositoryNetworkModel.ssh_url ?: "",
            url = repositoryNetworkModel.url ?: "",
            defaultBranch = repositoryNetworkModel.default_branch ?: "",
        )
    }

    fun mapListDbModelToListEntity(list: List<DownloadedRepoDto>) = list.map {
        mapDbModelToEntity(it)
    }

    fun mapDbModelToEntity(repositoryDbModel: DownloadedRepoDto): RepoEntity {
        val ownerEntity = OwnerEntity(
            avatarUrl = repositoryDbModel.avatarUrl,
            htmlUrl = repositoryDbModel.htmlUrlOwner,
            id = repositoryDbModel.idOwner,
            login = repositoryDbModel.author,
            nodeId = repositoryDbModel.nodeIdOwner,
            type = repositoryDbModel.typeOwner,
            url = repositoryDbModel.ownerUrl
        )
        return RepoEntity(
            archiveUrl = repositoryDbModel.archiveUrl,
            cloneUrl = repositoryDbModel.cloneUrl,
            description = repositoryDbModel.description,
            fullName = repositoryDbModel.fullName,
            gitUrl = repositoryDbModel.gitUrl,
            htmlUrl = repositoryDbModel.htmlUrl,
            id = repositoryDbModel.id,
            language = repositoryDbModel.language,
            name = repositoryDbModel.name,
            nodeId = repositoryDbModel.nodeId,
            owner = ownerEntity,
            isPrivate = false,
            sshUrl = repositoryDbModel.sshIrl,
            url = repositoryDbModel.url,
            defaultBranch = repositoryDbModel.defaultBranch
        )
    }

    fun mapEntityToDbModel(repositoryEntity: RepoEntity) = DownloadedRepoDto(
        id = repositoryEntity.id,
        name = repositoryEntity.name,
        archiveUrl = repositoryEntity.archiveUrl,
        cloneUrl = repositoryEntity.cloneUrl,
        description = repositoryEntity.description,
        fullName = repositoryEntity.fullName,
        gitUrl = repositoryEntity.gitUrl,
        htmlUrl = repositoryEntity.htmlUrl,
        language = repositoryEntity.language,
        nodeId = repositoryEntity.nodeId,
        sshIrl = repositoryEntity.sshUrl,
        url = repositoryEntity.url,
        defaultBranch = repositoryEntity.defaultBranch,
        author = repositoryEntity.owner.login,
        avatarUrl = repositoryEntity.owner.avatarUrl,
        htmlUrlOwner = repositoryEntity.owner.htmlUrl,
        idOwner = repositoryEntity.owner.id,
        nodeIdOwner = repositoryEntity.owner.nodeId,
        typeOwner = repositoryEntity.owner.type,
        ownerUrl = repositoryEntity.owner.url
    )

}