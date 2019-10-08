package com.ais.brm.study.brmTest.chains;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtmChainTest {

    /**
     * https://www.baeldung.com/apache-commons-chain
     *
     * @throws Exception
     */
    @Test
    public void givenInputsToContext_whenAppliedChain_thenExpectedContext() throws Exception {
        Context context = new AtmRequestContext();
        context.put("totalAmountToBeWithdrawn", 460);
        context.put("amountLeftToBeWithdrawn", 460);

        Catalog catalog = new AtmCatalog();
        Command atmWithdrawalChain = catalog.getCommand("atmWithdrawalChain");

        atmWithdrawalChain.execute(context);


        assertEquals(460, (int) context.get("totalAmountToBeWithdrawn"));
        assertEquals(0, (int) context.get("amountLeftToBeWithdrawn"));
        assertEquals(4, (int) context.get("noOfHundredsDispensed"));
        assertEquals(1, (int) context.get("noOfFiftiesDispensed"));
        assertEquals(1, (int) context.get("noOfTensDispensed"));
    }
}