package com.bluesky.gitify.domain.usecases

import com.bluesky.gitify.common.Constants
import com.bluesky.gitify.common.Resource
import com.bluesky.gitify.data.mappers.toDomainModel
import com.bluesky.gitify.domain.models.User
import com.bluesky.gitify.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(query: String): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading<List<User>>())
            val users = repository.getUsers(query).items.map { it.toDomainModel() }
            emit(Resource.Success<List<User>>(users))
        } catch(e: HttpException) {
            emit(Resource.Error<List<User>>(e.localizedMessage ?: Constants.UNEXPECTED_ERROR))
        } catch(e: IOException) {
            emit(Resource.Error<List<User>>(Constants.COULDNT_REACH_SERVER))
        }
    }
}