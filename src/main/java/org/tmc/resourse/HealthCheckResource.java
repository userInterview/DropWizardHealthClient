package org.tmc.resourse;
 
import java.util.Map;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
 

@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class HealthCheckResource 
{
    private HealthCheckRegistry registry;
 
    public HealthCheckResource(HealthCheckRegistry registry) {
        this.registry = registry;
    }
     
    @GET
    public Map<String, Result>  getStatus(){
    	Map<String, Result> mapHealth = registry.runHealthChecks() ;
    	return registry.runHealthChecks() ;    	
    }
}