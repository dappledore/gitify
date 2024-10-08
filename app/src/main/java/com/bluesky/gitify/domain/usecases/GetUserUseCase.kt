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

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(username: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val user = repository.getUser(username).toDomainModel()
            emit(Resource.Success<User>(user))
        } catch(e: HttpException) {
            emit(Resource.Error<User>(e.localizedMessage ?: Constants.UNEXPECTED_ERROR))
        } catch(e: IOException) {
            emit(Resource.Error<User>(Constants.COULDNT_REACH_SERVER))
        }
    }
}