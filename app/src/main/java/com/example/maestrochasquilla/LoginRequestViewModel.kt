package com.example.maestrochasquilla


import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST



data class LoginRequest(
    val email: String,
    val password: String
)



class LoginViewModel : ViewModel() {

    var message by mutableStateOf("")
        private set

    fun login(req: LoginRequest, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstanceLogin.api.login(req)
                if (response.isSuccessful) {
                    message = "Login OK"
                    onSuccess()
                } else {
                    message = "Login error: ${response.code()}"
                }
            } catch (e: Exception) {
                message = "Error: ${e.message}"
            }
        }
    }
}

object RetrofitInstanceLogin {
    private const val BASE_URL = "http://192.168.0.7:8000/api/"

    val api: ApiLogin by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiLogin::class.java)
    }
}

interface ApiLogin {
    @POST("login")
    suspend fun login(@Body req: LoginRequest): Response<Any>
}
