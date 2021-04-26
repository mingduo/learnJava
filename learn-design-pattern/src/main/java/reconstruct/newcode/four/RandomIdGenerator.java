package reconstruct.newcode.four;

import com.google.common.annotations.VisibleForTesting;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import reconstruct.newcode.IdGenerator;

/**
 * Id Generator that is used to generate random IDs. * * <p> * The IDs generated by this class are not absolutely unique, * but the probability of duplication is very low.
 */
public class RandomIdGenerator implements IdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(RandomIdGenerator.class);

    /**
     * Generate the random ID. The IDs may be duplicated only in extreme situatio   *   * @return an random ID
     */

    @Override
    public String generate() {    //...
        return null;
    }

    /**
     * Get the local hostname and
     * * extract the last field of the name string splitted by delimiter '.'.
     *
     * @return the last field of hostname.
     * Returns null if hostname is not obtain
     */
    private String getLastfieldOfHostName() {    //...
        return null;
    }

    /**
     * Get the last field of {@hostName} splitted by delemiter '.'.   *   * @param hostName should not be null   * @return the last field of {@hostName}. Returns empty string if {@hostName}
     * 重点回顾
     * 好了，今天的内容到此就讲完了。我们一块来总结回顾一下，你需要掌握的重点内容。
     * 在这节课中，我带你将小王写的凑活能用的代码，重构成了结构更加清晰、更加易读、更易 测试的代码，并且为其补全了单元测试。这其中涉及的知识点都是我们在理论篇中讲过的内 容，比较细节和零碎，我就不一一带你回顾了，如果哪里不是很清楚，你可以回到前面章节 去复习一下。
     * 实际上，通过这节课，我更想传达给你的是下面这样几个开发思想，我觉得这比我给你讲解 具体的知识点更加有意义。
     */
    @VisibleForTesting
    protected String getLastSubstrSplittedByDot(String hostName) {    //...
        return null;
    }

    /**
     * Generate random string which   * only contains digits, uppercase letters and lowercase letters.   *   * @param length should not be less than 0   * @return the random string. Returns empty string if {@length} is 0
     */

    @VisibleForTesting
    protected String generateRandomAlphameric(int length) {    //...  }
        return null;
    }

}