package com.example.rbates.rxplayground.searchapi;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface TagSearch {
    @GET("/search/{searchTerm}")
    List<SearchResult> search(@Path("searchTerm") String searchTerm);
}
