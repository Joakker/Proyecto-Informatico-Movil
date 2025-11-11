package com.example.maestrochasquilla


import AdviceApiService
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
import retrofit2.http.GET
import retrofit2.http.POST



data class SignupRequest(
    val fname: String,
    val lname: String,
    val phone: String,
    val address: String,
    val email: String,
    val password: String
)



class SignupViewModel : ViewModel() {

    var message by mutableStateOf("")
        private set

    fun signup(req: SignupRequest, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.signup(req)
                if (response.isSuccessful) {
                    message = "Signup OK"
                    onSuccess()
                } else {
                    message = "Signup error: ${response.code()}"
                }
            } catch (e: Exception) {
                message = "Error: ${e.message}"
            }
        }
    }
}


object RetrofitInstance {
    private const val BASE_URL = "http://192.168.0.7:8000/api/"

    val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}

interface Api {
    @POST("signup")
    suspend fun signup(@Body req: SignupRequest): Response<Any>
}
