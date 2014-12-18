package com.example.rbates.rxplayground;

import android.app.Application;

import java.util.Arrays;
import java.util.Collection;

import dagger.ObjectGraph;

public class RxPlaygroundApplication extends Application {

    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    private Collection<Object> getModules() {
        return Arrays.asList(new TagSearchModule());
    }

    ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
