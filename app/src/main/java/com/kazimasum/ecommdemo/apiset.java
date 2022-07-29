package com.kazimasum.ecommdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiset
{
     @FormUrlEncoded
     @POST("signup.php")
    Call<signup_response_model> getregister(
          @Field("name") String name,
          @Field("email") String email,
          @Field("password") String password,
          @Field("mobile") String mobile,
          @Field("address") String address
     );


    @FormUrlEncoded
    @POST("login.php")
    Call<login_response_model> getlogin(
            @Field("mobile") String mobile,
            @Field("password") String password
    );

//For Fetching Products
    @GET("json_user_fetch.php")
    Call<List<responsemodel>> getdata();



    //For Fetching Products
    @GET("shop1.php")
    Call<List<responsemodel>> getshop1data();



    //For Fetching Products
    @GET("shop2.php")
    Call<List<responsemodel>> getshop2data();


    @FormUrlEncoded
    @POST("order.php")
    Call<orderresponsemodel> placeorder(
            @Field("mobile") String mobile,
            @Field("details") String details,
            @Field("amount") String amount,
            @Field("shop") String shop
    );

    @FormUrlEncoded
    @POST("delivery.php")
    Call<orderresponsemodel> delivery(
            @Field("id") String id,
            @Field("paymentmethod") String paymentmethod,
            @Field("address") String address
    );


    @FormUrlEncoded
    @POST("payment.php")
    Call<orderresponsemodel> payment(
            @Field("id") String id,
            @Field("accountno") String accountno
    );

//for fetching orders
    @GET("order_fetch.php")
    Call<List<ordermodel>> getorders(
            @Query("mobile") String mobile
    );




    @FormUrlEncoded
    @POST("fetch_user_profile.php")
    Call<login_response_model> getemail(
            @Field("mobile") String mobile
            );



    @GET("fetch_category.php")
    Call<List<responsemodel>> getcategory(
            @Query("info") String info
    );

    @FormUrlEncoded
    @POST("feedback.php")
    Call<login_response_model> getfeedback(
            @Field("id") String id,
            @Field("rate") String rate
    );

    @FormUrlEncoded
    @POST("order_status.php")
    Call<login_response_model> getstatus(
            @Field("id") String id,
            @Field("status") String status
    );
}

