package patterns;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Data;

public class GetCreditScoreMock {

    private static boolean testIfMethodCalled;

    public GetCreditScoreMock() {
        testIfMethodCalled = false;
    }

    public boolean isTestIfMethodCalled() {
        return testIfMethodCalled;
    }

    public static void setTestIfMethodCalled( boolean testIfMethodCalled2 ) {
        testIfMethodCalled = testIfMethodCalled2;
    }

    public static void getCustomerRequest() throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        Data inputMessage = new Data();
        inputMessage.setSsn( "120990-1090" );
        String corrId = "2009";
        getCreditScoreWS( inputMessage.getSsn(), inputMessage, corrId );
    }

    private static void getCreditScoreWS( String ssn, Data message, String corrId ) {
        int creditScore = 100;
        message.setCreditScore( creditScore );
        try {
            sendScoreToGetBanks( message, corrId );
        } catch ( Exception ex ) {
            Logger.getLogger( GetCreditScoreMock.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    public static void sendScoreToGetBanks( Data message, String corrId ) throws IOException, TimeoutException, InterruptedException, ClassNotFoundException {
        setTestIfMethodCalled( true );
    }

}
