package com.ais.brm.study.brmTest.chains;

import com.ais.brm.study.brmTest.chains.commands.AuditFilter;
import com.ais.brm.study.brmTest.chains.commands.FiftyDenominationDispenser;
import com.ais.brm.study.brmTest.chains.commands.HundredDenominationDispenser;
import com.ais.brm.study.brmTest.chains.commands.TenDenominationDispenser;
import org.apache.commons.chain.impl.ChainBase;



public class AtmWithdrawalChain extends ChainBase {
 
    public AtmWithdrawalChain() {
        super();
        addCommand(new HundredDenominationDispenser());
        addCommand(new FiftyDenominationDispenser());
        addCommand(new TenDenominationDispenser());
        addCommand(new AuditFilter());
    }
}