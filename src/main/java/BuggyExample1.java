public class BuggyExample1 {

    public static int smallest(int x, int y){
        int smallest ;
        if (x < y) {
            smallest = x;
        }
        else {
            smallest = x;  //error, should be y
        }
        return smallest;
    }


}
