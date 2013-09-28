package com.pokerclient;

import android.app.Application;

public class Username extends Application
{
	 private String username;
	 private String port;
	 
	    public String getSomeVariable() {
	        return this.username;
	    }
	    
	    public String getPort(){
	    	return this.port;
	    }

	    public void setSomeVariable(String s) {
	        this.username = s;
	    }
	    
	    public void setPort(String s) {
	        this.port = s;
	    }
	
}
