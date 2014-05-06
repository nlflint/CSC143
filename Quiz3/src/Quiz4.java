/**
 * Created by nathanf on 5/5/14.
 */
public class Quiz4 {
    public static void main(String[] args) {

        Three object;
        object = new Three();
        object.banana(4);
    }




}

abstract class One {
    public void apple(boolean b) {
        System.out.println("b is " + b);
    }
    public void banana(double d) {
        System.out.println("d is " + d);
    }
    public abstract void cherry(int j);

}

class Two extends One {
    public void apple(int i) {
        System.out.println("i is " + i);
    }
    public void cherry(int q) {
        System.out.println("q is " + q);
    }

}

class Three extends Two {
    public void banana(int x) {
        System.out.println("x is " + x);
    }
    public void cherry(double w) {
        System.out.println("w is " + w);
    }
    public void apple(double t) {
        System.out.println("t is " + t);
    }

}

class ThreePrime extends Two {
    public double cherry(double s) {
        return s * 3;
    }
}


