package reconstruct.newcode.two;

import com.google.common.annotations.VisibleForTesting;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import reconstruct.newcode.IdGenerator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 第二轮重构：提高代码的可测试性
 */
public class RandomIdGenerator implements IdGenerator {
  private static final Logger logger = LoggerFactory.getLogger(RandomIdGenerator.class);

  @Override
  public String generate() {
    String substrOfHostName = getLastfieldOfHostName();
    long currentTimeMillis = System.currentTimeMillis();
    String randomString = generateRandomAlphameric(8);
    String id = String.format("%s-%d-%s", substrOfHostName, currentTimeMillis, randomString);
    return id;
  }

  private String getLastfieldOfHostName() {
    String substrOfHostName = null;
    try {
      String hostName = InetAddress.getLocalHost().getHostName();
      substrOfHostName = getLastSubstrSplittedByDot(hostName);
    } catch (UnknownHostException e) {
      logger.warn("Failed to get the host name.", e);
    }
    return substrOfHostName;
  }

  @VisibleForTesting
  protected String getLastSubstrSplittedByDot(String hostName) {
    String[] tokens = hostName.split("\\.");
    String substrOfHostName = tokens[tokens.length - 1];
    return substrOfHostName;
  }

  @VisibleForTesting
  protected String generateRandomAlphameric(int length) {
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