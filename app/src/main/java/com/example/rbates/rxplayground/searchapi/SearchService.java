package com.example.rbates.rxplayground.searchapi;

import retrofit.RestAdapter;

public class SearchService {
    private static final String URI = "http://tag-search.mobile-apps.guardianapis.com";
    private static TagSearch tagSearch;

    public static TagSearch get(){
        if(tagSearch == null){
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(URI)
                    .build();
            tagSearch = restAdapter.create(TagSearch.class);
        }
        return tagSearch;
    }
}
