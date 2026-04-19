package javaLang;
import java.io.*;
import java.util.*;

public class QuickSort {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java QuickSort <filename>");
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
        quickSort(data, 0, data.size() - 1);
        long endTime = System.currentTimeMillis();

        String outputFilename = "./output/quick_sort_" + data.size() + ".csv";
        new File("./output").mkdirs(); // Create output directory if it doesn't exist
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }

        System.out.println("Quick Sort completed. Output saved to " + outputFilename);
        System.out.println("Running time: " + (endTime - startTime) + " ms");
    }

    static void quickSort(List<String> arr, int low, int high) {
        // Use a stack to store pairs of (low, high) indices
        Stack<int[]> stack = new Stack<>();
        
        // Push initial bounds onto stack
        stack.push(new int[]{low, high});
        
        // Process stack until empty
        while (!stack.isEmpty()) {
            // Pop low and high from stack
            int[] bounds = stack.pop();
            int currentLow = bounds[0];
            int currentHigh = bounds[1];
            
            // Only partition if low < high
            if (currentLow < currentHigh) {
                // Partition the array and get pivot index
                int pi = partition(arr, currentLow, currentHigh);
                
                // Push left subarray bounds to stack
                if (pi - 1 > currentLow) {
                    stack.push(new int[]{currentLow, pi - 1});
                }
                
                // Push right subarray bounds to stack
                if (pi + 1 < currentHigh) {
                    stack.push(new int[]{pi + 1, currentHigh});
                }
            }
        }
    }

    static int partition(List<String> arr, int low, int high) {
        int pivot = Integer.parseInt(arr.get(high).split(",")[0]);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            int currentVal = Integer.parseInt(arr.get(j).split(",")[0]);
            if (currentVal < pivot) {
                Collections.swap(arr, ++i, j);
            }
        }
        Collections.swap(arr, i + 1, high);
        return i + 1;
    }
}