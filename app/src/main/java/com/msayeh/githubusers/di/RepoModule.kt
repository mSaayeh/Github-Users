package com.msayeh.githubusers.di

import com.msayeh.githubusers.features.profile.data.repo.ProfileRepoImpl
import com.msayeh.githubusers.features.profile.domain.repo.ProfileRepo
import com.msayeh.githubusers.features.search.data.repo.UsersRepoImpl
import com.msayeh.githubusers.features.search.domain.repo.UsersRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @Binds
    @ViewModelScoped
    abstract fun bindUsersRepo(usersRepoImpl: UsersRepoImpl): UsersRepo

    @Binds
    @ViewModelScoped
    abstract fun bindProfileRepo(profileRepoImpl: ProfileRepoImpl): ProfileRepo
}