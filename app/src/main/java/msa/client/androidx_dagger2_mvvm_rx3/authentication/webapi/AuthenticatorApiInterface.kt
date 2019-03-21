package msa.client.androidx_dagger2_mvvm_rx3.authentication.webapi

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticatorApiInterface {
    @POST("authenticate")
    @FormUrlEncoded
    fun signIn(@Field("username") email: String, @Field("password") password: String): Single<String>

    @POST("signUp")
    @FormUrlEncoded
    fun signUp(@Field("email") email: String, @Field("password") password: String): Single<String>
}