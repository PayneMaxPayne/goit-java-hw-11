package HW_11;

import java.util.StringJoiner;

public class TaskSecond {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(15);
        fizzBuzz.play();
    }
}

class FizzBuzz {
    private final int end;
    private volatile int currentNumber;
    private volatile StringJoiner result = new StringJoiner(", ", "", ".");

    public FizzBuzz(int end) {
        this.end = end;
        this.currentNumber = 1;
    }

    public void play() {

        new Thread(() -> number(), "numbers").start();

        new Thread(() -> fizz(), "Fizz numbers").start();

        new Thread(() -> buzz(), "Buzz numbers").start();

        new Thread(() -> fizzbuzz(), "FizzBuzz numbers").start();

        System.out.println("Result - " + result);
    }

    public synchronized void number() {
        while (currentNumber <= end) {
            addNumberToResult(String.valueOf(currentNumber));
            if (currentNumber % 3 == 0 || currentNumber % 5 == 0) {
                isWaiting();
            }
        }
    }

    public synchronized void fizz() {
        while (currentNumber <= end) {
            if (currentNumber % 3 == 0 && currentNumber % 5 != 0) {
                addNumberToResult("fizz");
            } else isWaiting();
        }
    }

    public synchronized void buzz() {
        while (currentNumber <= end) {
            if (currentNumber % 5 == 0 && currentNumber % 3 != 0) {
                addNumberToResult("buzz");
            } else isWaiting();
        }
    }

    public synchronized void fizzbuzz() {
        while (currentNumber <= end) {
            if (currentNumber % 15 == 0) {
                addNumberToResult("fizzbuzz");
            } else isWaiting();
        }
    }

    private void isWaiting() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void addNumberToResult(String numberIs) {
        result.add(String.valueOf(numberIs));
        currentNumber++;
        notifyAll();
    }

}
