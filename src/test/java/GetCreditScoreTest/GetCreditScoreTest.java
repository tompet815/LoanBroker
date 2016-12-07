package GetCreditScoreTest;

import org.junit.Test;
import static org.junit.Assert.*;
import patterns.GetCreditScoreMock;

public class GetCreditScoreTest {

    GetCreditScoreMock getCreditScoreMock;

    @Test
    public void tetSend() throws Exception {
        getCreditScoreMock = new GetCreditScoreMock();
        assertTrue( getCreditScoreMock.isTestIfMethodCalled() == false );
        getCreditScoreMock.getCustomerRequest();
        assertTrue( getCreditScoreMock.isTestIfMethodCalled() == true );
    }

}
