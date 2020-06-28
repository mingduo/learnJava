package chains;

import chains.commands.AuditFilter;
import chains.commands.FiftyDenominationDispenser;
import chains.commands.HundredDenominationDispenser;
import chains.commands.TenDenominationDispenser;
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