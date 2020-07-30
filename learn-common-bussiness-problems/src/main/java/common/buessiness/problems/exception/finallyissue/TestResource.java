package common.buessiness.problems.exception.finallyissue;

/**
 * 
 *  
 * @since 2020/7/30
 * @author : weizc 
 */
public class TestResource implements AutoCloseable{

    public void read() throws Exception {
        throw new RuntimeException("read error");
    }
    @Override
    public void close() throws Exception {
        throw new RuntimeException("close error");
    }
}
