package common.buessiness.problems.exception.finallyissue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : weizc
 * @since 2020/7/30
 */
@RestController
@RequestMapping("finallyissue")
@Slf4j
public class FinallyIssueController {


    @GetMapping("wrong")
    public void wrong() {
        try {
            log.info("try");
            throw new RuntimeException("try");
        //虽然 try 中的逻辑出现了异常，但却被 finally中的异常覆盖
        } finally {
            log.info("finally");
            throw new RuntimeException("finally");
        }
    }

    @GetMapping("right")
    public void right() {
        try {
            log.info("try");
            throw new RuntimeException("try");

        } finally {
            try {
                log.info("finally");

                throw new RuntimeException("finally");

            } catch (Exception e) {
                {
                    log.error("finally", e);
                }
            }
        }
    }

    @GetMapping("right2")
    public void right2() throws Exception {
        Exception e = null;
        try {
            log.info("try");
            throw new RuntimeException("try");

        } catch (Exception ex) {
            e = ex;

        } finally {
            try {
                log.info("finally");

                throw new RuntimeException("finally");

            } catch (Exception ex) {
                {
                    if (ex != null) {
                        e.addSuppressed(ex);
                    } else {
                        e = ex;
                    }
                    log.error("finally", e);
                }
            }
        }
        throw e;
    }

    //同样出现了 finally 中的异常覆盖了 try 中异常的问题
    @GetMapping("useresourcewrong")
    public void useresourceWrong() throws Exception {
        TestResource testResource = new TestResource();
        try {
            testResource.read();
        }finally {
            testResource.close();
        }
    }

    @GetMapping("useresourceright")
    public void useresourceRight() throws Exception {
        try(TestResource testResource = new TestResource()) {
            testResource.read();
        }
    }


}
