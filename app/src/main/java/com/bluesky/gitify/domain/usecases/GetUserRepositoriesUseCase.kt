package com.bluesky.gitify.domain.usecases

import com.bluesky.gitify.common.Constants
import com.bluesky.gitify.common.Resource
import com.bluesky.gitify.data.mappers.toDomainModel
import com.bluesky.gitify.domain.models.Repository
import com.bluesky.gitify.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserRepositoriesUseCase @Inject constructor(
    private val repository: UserRepository
) {

    operator fun invoke(userName: String): Flow<Resource<List<Repository>>> = flow {
        try {
            emit(Resource.Loading<List<Repository>>())
            val repos = repository.getUserRepositories(userName).map { it.toDomainModel() }
            emit(Resource.Success<List<Repository>>(repos))
        } catch(e: HttpException) {
            emit(Resource.Error<List<Repository>>(e.localizedMessage ?: Constants.UNEXPECTED_ERROR))
        } catch(e: IOException) {
            emit(Resource.Error<List<Repository>>(Constants.COULDNT_REACH_SERVER))
        }
    }

}