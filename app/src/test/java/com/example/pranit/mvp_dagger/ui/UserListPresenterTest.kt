package com.example.pranit.mvp_dagger.ui

import com.example.pranit.mvp_dagger.data.UserListDataSource
import com.example.pranit.mvp_dagger.model.UserResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserListPresenterTest {
    @Mock lateinit var repository : UserListDataSource
    @Mock lateinit var view : UserListContract.View

    private lateinit var presenter: UserListPresenter
    private lateinit var response: UserResponse

    @Before fun setupPresenter() {
        MockitoAnnotations.initMocks(this)
        presenter = UserListPresenter(repository,view)

        response = UserResponse(0, 3, 12, 4, null)
    }

    @Test
    fun test_getAllUsersList() {
        presenter.getAllUsersPerPage(0)
        Mockito.verify(view, Mockito.never()).onSuccess(response)
    }
}