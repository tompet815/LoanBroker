package com.mycompany.loanbroker.testDrivers;

import com.mycompany.loanbroker.reciplist.*;
import com.mycompany.loanbroker.interfaces.IMessaging;
import com.mycompany.loanbroker.translators.JSONTranslator;
import com.mycompany.loanbroker.translators.XMLTranslator;

public class TestTranslatorDriver {

    public static void main(String[] argv) throws Exception {
        IMessaging tr = new XMLTranslator();
        tr.connect();
        tr.receive();
        IMessaging jr = new JSONTranslator();
        jr.connect();
        jr.receive();
    }

}
