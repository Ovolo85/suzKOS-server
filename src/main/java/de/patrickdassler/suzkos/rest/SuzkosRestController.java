package de.patrickdassler.suzkos.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.patrickdassler.suzkos.semanticdata.SemanticStoreManagement;
import de.patrickdassler.suzkos.utility.httpbodies.Build;
import de.patrickdassler.suzkos.utility.httpbodies.Functionality;
import de.patrickdassler.suzkos.utility.httpbodies.SUZ_MA;
import de.patrickdassler.suzkos.utility.httpbodies.SUZ_Task;
import de.patrickdassler.suzkos.utility.httpbodies.SubSystem;

@RestController
public class SuzkosRestController {

	@Autowired
    private SemanticStoreManagement semanticStoreManagement;
	
	@RequestMapping(method = RequestMethod.POST, value = "/suz_ma")
    public void addNewSuzMa(@RequestBody SUZ_MA tm) {
        //LOGGER.info("Received POST command with no treatment Data attached.");
        semanticStoreManagement.addSUZ_MA(tm);
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/task")
    public void addNewTask(@RequestBody SUZ_Task task) {
        //LOGGER.info("Received POST command with no treatment Data attached.");
        semanticStoreManagement.addSUZTask(task);
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/efa")
    public void addEfa() {
        //LOGGER.info("Received POST command with no treatment Data attached.");
        semanticStoreManagement.addEFA();
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/subsystem")
    public void addSubSystem(@RequestBody SubSystem ss) {
        //LOGGER.info("Received POST command with no treatment Data attached.");
        semanticStoreManagement.addSubSystem(ss);
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/functionality")
    public void addSubSystem(@RequestBody Functionality f) {
        //LOGGER.info("Received POST command with no treatment Data attached.");
        semanticStoreManagement.addFunctionality(f);
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/suz_ma")
	@ResponseBody
	public List<SUZ_MA> getSuzMa() {
		return semanticStoreManagement.getSUZ_MAs();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/ac_build")
	@ResponseBody
	public Build getAcBuild() {
		return semanticStoreManagement.getAcBuild();
	}
	
}
