import java.util.Random;

public class Main {
    private static final int[] THREAD_COUNTS = {1, 2, 4, 10};
    private static final int[] arraySize = {100, 1000, 10000, 100000};
    private static final int max_Num = 100;

    public static void main(String[] args) {
        int[] randomArray = generateRandomArray(arraySize[arraySize.length - 1]); // Generate array with the maximum size
        for (int threadCount : THREAD_COUNTS) {
            for (int arraySize : arraySize) {
                long startTime = System.nanoTime();
                long sum = calculateSum(randomArray, arraySize, threadCount);
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                startTime = System.nanoTime();
                long sequenceSum = sequentialSum(randomArray,arraySize);
                endTime = System.nanoTime();
                long sequenceSumTime = endTime - startTime;

                System.out.println("Sequential Sum in a Loop is " + sequenceSum +" Time: " + sequenceSumTime +" ns \n" +
                		"Array Size: " + arraySize +
                        ", Threads: " + threadCount +
                        ", Time: " + elapsedTime + " ns" +
                        ", Sum: " + sum);
            }
        }
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max_Num) + 1;
        }
        return array;
    }
    public static long sequentialSum (int[] array, int arraySize1) {
    	long sum = 0;
    	for (int i = 0; i<arraySize1 ; i++)
    	
    	{
    		sum += array[i];
    	}
		return sum;
    }

    private static long calculateSum(int[] array, int arraySize, int threadCount) {
        MyThread[] threads = new MyThread[threadCount];
        int segmentSize = arraySize / threadCount;
        int remaining = arraySize % threadCount;
        int start = 0;

        for (int i = 0; i < threadCount; i++) {
            int segmentStart = start;
            int segmentEnd = start + segmentSize + (i < remaining ? 1 : 0);
            threads[i] = new MyThread(array, segmentStart, segmentEnd);
            threads[i].start();
            start = segmentEnd;
        }

        try {
            for (MyThread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long totalSum = 0;
        for (MyThread thread : threads) {
            totalSum += thread.getSum();
        }

        return totalSum;
    }
}
