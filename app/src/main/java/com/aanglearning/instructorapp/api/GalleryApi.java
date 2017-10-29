package com.aanglearning.instructorapp.api;

import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.AlbumImage;
import com.aanglearning.instructorapp.model.DeletedAlbum;
import com.aanglearning.instructorapp.model.DeletedAlbumImage;
import com.aanglearning.instructorapp.model.DeletedSubAlbum;
import com.aanglearning.instructorapp.model.DeletedSubAlbumImage;
import com.aanglearning.instructorapp.model.SubAlbum;
import com.aanglearning.instructorapp.model.SubAlbumImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Vinay on 29-10-2017.
 */

public interface GalleryApi {

    @POST("album")
    Call<Album> saveAlbum(@Body Album album);

    @GET("album/{id}/school/{schoolId}")
    Call<List<Album>> getAlbumAboveId(@Path("schoolId") long schoolId,
                                      @Path("id") long id);

    @GET("album/school/{schoolId}")
    Call<List<Album>> getAlbums(@Path("schoolId") long schoolId);

    @POST("deletedalbum")
    Call<DeletedAlbum> deleteAlbum(@Body DeletedAlbum deletedAlbum);

    @GET("deletedalbum/{id}/school/{schoolId}")
    Call<List<DeletedAlbum>> getDeletedAlbumsAboveId(@Path("schoolId") long schoolId,
                                                     @Path("id") long id);

    @GET("deletedalbum/school/{schoolId}")
    Call<List<DeletedAlbum>> getDeletedAlbums(@Path("schoolId") long schoolId);

    @POST("ai")
    Call<Void> saveAlbumImages(@Body List<AlbumImage> albumImages);

    @GET("ai/{id}/album/{albumId}")
    Call<List<AlbumImage>> getAlbumImagesAboveId(@Path("albumId") long albumId,
                                            @Path("id") long id);

    @GET("ai/album/{albumId}")
    Call<List<AlbumImage>> getAlbumImages(@Path("albumId") long albumId);

    @POST("deletedai")
    Call<Void> deleteAlbumImages(@Body List<DeletedAlbumImage> deletedAlbumImages);

    @GET("deletedai/{id}/album/{albumId}")
    Call<List<DeletedAlbumImage>> getDeletedAlbumImagesAboveId(@Path("albumId") long albumId,
                                                               @Path("id") long id);

    @GET("deletedai/album/{albumId}")
    Call<List<DeletedAlbumImage>> getDeletedAlbumImages(@Path("albumId") long albumId);

    @POST("subalbum")
    Call<SubAlbum> saveSubAlbum(@Body Album album);

    @GET("subalbum/{id}/album/{albumId}")
    Call<List<SubAlbum>> getSubAlbumAboveId(@Path("albumId") long albumId,
                                            @Path("id") long id);

    @GET("subalbum/album/{albumId}")
    Call<List<SubAlbum>> getSubAlbums(@Path("albumId") long albumId);

    @POST("deletedsubalbum")
    Call<DeletedSubAlbum> deleteSubAlbum(@Body DeletedAlbum deletedAlbum);

    @GET("deletedsubalbum/{id}/album/{albumId}")
    Call<List<DeletedSubAlbum>> getDeletedSubAlbumsAboveId(@Path("albumId") long albumId,
                                                           @Path("id") long id);

    @GET("deletedsubalbum/album/{albumId}")
    Call<List<DeletedSubAlbum>> getDeletedSubAlbums(@Path("albumId") long albumId);

    @POST("sai")
    Call<Void> saveSubAlbumImages(@Body List<SubAlbumImage> albumImages);

    @GET("sai/{id}/subalbum/{subAlbumId}")
    Call<List<SubAlbumImage>> getSubAlbumImagesAboveId(@Path("subAlbumId") long subAlbumId,
                                                       @Path("id") long id);

    @GET("sai/subalbum/{subAlbumId}")
    Call<List<SubAlbumImage>> getSubAlbumImages(@Path("subAlbumId") long subAlbumId);

    @POST("deletedsai")
    Call<Void> deleteSubAlbumImage(@Body List<DeletedAlbum> deletedAlbum);

    @GET("deletedsai/{id}/subalbum/{subalbumId}")
    Call<List<DeletedSubAlbumImage>> getDeletedSubAlbumImagesAboveId(@Path("subalbumId") long subalbumId,
                                                                     @Path("id") long id);

    @GET("deletedsai/subalbum/{subalbumId}")
    Call<List<DeletedSubAlbumImage>> getDeletedSubAlbumImages(@Path("subalbumId") long subalbumId);

}