package de.patrickdassler.suzkos.semanticdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class TripleStoreConnection {

    private HTTPRepository rep;
    private RepositoryConnection repConn;

    private String tripleStoreURL;

    @Autowired
    public TripleStoreConnection(@Value("${app.tripleserver}") String repURL,
            @Value("${app.repositoryname}") String repName) {
        tripleStoreURL = repURL;
        rep = new HTTPRepository(repURL + repName);
        //LOGGER.info("Opening Connection to Triplestore at " + rep.getRepositoryURL());
        repConn = rep.getConnection();
    }
	
    /**
     * This Method will add a single Triple to the TripleStore.
     * 
     * @param tri
     *            The Triple to be added.
     */
    public void addTriple(Statement tri) {

        try (RepositoryConnection con = rep.getConnection()) {
            con.begin();
            try {
                //LOGGER.info("Adding a Statement to " + rep.getRepositoryURL());
                con.add(tri.getSubject(), tri.getPredicate(), tri.getObject());
                con.commit();
            } catch (RepositoryException e) {
                //LOGGER.error("Failure during Triple-Add processing." + e);
                con.rollback();
            }

        }

    }
    
    /**
     * This method will add a list of Triples to the TripleStore.
     * 
     * @param tris
     *            The List of Triples to be added.
     */
    public void addTriple(List<Statement> tris) {
    	//TODO implement a Unit Test
        try (RepositoryConnection con = rep.getConnection()) {
            con.begin();
            try {
                //LOGGER.info("Adding a List of " + tris.size() + " Statements to " + rep.getRepositoryURL());
                for (int i = 0; i < tris.size(); i++) {

                    con.add(tris.get(i).getSubject(), tris.get(i).getPredicate(), tris.get(i).getObject());

                }
                con.commit();
            } catch (RepositoryException e) {
                //LOGGER.error("Failure during Triple-Add processing." + e);
                con.rollback();
            }

        }

    }
    
    /**
     * This method will store a complete tree of statements into a specified
     * repository on the TripleStore.
     * 
     * @param tris
     *            The graph to be stored.
     * @param repository
     *            The repository to store to.
     */
    public void addModel(Model tris, String repository) {
    	//TODO implement a Unit Test
    	//TODO why is this implemented for a given rep instead of the default one?
        //LOGGER.info("Adding a List of " + tris.size() + " Statements to " + repository);

        HTTPRepository tempRep = new HTTPRepository(tripleStoreURL + repository);
        RepositoryConnection tempRepConn = tempRep.getConnection();

        tempRepConn.add(tris);

        tempRepConn.close();
    }
    
    
    public List<String[]> queryListOfStrings(String queryString) {
    	//TODO lokale Variablen prüfen, leichter wirr warr
    	List<String[]> result = new ArrayList<>();
    	    	
    	TupleQuery tupleQuery;
        TupleQueryResult queryResult;
        
    	tupleQuery = repConn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
        queryResult = tupleQuery.evaluate();
    	
        while (queryResult.hasNext()) {
            BindingSet bindingSet = queryResult.next();
            Set<String> bindings = bindingSet.getBindingNames();
            List<String> singleLine = new ArrayList<>();
            
            for (String b : bindings) {
            	singleLine.add(bindingSet.getValue(b).stringValue());
            }
            
            String[] simpleLine = new String[singleLine.size()];
            result.add(singleLine.toArray(simpleLine));
        }

        queryResult.close();
        
    	return result;
    }
    
}
