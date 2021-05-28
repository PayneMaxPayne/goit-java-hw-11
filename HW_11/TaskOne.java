package HW_11;

public class TaskOne {
    private static Timer timer = new Timer(12);

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                timer.startSession();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                timer.everyFiveSeconds();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }
}

class Timer {
    private int start = 1;
    private int end;

    public Timer(int end) {
        this.end = end;
    }

    public synchronized void startSession() throws InterruptedException {
        System.out.println("Timer started");
        while (end > 0) {
            Thread.sleep(1000);
            System.out.println(start + " sec");
            start++;
            end--;
            notifyAll();
            while (start % 5 == 0) {
                if (end <= 0) { break; }
                Thread.sleep(1000);
                System.out.println(start + " sec");
                wait();
            }
        }
        System.out.println("Timer stopped");
    }

    public synchronized void everyFiveSeconds() throws InterruptedException {
        while (end > 0) {
            while (start % 5 != 0) {
                if (end <= 0) { return; }
                wait();
            }
            System.out.println("5 sec run out");
            start++;
            end--;
            notifyAll();
        }
    }
}
