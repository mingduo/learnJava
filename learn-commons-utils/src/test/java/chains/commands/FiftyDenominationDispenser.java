package chains.commands;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class FiftyDenominationDispenser implements Command {

    @Override
    public boolean execute(Context context) throws Exception {
        System.out.println("FiftyDenominationDispenser = [" + context + "]");


        int amountLeftToBeWithdrawn = (int) context.get("amountLeftToBeWithdrawn");
        if (amountLeftToBeWithdrawn >= 50) {
            context.put("noOfFiftiesDispensed", amountLeftToBeWithdrawn / 50);
            context.put("amountLeftToBeWithdrawn", amountLeftToBeWithdrawn % 50);
        }
        return false;
    }
}