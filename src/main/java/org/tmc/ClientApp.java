package org.tmc;

import io.dropwizard.Application;
//import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.tmc.configuration.ClientConfiguration;
import org.tmc.healths.AppHealthCheck;
import org.tmc.resourse.HealthCheckResource;

import com.codahale.metrics.health.HealthCheckRegistry;
 

public class ClientApp extends Application<ClientConfiguration> {

	public static void main(String[] args) throws Exception {
		new ClientApp().run(args);
	}

	@Override
	public String getName() {
		return "**************** TM NOVA - PRESENTATION DROPWIZARD CLIENT ********************";
	}

	@Override
	public void initialize(Bootstrap<ClientConfiguration> bootstrap) {
	}

	@Override
	public void run(final ClientConfiguration conf, final Environment env) throws Exception {	

		//Graphite graphite = null ;
		
		Client client = ClientBuilder.newClient();
		
		// Application health check
		env.healthChecks().register("HealthCheckReadFullNames", new AppHealthCheck(client));
		

		// Run multiple health checks
		HealthCheckRegistry healthCheckRegistry = env.healthChecks() ;
		env.jersey().register(new HealthCheckResource(healthCheckRegistry));
	}

}