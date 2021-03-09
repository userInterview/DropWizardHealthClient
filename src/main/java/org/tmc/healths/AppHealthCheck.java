package org.tmc.healths;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Optional;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.health.HealthCheck;

public class AppHealthCheck extends HealthCheck {
	private final Client client;

	public AppHealthCheck(Client client) {
		super();
		this.client = client;
	}

	@Override
	protected Result check() throws Exception {
		WebTarget webTarget = client.target("http://localhost:8080/users/jdbi-injected-provided-read");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = null;
		int iCodeHttp = 0;
		Optional<Response> or = null;
		Result resultatHealth = null;
		try {
			response = invocationBuilder.get();
			or = Optional.ofNullable(response);
			if (or.isPresent())
				iCodeHttp = response.getStatus();
			//erreur service provider
			if (iCodeHttp >= 500) {
				resultatHealth = Result.unhealthy("Code réponse du service : " + iCodeHttp + " - Le service ne répond pas");
				return resultatHealth;				
			}
			//service provider operationnel - datas éxistantes ?
			else {
				if (!response.hasEntity())
					resultatHealth = Result
							.unhealthy("Le service a répondu. Table vide. Pas de données \"full names\" en base");
				else {
					@SuppressWarnings("rawtypes")
					ArrayList users = response.readEntity(ArrayList.class);
					if (users.size() > 0)
						resultatHealth = Result.healthy("Le service a répondu et fournit des données \"full names\" : " + users.size()
								+ " enrigistrements de type full names.");
					else
						resultatHealth = Result.unhealthy("Le service a répondu mais ne contient pas de données \"full names\"");
				}
			}
		} 
	
		
		catch (ProcessingException pe) {
			if (iCodeHttp==0){
				resultatHealth = Result
						.unhealthy("Le service ne répond pas : " + pe.getMessage());
				}
			else {
			resultatHealth = Result
					.unhealthy("Code réponse du service : " + iCodeHttp + " - Le service ne répond pas : " + pe.getMessage());
			}
			return resultatHealth;
		}

		return resultatHealth;
	}
}