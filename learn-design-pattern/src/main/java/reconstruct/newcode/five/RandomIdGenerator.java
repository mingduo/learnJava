package reconstruct.newcode.five;

import com.google.common.annotations.VisibleForTesting;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author : weizc
 * @since 2021/1/7
 */
public class RandomIdGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RandomIdGenerator.class);

    public String generate() throws IdGenerationFailureException {
        String substrOfHostName = null;
        try {
            substrOfHostName = getLastFiledOfHostName();
        } catch (UnknownHostException e) {
            throw new IdGenerationFailureException("...", e);
        }
        long currentTimeMillis = System.currentTimeMillis();
        String randomString = generateRandomAlphameric(8);
        return String.format("%s-%d-%s", substrOfHostName, currentTimeMillis, randomString);
    }

    private String getLastFiledOfHostName() throws UnknownHostException {
        String substrOfHostName = null;
        String hostName = InetAddress.getLocalHost().getHostName();
        if (hostName == null || hostName.isEmpty()) {
            throw new UnknownHostException("...");
        }
        substrOfHostName = getLastSubstrSplittedByDot(hostName);
        return substrOfHostName;
    }

    @VisibleForTesting
    protected String getLastSubstrSplittedByDot(String hostName) {
        if (hostName == null || hostName.isEmpty()) {
            throw new IllegalArgumentException("...");
        }
        String[] tokens = hostName.split("\\.");
        String substrOfHostName = tokens[tokens.length - 1];
        return substrOfHostName;
    }

    @VisibleForTesting
    protected String generateRandomAlphameric(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("...");
        }
        char[] randomChars = new char[length];
        int count = 0;
        Random random = new Random();
        while (count < length) {
            int maxAscii = 'z';
            int randomAscii = random.nextInt(maxAscii);
            boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
            boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count] = (char) (randomAscii);
                ++count;
            }
        }
        return new String(randomChars);
    }
}

