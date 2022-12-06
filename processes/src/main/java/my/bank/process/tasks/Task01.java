package my.bank.process.tasks;

import java.util.Map;
import java.util.logging.Level;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;
import my.bank.process.conf.Process01;
import my.bank.process.data.ComplexData;

@Log
@Component
@ExternalTaskSubscription(Process01.DO_WIERD_EXTERNAL_TASK)
public class Task01 implements ExternalTaskHandler {

	@Override
	public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

		try {

			Map<String, Object> vars = externalTask.getAllVariables();
			//String uid = (String) vars.get(ProcessPrueba.VAR_UID);


			VariableMap variables = Variables.createVariables();
			variables.put(Process01.VAR1, "hello");
			variables.put(Process01.VAR2, "world");
			variables.put(Process01.VAR_EXISTS, true);
			
			ComplexData cd = new ComplexData();
			cd.setAddress("el mundo");
			cd.setId("0000-000-000-0000");
			cd.setName("John Smith");
			variables.put(Process01.VAR_DATUM, cd);
			
			

			// complete the external task
			externalTaskService.complete(externalTask, variables);

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage(), ex);
		}

	}

}
