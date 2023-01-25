package pir.demo.circuitbreakermonitoring.common.nofity;

public class SlackCircuitBreakerErrorMessage {

    public static String create(String status, String name){
        return "====== Send message to slack : ["+name+"] "+status+"!!!  ========";
    }
}
