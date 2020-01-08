// IRemoteService.aidl
package com.example.myapplication;
import com.example.myapplication.IRemoteServiceCallback;

interface IRemoteService {
    boolean registerCallback(IRemoteServiceCallback callback);
    boolean unregisterCallback(IRemoteServiceCallback callback);
    String getMessage();

}
