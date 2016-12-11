package GetCreditScoreTest;

import org.junit.Test;
import static org.junit.Assert.*;
import patterns.GetCreditScoreMock;

public class GetCreditScoreTest {

    GetCreditScoreMock getCreditScoreMock;

    @Test
    public void testSend() throws Exception {
        getCreditScoreMock = new GetCreditScoreMock();
        assertFalse( getCreditScoreMock.isTestIfMethodCalled() );
        getCreditScoreMock.getCustomerRequest();
        assertTrue( getCreditScoreMock.isTestIfMethodCalled() );
    }

}
