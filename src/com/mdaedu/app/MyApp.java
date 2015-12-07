package com.mdaedu.app;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class MyApp extends ResourceConfig {

	public MyApp() {
		// TODO Auto-generated constructor stub
		packages("com.mdaedu.ws");
		register(JacksonJsonProvider.class);		
		register(MultiPartFeature.class);
	}

	
}
