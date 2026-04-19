import sys
import time
import os


def partition(arr, low, high):
    """
    Partition function for QuickSort.
    Places pivot element at its correct position.
    """
    pivot_val = int(arr[high].split(",")[0])
    i = low - 1
    for j in range(low, high):
        curr_val = int(arr[j].split(",")[0])
        if curr_val <= pivot_val:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1


def quick_sort(arr, low, high):
    """
    Iterative QuickSort implementation using a stack.
    This avoids recursion depth issues.
    """
    # Create a stack to store low and high indices
    stack = []

    # Push initial values onto stack
    stack.append((low, high))

    # Process stack until empty
    while stack:
        # Pop low and high from stack
        low, high = stack.pop()

        # Only partition if low < high
        if low < high:
            # Partition the array and get pivot index
            pi = partition(arr, low, high)

            # Push left subarray bounds to stack
            if pi - 1 > low:
                stack.append((low, pi - 1))

            # Push right subarray bounds to stack
            if pi + 1 < high:
                stack.append((pi + 1, high))


def main():
    if len(sys.argv) != 2:
        print("Usage: python quick_sort.py <filename>")
        return

    filename = sys.argv[1]
    data = []

    try:
        # Read data from file
        with open(filename, "r") as file:
            for line in file:
                line = line.strip()
                if line:
                    data.append(line)

        # Time the sorting operation
        start_time = time.time()
        quick_sort(data, 0, len(data) - 1)
        end_time = time.time()

        running_time = (end_time - start_time) * 1000  # Convert to milliseconds

        # Write sorted data to output file
        output_filename = f"./output/quick_sort_{len(data)}.csv"
        os.makedirs(os.path.dirname(output_filename), exist_ok=True)

        with open(output_filename, "w") as output_file:
            for item in data:
                output_file.write(f"{item}\n")

        print(f"Quick Sort completed. Output saved to {output_filename}")
        print(f"Running time: {running_time:.0f} ms")

    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()
