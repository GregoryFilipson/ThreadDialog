import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        startThreadGroup();
        startCallable();
        startInvokeAny();
    }

    private static void startThreadGroup() {
        ThreadGroup mainGroup = new ThreadGroup("main group");

        MyThread myThread = new MyThread(mainGroup, "Поток 1");
        MyThread myThread2 = new MyThread(mainGroup, "Поток 2");
        MyThread myThread3 = new MyThread(mainGroup, "Поток 3");
        MyThread myThread4 = new MyThread(mainGroup, "Поток 4");

        myThread.start();
        myThread2.start();
        myThread3.start();
        myThread4.start();

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {

        }
        mainGroup.interrupt();
    }

    private static void startCallable() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        MyCallable myCallable = new MyCallable("Поток 1");
        MyCallable myCallable2 = new MyCallable("Поток 2");
        final Future<Integer> task = pool.submit(myCallable);
        final Future<Integer> task2 = pool.submit(myCallable2);
        final Integer resultOfTask = task.get();
        final Integer resultOfTask2 = task2.get();
        System.out.println("Количество сообщений, выведенных в консоль = " + resultOfTask);
        System.out.println("Количество сообщений, выведенных в консоль = " + resultOfTask2);
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);
    }

    private static void startInvokeAny() {
        int result;
        ExecutorService pool = Executors.newFixedThreadPool(2);
        MyCallable myCallable = new MyCallable("Поток X");
        MyCallable myCallable2 = new MyCallable("Поток Y");
        List<MyCallable> taskList = new ArrayList<>();
        taskList.add(myCallable);
        taskList.add(myCallable2);
        try {
            result = pool.invokeAny(taskList);
            System.out.printf("Победил поток %s: %s циклов!\n", MyThread.currentThread().getName(), result);
            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException | ExecutionException ignored) {
        }
    }
}



