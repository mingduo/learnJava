package chains;

import org.apache.commons.chain.impl.CatalogBase;

public class AtmCatalog extends CatalogBase {

    public AtmCatalog() {
        super();
        addCommand("atmWithdrawalChain", new AtmWithdrawalChain());
    }
}