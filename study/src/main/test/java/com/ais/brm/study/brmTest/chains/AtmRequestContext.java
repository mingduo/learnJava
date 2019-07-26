package com.ais.brm.study.brmTest.chains;

import lombok.Data;
import org.apache.commons.chain.impl.ContextBase;

/**
 * <table border="1">
 * <tr><th>@Description:</th></tr>
 * <tr><td>@Date:Created in 2018/11/22</td>
 * </tr>
 * </table>
 *
 * @author :    weizc
 */
@Data
public class AtmRequestContext extends ContextBase {
    int totalAmountToBeWithdrawn;
    int noOfHundredsDispensed;
    int noOfFiftiesDispensed;
    int noOfTensDispensed;
    int amountLeftToBeWithdrawn;

}
