package com.bluesky.gitify.presentation.ui.screens.user_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesky.gitify.common.Constants
import com.bluesky.gitify.common.Resource
import com.bluesky.gitify.domain.usecases.GetUserListUseCase
import com.bluesky.gitify.domain.usecases.GetUserRepositoriesUseCase
import com.bluesky.gitify.domain.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

// ViewModel to manage UI-related data for the user list screen.
// It fetches users based on a search query and exposes the result to the UI.
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase
) : ViewModel() {

    private val _users = mutableStateOf(UserListState())
    val users: State<UserListState> = _users

    init {
        searchUsers("android")
    }

    fun searchUsers(query: String) {
            getUserListUseCase(query).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _users.value = UserListState(users = result.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        _users.value = UserListState(
                            error = result.message ?: Constants.UNEXPECTED_ERROR
                        )
                    }

                    is Resource.Loading -> {
                        _users.value = UserListState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
    }
}
