import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



class WorkerRequestViewModel : ViewModel() {
    val workerRequest = mutableStateOf("Fetching worker request...")

    init {
        fetchWorkerRequests()
    }

    fun fetchWorkerRequests() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWorkerRequests()
                workerRequest.value = response
            } catch (e: Exception) {
                workerRequest.value = "Error: ${e.message}"
            }
        }
    }
}
object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.95:8000/"

    val api: AdviceApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.scalars.ScalarsConverterFactory.create())
            .build()
            .create(AdviceApiService::class.java)
    }
}
interface AdviceApiService {
    @GET("workerrequests")
    suspend fun getWorkerRequests(): String
}

