package javaLang;

import java.io.*;
import java.util.*;

public class BinarySearchStep {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java BinarySearchStep <filename> <target>");
            return;
        }

        String filename = args[0];
        String target = args[1];

        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.trim());
            }
        }

        List<String> steps = new ArrayList<>();
        binarySearch(data, target, steps);

        // Extract only the number part before the comma for filename
        String fileSafeTarget = target.split(",")[0];

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("./output/binary_search_step_" + fileSafeTarget + ".txt"))) {
            for (String step : steps) {
                writer.write(step);
                writer.newLine();
            }
        }
    }

    static int binarySearch(List<String> arr, String targetEntry, List<String> steps) {
        int targetVal = Integer.parseInt(targetEntry.split(",")[0]);
        int left = 0, right = arr.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String current = arr.get(mid);
            int midVal = Integer.parseInt(current.split(",")[0]);
            String[] parts = current.split(",");

            // Use 1-indexed position in steps
            steps.add((mid + 1) + ": " + parts[0] + "/" + parts[1]);

            if (midVal == targetVal && current.equals(targetEntry))
                return mid;
            else if (midVal < targetVal)
                left = mid + 1;
            else
                right = mid - 1;
        }

        steps.add("-1");
        return -1;
    }
}
