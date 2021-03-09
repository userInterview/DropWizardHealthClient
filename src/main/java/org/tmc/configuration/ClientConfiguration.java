package org.tmc.configuration;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientConfiguration extends Configuration {

	@NotEmpty
	@JsonProperty("yattr")
	private String yattr ;
	
	@JsonProperty
	public String getYattr() {
		return yattr;
	}
	
	@JsonProperty
	public void setYattr(String yattr) {
		this.yattr = yattr;
	}

	
	
	

}
