package javaLang;

import java.io.*;
import java.util.*;

public class QuickSortStep {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java QuickSortStep <filename> <startRow> <endRow>");
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
                // Use 1-based indexing for row selection
                if (lineNo + 1 >= startRow && lineNo + 1 <= endRow) {
                    values.add(line.trim());
                }
                lineNo++;
            }
        }

        List<String> steps = new ArrayList<>();
        steps.add(convertToSlashFormat(values));

        quickSort(values, 0, values.size() - 1, steps);

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("./output/quick_sort_step_" + startRow + "_" + endRow + ".txt"))) {
            for (String step : steps) {
                writer.write(step);
                writer.newLine();
            }
        }
    }

    static void quickSort(List<String> arr, int low, int high, List<String> steps) {
        if (low >= high)
            return;

        int pivotIndex = partition(arr, low, high, steps);
        quickSort(arr, low, pivotIndex - 1, steps);
        quickSort(arr, pivotIndex + 1, high, steps);
    }

    static int partition(List<String> arr, int low, int high, List<String> steps) {
        String pivot = arr.get(high);
        int pivotValue = Integer.parseInt(pivot.split(",")[0]);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            int currentValue = Integer.parseInt(arr.get(j).split(",")[0]);
            if (currentValue < pivotValue) {
                Collections.swap(arr, ++i, j);
            }
        }

        Collections.swap(arr, ++i, high);
        steps.add("pi=" + i + " " + convertToSlashFormat(arr));
        return i;
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
