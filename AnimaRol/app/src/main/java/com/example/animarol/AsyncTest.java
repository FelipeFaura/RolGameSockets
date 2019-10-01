package com.example.animarol;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncTest extends AsyncTask<Void,Void,Void> {
    int numberTest = 0;
    boolean control = true;


    @Override
    protected Void doInBackground(Void... voids) {
        while (control){
            try {
                Thread.sleep(2000);
            }catch (Exception e){

            }
            numberTest++;
            Log.d("ThreadTest", String.valueOf(numberTest));
        }
        return null;
    }

    public void SetControlFalse (){
        control = false;
    }
}
