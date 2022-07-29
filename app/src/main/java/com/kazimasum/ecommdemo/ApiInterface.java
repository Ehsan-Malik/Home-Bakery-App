package com.kazimasum.ecommdemo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("payment.php")
    Call<Img_Pojo> uploadImage(@Field("id") String myid, @Field("image_name") String title, @Field("image") String image);
}
