package camunda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.DecisionDefinitionApi;
import org.openapitools.client.model.CorrelationMessageDto;
import org.openapitools.client.model.EvaluateDecisionDto;
import org.openapitools.client.model.VariableValueDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestEvaluateDecisionEvent {

  
  private static String camundaBaseUri = "http://localhost:8080/engine-rest";


  private static DecisionDefinitionApi apiDecision;
  
  @BeforeAll
  public static void setup() {
      log.debug("TestEvaluateDecisionEvent INIT");
      
      
      ApiClient ac = new ApiClient().setBasePath(camundaBaseUri);
      ac.setUsername("admin");
      ac.setPassword("admin");
      apiDecision = new DecisionDefinitionApi(ac);
  }
  
  
  @Test
  void doEvaluate() {
    
    Map<String, VariableValueDto> variables = new HashMap();
    
    
    VariableValueDto v0 = new VariableValueDto();
    v0.setType("String");
    v0.setValue("0000-00000-00000-00000-00000-00000-00000");
    variables.put("externalId", v0);
    
    VariableValueDto v1 = new VariableValueDto();
    v1.setType("integer");
    v1.setValue(190);
    variables.put("altura", v1);
    
    EvaluateDecisionDto decDto = new EvaluateDecisionDto();
    decDto.setVariables(variables);
    
    CorrelationMessageDto msgDto = new CorrelationMessageDto();
    msgDto.setVariablesInResultEnabled(true);
    msgDto.resultEnabled(true);
    msgDto.setProcessVariables(variables);
    msgDto.setMessageName(ProcessPrueba.START_MESSAGE_EVENT);
    
    try {
      List<Map<String, VariableValueDto>> result = apiDecision.evaluateDecisionByKey("TD_ALTO_DECISION", decDto);
      log.info(result.toString());
    } catch (ApiException e) {
      
      e.printStackTrace();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    
    
  }


}
