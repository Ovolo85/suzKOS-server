package de.patrickdassler.suzkos.semanticdata;

import static org.junit.Assert.*;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.junit.Before;
import org.junit.Test;


public class TripleStoreConnectionTest {

	private final String jUnitRepURL = "http://localhost:7200/repositories/";
	private final String jUnitRepName = "JUnitRep";
	private TripleStoreConnection conn;
	
	private HTTPRepository rep;
	private RepositoryConnection repConnReference;
	SimpleValueFactory vf = SimpleValueFactory.getInstance();
	
	@Before
	public void initCon () {
		
		rep = new HTTPRepository(jUnitRepURL + jUnitRepName);
		repConnReference = rep.getConnection();
		
		if (!repConnReference.isEmpty()) {
			repConnReference.clear();
		}
		
		repConnReference.close();
		
		conn = new TripleStoreConnection(jUnitRepURL, jUnitRepName);
		
	}
	
	@Test
	public void testAddTriple() {
		Statement teststatement = vf.createStatement(
				vf.createIRI("http://testsystemsub.test#", "testsubject"), 
				vf.createIRI("http://testsystempre.test#", "testpredicate"), 
				vf.createIRI("http://testsystemobj.test#", "testobject")) ;
		conn.addTriple(teststatement);
		
		assertTrue(repConnReference.hasStatement(teststatement, false));
		assertArrayEquals(new long[] {repConnReference.size()},  new long[] {1l});
	}

}
