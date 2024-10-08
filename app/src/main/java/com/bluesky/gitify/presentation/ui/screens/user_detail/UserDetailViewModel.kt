package com.bluesky.gitify.presentation.ui.screens.user_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesky.gitify.common.Constants
import com.bluesky.gitify.common.Resource
import com.bluesky.gitify.domain.usecases.GetUserRepositoriesUseCase
import com.bluesky.gitify.domain.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

// ViewModel to manage UI-related data for the user list screen.
// It fetches users based on a search query and exposes the result to the UI.
@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _user = mutableStateOf(UserState())
    val user: State<UserState> = _user

    private val _repositories = mutableStateOf(UserRepositoryState())
    val repositories: State<UserRepositoryState> = _repositories

    init {
        savedStateHandle.get<String>("id")?.let { user ->
            getUser(user)
            loadUserRepositories(user)
        }
    }



    fun getUser(userName: String) {
        getUserUseCase(userName).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _user.value = UserState(user = result.data)
                }

                is Resource.Error -> {
                    _user.value = UserState(
                        error = result.message ?: Constants.UNEXPECTED_ERROR
                    )
                }

                is Resource.Loading -> {
                    _user.value = UserState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadUserRepositories(userName: String) {
        getUserRepositoriesUseCase(userName).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _repositories.value = UserRepositoryState(repos = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _repositories.value = UserRepositoryState(
                            error = result.message ?: Constants.UNEXPECTED_ERROR
                        )
                    }

                    is Resource.Loading -> {
                        _repositories.value = UserRepositoryState(isLoading = true)
                    }
                }
        }.launchIn(viewModelScope)
    }
}
