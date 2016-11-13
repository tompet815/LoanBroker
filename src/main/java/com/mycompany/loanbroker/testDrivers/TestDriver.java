package com.mycompany.loanbroker.testDrivers;

import com.mycompany.loanbroker.interfaces.IMessaging;
import com.mycompany.loanbroker.reciplist.RecipientList;

public class TestDriver {

    public static void main(String[] argv) throws Exception {
        IMessaging rl = new RecipientList();
        rl.connect();
        rl.receive();
    }

}
