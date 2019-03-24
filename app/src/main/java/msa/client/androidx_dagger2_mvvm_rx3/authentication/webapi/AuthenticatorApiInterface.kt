package msa.client.androidx_dagger2_mvvm_rx3.authentication.webapi

import io.reactivex.Observable
import io.reactivex.Single
import msa.client.androidx_dagger2_mvvm_rx3.authentication.entity.MemberSignIn
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticatorApiInterface {

    // ?? POST 어떤식으로 보내는것으로 이해 해야 되는지 ??
    @POST("authenticate")
    @FormUrlEncoded
    fun signIn(@Field("username") email: String, @Field("password") password: String): Single<String>



    //post raw body
    @POST("authenticate")
    fun postLogin(@Body memberSignIn:MemberSignIn): Observable<Any>


    @POST("signUp")
    @FormUrlEncoded
    fun signUp(@Field("email") email: String, @Field("password") password: String): Single<String>
}