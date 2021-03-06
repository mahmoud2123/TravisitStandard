package com.travisit.travisitstandard.vvm.vm;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.travisit.travisitstandard.data.Client;
import com.travisit.travisitstandard.model.User;
import com.travisit.travisitstandard.model.forms.SignInForm;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AuthenticationVM extends ViewModel {
    public MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable;
    public void signIn(String email, String password){
        SignInForm signInForm = new SignInForm(email, password);
        Observable<User> observable = Client.getINSTANCE().signIn(signInForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(observable.subscribe(o-> userMutableLiveData.setValue(o), e-> Log.d("PVMError",e.getMessage())));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
