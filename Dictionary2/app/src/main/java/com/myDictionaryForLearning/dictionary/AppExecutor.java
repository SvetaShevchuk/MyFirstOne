package com.myDictionaryForLearning.dictionary;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static AppExecutor instance;
    private final Executor mainIo, subIo;

    public AppExecutor(Executor mainIo, Executor subIo) {
        this.mainIo = mainIo;
        this.subIo = subIo;
    }

    public static AppExecutor getInstance(){
        if(instance == null) instance = new AppExecutor(new MainThreadHandler(),
                Executors.newSingleThreadExecutor());
        return  instance;
    }

    public static class MainThreadHandler implements  Executor{

        private Handler mainHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            mainHandler.post(runnable);
        }
    }

    public Executor getMainIo() {
        return mainIo;
    }

    public Executor getSubIo() {
        return subIo;
    }
}
