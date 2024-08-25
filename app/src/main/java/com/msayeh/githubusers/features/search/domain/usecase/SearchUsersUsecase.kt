package com.msayeh.githubusers.features.search.domain.usecase

import com.msayeh.githubusers.features.search.domain.repo.UsersRepo
import javax.inject.Inject

class SearchUsersUsecase @Inject constructor(private val usersRepo: UsersRepo) {
    operator fun invoke(query: String, page: Int = 1) = usersRepo.searchUsers(query, page)
}