package com.example.rxjavafirsttutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //Global variable containg all Observables that can be disposed
    CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating Observable object
        Observable<Task> taskObservable = Observable
                // Take a list of objects and turn it into an observable
                .fromIterable(DataSource.createTaskList())
                // designate a thread where the work is done (background)
                .subscribeOn(Schedulers.io())
                // designate a thread observing the results (main thread)
                .observeOn(AndroidSchedulers.mainThread());

        //Subscribing to the Observable Object to observe the changes
        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
                Log.d(TAG, "onSubscribe: Subscribe Called");
            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: " + task.getDescription());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: called");
            }
        });

        //An Observable can be converted to a Flowable by defining a Backpressure Strategy
        Flowable<Task> taskFlowable = taskObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //When the activity is destroyed all observables get disposed.
        // When using a ViewModel this can be done in onClear().
        disposables.clear();
    }
}