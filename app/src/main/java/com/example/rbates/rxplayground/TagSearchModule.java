package com.example.rbates.rxplayground;

import com.example.searchapi.TagSearch;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(library = true, injects = MainActivity.class)
public class TagSearchModule {
    private static final String URI = "http://tag-search.mobile-apps.guardianapis.com";

    @Provides
    @Singleton
    TagSearch provideTagSearch() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URI)
                .build();
        return restAdapter.create(TagSearch.class);
    }
}
