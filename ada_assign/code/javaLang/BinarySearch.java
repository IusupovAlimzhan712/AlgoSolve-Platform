package javaLang;

import java.io.*;
import java.util.*;

public class BinarySearch {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java BinarySearch <filename>");
            return;
        }

        String filename = args[0];
        List<String> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    data.add(line.trim());
                }
            }
        }

        int n = data.size();
        long bestTime = Long.MAX_VALUE, worstTime = Long.MIN_VALUE, totalTime = 0;

        for (String target : data) {
            long startTime = System.nanoTime();
            binarySearch(data, target);
            long duration = System.nanoTime() - startTime;

            bestTime = Math.min(bestTime, duration);
            worstTime = Math.max(worstTime, duration);
            totalTime += duration;
        }

        // Add a not-found target
        String notFoundTarget = "99999999,notfound";
        long startTime = System.nanoTime();
        binarySearch(data, notFoundTarget);
        long duration = System.nanoTime() - startTime;

        worstTime = Math.max(worstTime, duration);
        totalTime += duration;
        n++; // include not-found test in average

        // Convert nanoseconds to milliseconds
        double bestMs = bestTime / 1_000_000.0;
        double avgMs = (totalTime / (double) n) / 1_000_000.0;
        double worstMs = worstTime / 1_000_000.0;

        File outputDir = new File("./output");
        outputDir.mkdirs();
        String outputFile = "./output/binary_search_" + (n - 1) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(String.format("Best Case (ms): %.6f\n", bestMs));
            writer.write(String.format("Average Case (ms): %.6f\n", avgMs));
            writer.write(String.format("Worst Case (ms): %.6f\n", worstMs));
        }

        System.out.println("Binary search timing written to " + outputFile);
    }

    static int binarySearch(List<String> arr, String targetEntry) {
        int targetVal = Integer.parseInt(targetEntry.split(",")[0]);
        int left = 0, right = arr.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midEntry = arr.get(mid);
            int midVal = Integer.parseInt(midEntry.split(",")[0]);

            if (midVal == targetVal) {
                if (midEntry.equals(targetEntry)) {
                    return mid;
                } else {
                    return -1;
                }
            } else if (midVal < targetVal) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }
}
