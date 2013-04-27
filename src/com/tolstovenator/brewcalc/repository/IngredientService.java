package com.tolstovenator.brewcalc.repository;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class IngredientService extends Service{
	
	private final IBinder mBinder = new LocalBinder();
	
	private HopRepository hopRepository;
        /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
    	public IngredientService getService() {
            // Return this instance of LocalService so clients can call public methods
            return IngredientService.this;
        }
    }
	
	@Override
    public IBinder onBind(Intent intent) {
		if (hopRepository == null) {
			hopRepository = new HopRepository(getAssets());
		}
        return mBinder;
    }
	
	public HopRepository getHopRepository() {
		return hopRepository;
	}
	

}
