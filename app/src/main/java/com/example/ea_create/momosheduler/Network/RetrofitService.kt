package com.example.ea_create.momosheduler.Network

import com.example.ea_create.momosheduler.Global.Companion.operator
import com.example.ea_create.momosheduler.Models.Operation
import com.example.ea_create.momosheduler.Models.UserCompte
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("/login/{login}/{password}")
    fun login(@Path("login") login: String, @Path("password") password: String): Call<ArrayList<UserCompte>>

    @GET("/gettodayoperationsbyoperator/{operator}")
    fun gettodayOperationbyOperator(@Path("operator") operator:String):Call<ArrayList<Operation>>


    @POST("/createusercomptes")
    fun createUserComptes(@Body newUserCompte: UserCompte): Call<UserCompte>

    @POST("/addoperations")
    fun createOperation(@Body operation: Operation): Call<Operation>
}