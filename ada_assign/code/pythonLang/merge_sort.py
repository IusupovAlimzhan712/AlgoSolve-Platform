import sys
import time
import os


def merge(left, right, result):
    """
    Merge two sorted arrays into the result array.
    """
    i = j = k = 0

    # Compare elements and merge
    while i < len(left) and j < len(right):
        left_val = int(left[i].split(",")[0])
        right_val = int(right[j].split(",")[0])

        if left_val <= right_val:
            result[k] = left[i]
            i += 1
        else:
            result[k] = right[j]
            j += 1
        k += 1

    # Copy remaining elements from left array
    while i < len(left):
        result[k] = left[i]
        i += 1
        k += 1

    # Copy remaining elements from right array
    while j < len(right):
        result[k] = right[j]
        j += 1
        k += 1


def merge_sort(arr):
    """
    MergeSort implementation using divide and conquer.
    """
    if len(arr) <= 1:
        return

    mid = len(arr) // 2
    left = arr[:mid].copy()
    right = arr[mid:].copy()

    merge_sort(left)
    merge_sort(right)
    merge(left, right, arr)


def main():
    if len(sys.argv) != 2:
        print("Usage: python merge_sort.py <filename>")
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
        merge_sort(data)
        end_time = time.time()

        running_time = (end_time - start_time) * 1000  # Convert to milliseconds

        # Write sorted data to output file
        output_filename = f"./output/merge_sort_{len(data)}.csv"
        os.makedirs(os.path.dirname(output_filename), exist_ok=True)

        with open(output_filename, "w") as output_file:
            for item in data:
                output_file.write(f"{item}\n")

        print(f"Merge Sort completed. Output saved to {output_filename}")
        print(f"Running time: {running_time:.0f} ms")

    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()
