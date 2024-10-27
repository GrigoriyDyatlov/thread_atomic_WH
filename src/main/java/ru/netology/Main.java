package ru.netology;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static AtomicInteger cnt3 = new AtomicInteger(0);
    public static AtomicInteger cnt4 = new AtomicInteger(0);
    public static AtomicInteger cnt5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        ExecutorService threadPoll = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


        Runnable logic1 = () -> {
            for (int i = 0; i < texts.length; i++) {
                if (isPalindrome(texts[i])) {
                    int length = texts[i].length();
                    switch (length) {
                        case 3:
                            cnt3.getAndIncrement();
                            break;
                        case 4:
                            cnt4.getAndIncrement();
                            break;
                        case 5:
                            cnt5.getAndIncrement();
                            break;
                    }
                }
            }
        };
        threadPoll.execute(logic1);

        Runnable logic2 = () -> {
            for (int i = 0; i < texts.length; i++) {
                if (isOneChar(texts[i])) {
                    int length = texts[i].length();
                    switch (length) {
                        case 3:
                            cnt3.getAndIncrement();
                            break;
                        case 4:
                            cnt4.getAndIncrement();
                            break;
                        case 5:
                            cnt5.getAndIncrement();
                            break;
                    }
                }
            }
        };
        threadPoll.execute(logic2);

        Runnable logic3 = () -> {
            for (int i = 0; i < texts.length; i++) {
                if (isAlphabetically(texts[i])) {
                    int length = texts[i].length();
                    switch (length) {
                        case 3:
                            cnt3.getAndIncrement();
                            break;
                        case 4:
                            cnt4.getAndIncrement();
                            break;
                        case 5:
                            cnt5.getAndIncrement();
                            break;
                    }
                }
            }
        };
        threadPoll.execute(logic3);
        while (!threadPoll.isTerminated()) {
        }
        threadPoll.shutdown();
        System.out.println("Красивых слов с длиной 3: " + cnt3 + " шт\n" +
                "Красивых слов с длиной 4: " + cnt4 + " шт\n" +
                "Красивых слов с длиной 5: " + cnt5 + " шт\n");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String str) {
        boolean result = true;
        int cnt = 0;
        while (result && cnt <= str.length() / 2) {
            if (str.charAt(cnt) != str.charAt(str.length() - cnt - 1)) result = false;
            cnt++;
        }
        return result;
    }

    public static boolean isOneChar(String str) {
        return str.length() == str.chars().filter(ch -> ch == str.charAt(0)).count();
    }

    public static boolean isAlphabetically(String str) {
        String sorted = Stream.of(str.split(""))
                .sorted()
                .collect(Collectors.joining());
        return str.equals(sorted);
    }
}