import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BuggyExample1Test {

    @Test
    public void smallest() {
        assertEquals(4, BuggyExample1.smallest(4, 10));
    }

}