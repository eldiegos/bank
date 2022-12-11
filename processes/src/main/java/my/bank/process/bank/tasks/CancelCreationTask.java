package my.bank.process.bank.tasks;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;
import my.bank.process.bank.BankProcess;
import my.bank.process.bank.data.ComplexData;


@Log
@Component
@ExternalTaskSubscription(BankProcess.CANCEL_CREATION_TASK)
public class CancelCreationTask implements ExternalTaskHandler {

	@Override
	public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

		try {

			Map<String, Object> vars = externalTask.getAllVariables();
			
			LinkedHashMap client1 = (LinkedHashMap) vars.get(BankProcess.BANK_START_VAR_CLIENT_1);
			LinkedHashMap client2 = (LinkedHashMap) vars.get(BankProcess.BANK_START_VAR_CLIENT_2);

			log.info("TOO MUCH RISK. CANCELING CREATION");
			
			externalTaskService.complete(externalTask);

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage(), ex);
		}

	}

}
