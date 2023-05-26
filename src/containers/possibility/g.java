import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaFunction;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.Map;

public class MyLambdaFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        // Access the Lambda logger
        LambdaLogger logger = context.getLogger();

        // Invoke the API Gateway using the VPC endpoint
        String vpcEndpointUrl = "https://<your-vpc-endpoint-url>";  // Replace with your VPC endpoint URL
        String apiPath = "/path/to/your/api";  // Replace with your API path

        // Create an AWS SDK client for API Gateway
        AWSLambdaClientBuilder lambdaClientBuilder = AWSLambdaClientBuilder.standard();
        lambdaClientBuilder.setEndpointConfiguration(new EndpointConfiguration(vpcEndpointUrl, "us-east-1"));  // Replace "us-east-1" with your region
        AWSLambda lambdaClient = lambdaClientBuilder.build();

        // Create the Lambda function invocation request
        InvokeRequest invokeRequest = new InvokeRequest();
        invokeRequest.setFunctionName(apiPath);
        invokeRequest.setInvocationType(InvocationType.RequestResponse);  // Change the invocation type if needed
        invokeRequest.setPayload(input.getBody());  // Pass the request body as the payload

        // Invoke the Lambda function
        InvokeResult invokeResult = lambdaClient.invoke(invokeRequest);

        // Process the response from the Lambda function
        String responsePayload = new String(invokeResult.getPayload().array());
        int responseStatusCode = invokeResult.getStatusCode();

        // Create the API Gateway response
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(responseStatusCode);
        responseEvent.setBody(responsePayload);

        // Return the response
        return responseEvent;
    }
}
