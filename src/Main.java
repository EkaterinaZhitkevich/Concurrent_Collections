import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    static ArrayBlockingQueue<String> queue1 = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> queue2 = new ArrayBlockingQueue<>(100);
    static ArrayBlockingQueue<String> queue3 = new ArrayBlockingQueue<>(100);
    static String letters = "abc";
    static int length = 100_000;
    static int textsAmount = 10_000;

    public static void main(String[] args) throws InterruptedException{

       Thread thread = new Thread(() -> {
            String[] texts = new String[textsAmount];
            for (int i = 0; i < texts.length; i++) {
                texts[i] = generateText(letters, length);
                queue1.offer(texts[i]);
                queue2.offer(texts[i]);
                queue3.offer(texts[i]);
//                System.out.println(queue1);
            }
        });
       thread.start();

        new Thread(() -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result1 = wordWithMaxAmountOfOneLetter(queue1, 'a');
            System.out.println("Слово с максимальным количеством букв а - " +result1);
        }).start();
        new Thread(() -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result2 = wordWithMaxAmountOfOneLetter(queue2, 'b');
            System.out.println("Слово с максимальным количеством букв b - " +result2);

        }).start();
        new Thread(() -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result3 = wordWithMaxAmountOfOneLetter(queue3, 'c');
            System.out.println("Слово с максимальным количеством букв c - " +result3);

        }).start();
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static long countLettersAmount(String word, char letter) {
        return word.chars().filter(c -> c == letter).count();
    }

    public static String wordWithMaxAmountOfOneLetter(ArrayBlockingQueue<String> queue, char letter) {
        String result = queue.poll();
        for (int i = 0; i < queue.size(); i++) {
            String temp = queue.poll();
            if (countLettersAmount(result, letter) < countLettersAmount(temp, letter)) {
                result = temp;
            }
        }
        return result;
    }

}
