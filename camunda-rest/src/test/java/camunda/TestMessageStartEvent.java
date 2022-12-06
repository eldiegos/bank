package camunda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.MessageApi;
import org.openapitools.client.api.ProcessDefinitionApi;
import org.openapitools.client.api.ProcessInstanceApi;
import org.openapitools.client.model.CorrelationMessageDto;
import org.openapitools.client.model.MessageCorrelationResultWithVariableDto;
import org.openapitools.client.model.VariableValueDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMessageStartEvent {

  
  private static String camundaBaseUri = "http://localhost:8080/engine-rest";


  private static MessageApi apiMessage;
  
  @BeforeAll
  public static void setup() {
      log.debug("TestMessageStartEvent INIT");
      
      
      ApiClient ac = new ApiClient().setBasePath(camundaBaseUri);
      ac.setUsername("admin");
      ac.setPassword("admin");
      apiMessage = new MessageApi(ac);
  }
  
  
  @Test
  void testMessageStartEvent() {
    
    Map<String, VariableValueDto> variables = new HashMap();
    VariableValueDto v1 = new VariableValueDto();
    v1.setType("String");
    v1.setValue("dperez");
    variables.put(ProcessPrueba.VAR_UID, v1);
    
    CorrelationMessageDto msgDto = new CorrelationMessageDto();
    msgDto.setVariablesInResultEnabled(true);
    msgDto.resultEnabled(true);
    msgDto.setProcessVariables(variables);
    msgDto.setMessageName(ProcessPrueba.START_MESSAGE_EVENT);
    
    try {
      List<MessageCorrelationResultWithVariableDto> result = apiMessage.deliverMessage(msgDto);
      log.info(result.toString());
    } catch (ApiException e) {
      
      e.printStackTrace();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    
    
  }


}
