package com.example.foodapp.retrofit

import com.example.foodapp.data.entity.CRUDAnswer
import com.example.foodapp.data.entity.FoodAnswer
import com.example.foodapp.data.entity.FoodGetCartAnswer
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodDao {

    //http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
    //http://kasimadalan.pe.hu/yemekler/sepeteYemekEkle.php -> sepete yemek ekleme
    //http://kasimadalan.pe.hu/yemekler/sepettekiYemekleriGetir.php -> speteki yemekleri getir
    //http://kasimadalan.pe.hu/yemekler/sepettenYemekSil.php -> speteki yemekleri sil

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun foodGetAll(): FoodAnswer

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(@Field("yemek_adi") yemek_adi: String,
                          @Field("yemek_resim_adi") yemek_resim_adi: String,
                          @Field("yemek_fiyat") yemek_fiyat: Int,
                          @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
                          @Field("kullanici_adi") kullanici_adi: String
                          ): CRUDAnswer

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getCart(@Field("kullanici_adi") kullanici_adi: String): FoodGetCartAnswer


    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun foodDelete(@Field("sepet_yemek_id") sepet_yemek_id: Int,
                           @Field("kullanici_adi") kullanici_adi: String
                           ): CRUDAnswer

}