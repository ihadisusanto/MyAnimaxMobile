package com.example.myanimaxmobile.request;

import com.example.myanimaxmobile.Activity.ChangePasswordActivity;
import com.example.myanimaxmobile.response.AnimeModel;
import com.example.myanimaxmobile.response.EpisodeModel;
import com.example.myanimaxmobile.response.userLogin;
import com.example.myanimaxmobile.response.userMe;
import com.example.myanimaxmobile.response.userRegister;
import com.example.myanimaxmobile.response.userUpdate;
import com.example.myanimaxmobile.Activity.ChangeProfileActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface restAPIInterface {
    @FormUrlEncoded
    @POST("login")
    Call<userLogin> getLoginInformation(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<userRegister> registerUser(
            @Field("username") String username,
            @Field("name") String name,
            @Field("email") String email,
            @Field("genre") String genre,
            @Field("password") String password,
            @Field("confpassword") String confpassword
    );

    @GET("me")
    Call<userMe> getMeInformation(
            @Header("Authorization") String token
    );

    @PUT("users/{username}")
    Call<userUpdate> updateUser(
            @Header("Authorization") String token,
            @Path("username") String username,
            @Body ChangeProfileActivity.dummy body
    );

    @PUT("users/updatepassword/{username}")
    Call<userUpdate> updatePassword(
            @Header("Authorization") String token,
            @Path("username") String username,
            @Body ChangePasswordActivity.dummyPassword body
    );

    @GET("anime")
    Call<List<AnimeModel>> getAnime(
            @Header("Authorization") String token
    );

    @GET("episode/{id}")
    Call<List<EpisodeModel>> getEpisode(
            @Header("Authorization") String token,
            @Path("id") String id
    );

    @FormUrlEncoded
    @POST("anime/search")
    Call<List<AnimeModel>> searchAnime(
            @Header("Authorization") String token,
            @Field("keyword") String keyword
    );
}
