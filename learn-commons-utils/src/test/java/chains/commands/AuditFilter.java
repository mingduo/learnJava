package chains.commands;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;

public class AuditFilter implements Filter {

    @Override
    public boolean postprocess(Context context, Exception exception) {
        // send notification to bank and user
        System.out.println("AuditFilter postprocess = [" + context + "], exception = [" + exception + "]");
        return false;
    }

    @Override
    public boolean execute(Context context) throws Exception {
        System.out.println("AuditFilter = [" + context + "]");

        return false;
    }
}