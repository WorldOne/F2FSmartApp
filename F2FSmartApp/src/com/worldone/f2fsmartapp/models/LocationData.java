package com.worldone.f2fsmartapp.models;

import android.location.Location;

public class LocationData extends ClientUser{
	
	protected Location mLocation;
	protected ClientUser mClientUser;
	
	public Location getLocation() {
		return mLocation;
	}

	public void setLocation(Location location) {
		mLocation = location;
	}

	public ClientUser getClientUser() {
		return mClientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		mClientUser = clientUser;
	}
	
	
}
