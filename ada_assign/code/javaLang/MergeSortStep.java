package javaLang;

import java.io.*;
import java.util.*;

public class MergeSortStep {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java MergeSortStep <filename> <startRow> <endRow>");
            return;
        }

        String filename = args[0];
        int startRow = Integer.parseInt(args[1]);
        int endRow = Integer.parseInt(args[2]);

        List<String> values = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNo = 0;
            while ((line = reader.readLine()) != null) {
                // Adjusted to treat startRow and endRow as 1-indexed
                if (lineNo + 1 >= startRow && lineNo + 1 <= endRow) {
                    values.add(line.trim());
                }
                lineNo++;
            }
        }

        List<String> steps = new ArrayList<>();
        // Add initial state
        steps.add(convertToSlashFormat(values));

        mergeSort(values, steps, 0, values.size() - 1);

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("./output/merge_sort_step_" + startRow + "_" + endRow + ".txt"))) {
            for (String step : steps) {
                writer.write(step);
                writer.newLine();
            }
        }
    }

    static void mergeSort(List<String> arr, List<String> steps, int left, int right) {
        if (left >= right)
            return;

        int mid = left + (right - left) / 2;

        // Recursively sort both halves
        mergeSort(arr, steps, left, mid);
        mergeSort(arr, steps, mid + 1, right);

        // Merge the sorted halves
        merge(arr, steps, left, mid, right);
    }

    static void merge(List<String> arr, List<String> steps, int left, int mid, int right) {
        List<String> leftArr = new ArrayList<>();
        List<String> rightArr = new ArrayList<>();

        for (int i = left; i <= mid; i++) {
            leftArr.add(arr.get(i));
        }
        for (int i = mid + 1; i <= right; i++) {
            rightArr.add(arr.get(i));
        }

        int i = 0, j = 0, k = left;

        while (i < leftArr.size() && j < rightArr.size()) {
            if (compare(leftArr.get(i), rightArr.get(j)) <= 0) {
                arr.set(k, leftArr.get(i));
                i++;
            } else {
                arr.set(k, rightArr.get(j));
                j++;
            }
            k++;
        }

        while (i < leftArr.size()) {
            arr.set(k, leftArr.get(i));
            i++;
            k++;
        }

        while (j < rightArr.size()) {
            arr.set(k, rightArr.get(j));
            j++;
            k++;
        }

        // Save step after each merge
        steps.add(convertToSlashFormat(arr));
    }

    static int compare(String a, String b) {
        int aNum = Integer.parseInt(a.split(",")[0]);
        int bNum = Integer.parseInt(b.split(",")[0]);
        return Integer.compare(aNum, bNum);
    }

    static String convertToSlashFormat(List<String> list) {
        List<String> formatted = new ArrayList<>();
        for (String entry : list) {
            String[] parts = entry.split(",");
            formatted.add(parts[0] + "/" + parts[1]);
        }
        return "[" + String.join(", ", formatted) + "]";
    }
}
