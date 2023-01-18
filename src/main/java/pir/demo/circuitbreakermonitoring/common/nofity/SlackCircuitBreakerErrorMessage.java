package pir.demo.circuitbreakermonitoring.common.nofity;

public class SlackCircuitBreakerErrorMessage {

    public static String create(String status, String name, Exception exception){
        return "====== Send message to slack : ["+name+"] "+status+"! " +
                "| errorMessage : "+exception.getMessage()+" ========";
    }
}
