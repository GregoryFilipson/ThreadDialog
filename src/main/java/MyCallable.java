import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {
   int result = 0;
   String name;

//    public MyCallable(int result, String name) {
//        this.result = result;
//        this.name = name;
//        result++;
//    }


    public MyCallable(String name) {
        this.name = name;
        result++;
    }

    public String getName() {
        return name;
    }

    @Override
    public Integer call() {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(250);
                System.out.printf("Я" + " %s. Всем привет!\n", getName());
                result++;
            }
        } catch (InterruptedException e) {

        } finally {
            System.out.printf("%s завершен\n", getName());
        }
        return result;
    }
}

