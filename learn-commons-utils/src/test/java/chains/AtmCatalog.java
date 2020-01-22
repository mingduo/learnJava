package com.ais.brm.study.brmTest.chains;

import org.apache.commons.chain.impl.CatalogBase;

public class AtmCatalog extends CatalogBase {

    public AtmCatalog() {
        super();
        addCommand("atmWithdrawalChain", new com.ais.brm.study.brmTest.chains.AtmWithdrawalChain());
    }
}