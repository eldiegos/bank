package my.bank.process.client.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.MessageApi;
import org.openapitools.client.model.CorrelationMessageDto;
import org.openapitools.client.model.MessageCorrelationResultWithVariableDto;
import org.openapitools.client.model.VariableValueDto;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;
import my.bank.process.bank.BankProcess;
import my.bank.process.bank.data.ComplexData;
import my.bank.process.client.ClientProcess;

@Log
@Component
@ExternalTaskSubscription(ClientProcess.SEND_INFO_TASK)
public class SendInfoTask implements ExternalTaskHandler {

	private static String camundaBaseUri = "http://localhost:8080/engine-rest";

	@Override
	public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

		try {

			Map<String, Object> vars = externalTask.getAllVariables();

			String clientName1 = (String) vars.get(ClientProcess.VAR_CLIENT_NAME_1);
			Long clientAge1 = (Long) vars.get(ClientProcess.VAR_CLIENT_AGE_1);
			String clientEmail1 = (String) vars.get(ClientProcess.VAR_CLIENT_EMAIL_1);

			String clientName2 = (String) vars.get(ClientProcess.VAR_CLIENT_NAME_2);
			Long clientAge2 = (Long) vars.get(ClientProcess.VAR_CLIENT_AGE_2);
			String clientEmail2 = (String) vars.get(ClientProcess.VAR_CLIENT_EMAIL_2);

			ComplexData client1 = new ComplexData();
			client1.setId("0000-000-000-1111");
			client1.setEmail(clientEmail1);
			client1.setName(clientName1);
			client1.setAge(clientAge1);

			ComplexData client2 = new ComplexData();
			client2.setId("0000-000-000-2222");
			client2.setEmail(clientEmail2);
			client2.setName(clientName2);
			client2.setAge(clientAge2);

			ApiClient ac = new ApiClient().setBasePath(camundaBaseUri);
			MessageApi apiMessage = new MessageApi(ac);

			Map<String, VariableValueDto> variables = new HashMap();

			VariableValueDto vClient1 = new VariableValueDto();
			// vClient1.setType("String");
			vClient1.setValue(client1);
			variables.put(BankProcess.BANK_START_VAR_CLIENT_1, vClient1);

			VariableValueDto vClient2 = new VariableValueDto();
			// vClient1.setType("String");
			vClient2.setValue(client2);
			variables.put(BankProcess.BANK_START_VAR_CLIENT_2, vClient2);

			CorrelationMessageDto msgDto = new CorrelationMessageDto();
			msgDto.setVariablesInResultEnabled(true);
			msgDto.resultEnabled(true);
			msgDto.setProcessVariables(variables);
			msgDto.setMessageName(BankProcess.BANK_START_MESSAGE);

			try {
				List<MessageCorrelationResultWithVariableDto> result = apiMessage.deliverMessage(msgDto);
				log.info(result.toString());
			} catch (ApiException e) {
				e.printStackTrace();
			}
			
			
			externalTaskService.complete(externalTask);

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage(), ex);
		}

	}

}
