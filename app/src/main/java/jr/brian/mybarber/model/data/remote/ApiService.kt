package jr.brian.mybarber.model.data.remote

import jr.brian.mybarber.model.data.request.SignInRequest
import jr.brian.mybarber.model.data.request.SignUpRequest
import jr.brian.mybarber.model.data.response.SignInResponse
import jr.brian.mybarber.model.data.response.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-type: application/json")
    @POST("AppUser/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @Headers("Content-type: application/json")
    @POST("AppUser/login")
    fun signIn(@Body signInRequest: SignInRequest): Call<SignInResponse>

//    @GET("User/jobs")
//    fun getJobs(): Call<JobsResponse>
//
//    @GET("User/applyForJob/{user_id}/{job_id}")
//    fun applyForJob(
//        @Path("user_id") user_id: String,
//        @Path("job_id") job_id: String
//    ): Call<ApplyJobResponse>
}