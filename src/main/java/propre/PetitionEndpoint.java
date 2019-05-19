package propre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

@Api(name = "cloud", version = "v1",
namespace = @ApiNamespace(ownerDomain = "helloworld.example.com",
ownerName = "helloworld.example.com",
packagePath = ""))
public class PetitionEndpoint {
	@ApiMethod(name = "newPet")
	public Entity newPet(@Named("owner") String owner, @Named("contenu") String contenu, @Named("titre") String titre ) {
			
			Entity e = new Entity("Petition");
			e.setProperty("owner", owner);
			e.setProperty("titre", titre);
			e.setProperty("contenu", contenu);
			ArrayList<String> signataire= new ArrayList<>();
			e.setProperty("signataires", signataire);
			e.setProperty("nbSignataires", 0);		
			

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(e);
			
			return  e;
	}
	
	@ApiMethod(name = "listAllPets")
	public List<Entity> listPetitionsEntity() {
			Query q = new Query("Petition");
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			PreparedQuery prepQuery = datastore.prepare(q);
			List<Entity> result = prepQuery.asList(FetchOptions.Builder.withDefaults());
			return result;
	}

}
