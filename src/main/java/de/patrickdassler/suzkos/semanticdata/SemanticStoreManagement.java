package de.patrickdassler.suzkos.semanticdata;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.patrickdassler.suzkos.utility.Namespaces;
import de.patrickdassler.suzkos.utility.httpbodies.Build;
import de.patrickdassler.suzkos.utility.httpbodies.Functionality;
import de.patrickdassler.suzkos.utility.httpbodies.SUZ_MA;
import de.patrickdassler.suzkos.utility.httpbodies.SUZ_Task;
import de.patrickdassler.suzkos.utility.httpbodies.SubSystem;

@Service
public class SemanticStoreManagement {

	private String namespace;
	
	@Autowired
    private TripleStoreConnection tripleStore;
	
	@Autowired
    public SemanticStoreManagement(@Value("${app.namespace}") String databaseNamespace) {
        
		this.namespace = databaseNamespace;
        
    }
	
	public void addSUZ_MA(SUZ_MA tm) {
		
		ValueFactory vf = SimpleValueFactory.getInstance();
		
		List<Statement> maTriples = new ArrayList<>();
		
		IRI taskmanager = vf.createIRI(namespace,tm.getId());
		
		maTriples.add(vf.createStatement(taskmanager, RDF.TYPE, vf.createIRI(Namespaces.SUZONT, "SUZ-MA")));
		maTriples.add(vf.createStatement(taskmanager, vf.createIRI(Namespaces.SUZONT, "hasName"), vf.createLiteral(tm.getName())));
		maTriples.add(vf.createStatement(taskmanager, vf.createIRI(Namespaces.SUZONT, "hasMailAddress"), vf.createLiteral(tm.getMailAddress())));
		maTriples.add(vf.createStatement(taskmanager, vf.createIRI(Namespaces.SUZONT, "hasPhoneNumber"), vf.createLiteral(tm.getPhoneNumber())));
	
		tripleStore.addTriple(maTriples);
	}
	
	public List<SUZ_MA> getSUZ_MAs() {
		
		List<SUZ_MA> result = new ArrayList<SUZ_MA>() ;
		List<String[]> intermediateResult;
		
		String queryString = "select ?id ?name ?phone ?mail where { " + 
				"	?id <" + RDF.TYPE +"> <" + Namespaces.SUZONT + "SUZ-MA> . " + 
				"    ?id <" + Namespaces.SUZONT + "hasName> ?name . " + 
				"    ?id <" + Namespaces.SUZONT + "hasMailAddress> ?mail . " + 
				"    ?id <" + Namespaces.SUZONT + "hasPhoneNumber> ?phone . " + 
				" }";
		
		//System.out.println(queryString);
		
		intermediateResult = tripleStore.queryListOfStrings(queryString);
		
		for (String[] sl : intermediateResult) {
			result.add(new SUZ_MA(sl[0], sl[1], sl[3], sl[2]));
		}
				
		return result;
	}
	
	public void addSUZTask(SUZ_Task t) {
		
		ValueFactory vf = SimpleValueFactory.getInstance();
		
		List<Statement> taskTriples = new ArrayList<>();
		
		IRI task = vf.createIRI(namespace, t.getId());
		
		taskTriples.add(vf.createStatement(task, RDF.TYPE, vf.createIRI(Namespaces.SUZONT, t.getType())));
		taskTriples.add(vf.createStatement(task, vf.createIRI(Namespaces.SUZONT, "hasTaskID"), vf.createLiteral(t.getId())));
		taskTriples.add(vf.createStatement(task, vf.createIRI(Namespaces.SUZONT, "hasTitle"), vf.createLiteral(t.getTitle())));
		taskTriples.add(vf.createStatement(task, vf.createIRI(Namespaces.SUZONT, "hasWorkingDirectory"), vf.createLiteral(t.getWorkingDirectory())));
		taskTriples.add(vf.createStatement(vf.createIRI(t.getTaskmanager()), vf.createIRI(Namespaces.SUZONT, "IsTaskmanager"), task ));
		for (String con : t.getContributors()) {
			taskTriples.add(vf.createStatement(vf.createIRI(con), vf.createIRI(Namespaces.SUZONT, "IsContributor"), task ));
		}
		
		tripleStore.addTriple(taskTriples);
	}
	
	public void addEFA() {
		ValueFactory vf = SimpleValueFactory.getInstance();
		IRI efa = vf.createIRI(namespace, "EFA");
		
		Statement efaTriple = vf.createStatement(efa, RDF.TYPE, vf.createIRI(Namespaces.SUZONT, "TechnicalSystem"));
		
		tripleStore.addTriple(efaTriple);
	}

	public void addSubSystem(SubSystem ss) {
		ValueFactory vf = SimpleValueFactory.getInstance();
		
		List<Statement> ssTriples = new ArrayList<>();
		
		IRI subSystem = vf.createIRI(namespace, ss.getName().replaceAll("\\s", ""));
		
		ssTriples.add(vf.createStatement(subSystem, RDF.TYPE, vf.createIRI(Namespaces.SUZONT, "TechnicalSystem")));
		ssTriples.add(vf.createStatement(subSystem, vf.createIRI(Namespaces.SUZONT, "IsPartOf"), vf.createIRI(namespace, ss.getParent())));
		ssTriples.add(vf.createStatement(subSystem, vf.createIRI(Namespaces.SUZONT, "hasReadableName"), vf.createLiteral(ss.getName())));
		
		tripleStore.addTriple(ssTriples);
	}

	public void addFunctionality(Functionality f) {
		ValueFactory vf = SimpleValueFactory.getInstance();
		
		List<Statement> fTriples = new ArrayList<>();
		
		IRI functionality = vf.createIRI(namespace, f.getName().replaceAll("\\s", ""));
		
		fTriples.add(vf.createStatement(functionality, RDF.TYPE, vf.createIRI(Namespaces.SUZONT, "SoftwareFunctionality")));
		fTriples.add(vf.createStatement(functionality, vf.createIRI(Namespaces.SUZONT, "IsPartOf"), vf.createIRI(namespace, f.getLri())));
		fTriples.add(vf.createStatement(functionality, vf.createIRI(Namespaces.SUZONT, "hasReadableName"), vf.createLiteral(f.getName())));
		
		tripleStore.addTriple(fTriples);
	}

	public Build getAcBuild() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
