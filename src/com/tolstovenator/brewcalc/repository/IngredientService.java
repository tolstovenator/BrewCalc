package com.tolstovenator.brewcalc.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class IngredientService extends Service{
	
	private static final String HOPS_XML = "hops.xml";

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
			FileInputStream localFile;
			try {
				System.out.println(getFilesDir());
				localFile = openFileInput(HOPS_XML);
				hopRepository = new HopRepository(localFile);
			} catch (FileNotFoundException e) {
				try {
					localFile = copyFileToLocal();
					hopRepository = new HopRepository(localFile);
				} catch (Exception e2) {
					hopRepository = new HopRepository();
				}
			}
			
		}
        return mBinder;
    }

	private FileInputStream copyFileToLocal() throws FileNotFoundException,
			IOException {
		FileInputStream localFile;
		byte[] buffer = new byte[256];
		FileOutputStream newFile = openFileOutput(HOPS_XML, Context.MODE_MULTI_PROCESS);
		InputStream stream = getAssets().open(HOPS_XML);
		int read;
		while ((read = stream.read(buffer, 0, buffer.length)) != -1) {
			newFile.write(buffer, 0, read);
		}
		newFile.close();
		localFile = openFileInput(HOPS_XML);
		return localFile;
	}
	
	public HopRepository getHopRepository() {
		return hopRepository;
	}
	

}
