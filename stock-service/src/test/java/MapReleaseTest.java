import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MapReleaseTest {
    private static final Map<WeakReference<Integer>, String> MAP = new HashMap<>();

    @Test
    public void isRelease() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            putValue(i);
            if (i % 10 == 0) {
                System.gc();
                TimeUnit.MILLISECONDS.sleep(30);
                for (WeakReference<Integer> key : MAP.keySet()) {
                    System.out.print(key.get() + "=" + MAP.get(key));
                }
                System.out.println();
            }
        }
    }

    private void putValue(Integer integer) {
        MAP.put(new WeakReference<>(integer), "test");
    }
}
