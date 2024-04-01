import java.util.Scanner;

public class MyThread extends Thread {
    private int[] array;
    private int start;
    private int end;
    private long sum;

    public MyThread(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
    }

    public long getSum() {
        return sum;
    }
}
