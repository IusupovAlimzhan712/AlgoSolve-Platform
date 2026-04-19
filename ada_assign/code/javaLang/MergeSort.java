package javaLang;
import java.io.*;
import java.util.*;

public class MergeSort {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java MergeSort <filename>");
            return;
        }

        String filename = args[0];
        List<String> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.trim());
            }
        }

        long startTime = System.currentTimeMillis();
        mergeSort(data);
        long endTime = System.currentTimeMillis();

        String outputFilename = "./output/merge_sort_" + data.size() + ".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }

        System.out.println("Merge Sort completed. Output saved to " + outputFilename);
        System.out.println("Running time: " + (endTime - startTime) + " ms");
    }

    public static void mergeSort(List<String> arr) {
        if (arr.size() <= 1)
            return;

        int middle = arr.size() / 2;
        List<String> left = new ArrayList<>(arr.subList(0, middle));
        List<String> right = new ArrayList<>(arr.subList(middle, arr.size()));

        mergeSort(left);
        mergeSort(right);

        merge(left, right, arr);
    }

    public static void merge(List<String> left, List<String> right, List<String> result) {
        int i = 0, l = 0, r = 0;
        while (l < left.size() && r < right.size()) {
            int leftVal = Integer.parseInt(left.get(l).split(",")[0]);
            int rightVal = Integer.parseInt(right.get(r).split(",")[0]);
            if (leftVal < rightVal)
                result.set(i++, left.get(l++));
            else
                result.set(i++, right.get(r++));
        }
        while (l < left.size())
            result.set(i++, left.get(l++));
        while (r < right.size())
            result.set(i++, right.get(r++));
    }
}
