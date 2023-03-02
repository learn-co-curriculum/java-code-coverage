public class BuggyExample2 {

    public static boolean isEven(int x) {
        boolean result = true;  //error, should be false
        if (x % 2 == 0)
            result = true;
        return result;
    }
}
