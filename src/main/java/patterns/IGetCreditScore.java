package patterns;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import models.Data;

public interface IGetCreditScore {

    static void getCustomerRequest() throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
    }

    static void sendScoreToGetBanks( Data message, String corrId ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
    }
}
