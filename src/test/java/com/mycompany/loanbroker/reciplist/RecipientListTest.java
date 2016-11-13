package com.mycompany.loanbroker.reciplist;

import com.mycompany.loanbroker.interfaces.IMessaging;
import java.io.IOException;
import junit.framework.TestCase;

public class RecipientListTest extends TestCase {
    IMessaging rl;
    public RecipientListTest(String testName) {
        super(testName);
        rl=new RecipientList();
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
 public void testSend()throws IOException {
//     boolean res=rl.send();
//     assertEquals(res, true);
 
 }
 public void testReceive() throws IOException {
//     boolean res=rl.receive();
//     assertEquals(res, true);
 
 }
}
