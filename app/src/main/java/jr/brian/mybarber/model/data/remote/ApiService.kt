package jr.brian.mybarber.model.data.remote

import jr.brian.mybarber.model.data.CurrentApptResponse
import jr.brian.mybarber.model.data.barber.BarberResponse
import jr.brian.mybarber.model.data.request.SignInRequest
import jr.brian.mybarber.model.data.request.SignUpRequest
import jr.brian.mybarber.model.data.response.SignInResponse
import jr.brian.mybarber.model.data.response.SignUpResponse
import jr.brian.mybarber.model.data.services.BarberServiceResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-type: application/json")
    @POST("appUser/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @Headers("Content-type: application/json")
    @POST("appUser/login")
    fun signIn(@Body signInRequest: SignInRequest): Call<SignInResponse>

    @Headers("Content-type: application/json")
    @GET("barber/getBarbers")
    fun getBarbers(): Call<BarberResponse>

    @Headers("Content-type: application/json")
    @POST("barber/getBarberServices1")
    fun getBarberServices(@Body getBarberServicesReq: RequestBody): Call<BarberServiceResponse>

    @Headers("Content-type: application/json")
    @GET("appointment/currentAppointments/1")
    fun getCurrentAppts(): Call<CurrentApptResponse>


//    @GET("User/jobs")
//    fun getJobs(): Call<JobsResponse>
//
//    @GET("User/applyForJob/{user_id}/{job_id}")
//    fun applyForJob(
//        @Path("user_id") user_id: String,
//        @Path("job_id") job_id: String
//    ): Call<ApplyJobResponse>
}