import sys
import time
import os


def binary_search(arr, target):
    """
    Perform binary search on a sorted array of strings.
    Each string is expected to be comma-separated with a number in the first column.
    """
    left, right = 0, len(arr) - 1
    target_val = int(target.split(",")[0])

    while left <= right:
        mid = (left + right) // 2
        mid_val = int(arr[mid].split(",")[0])

        if mid_val == target_val:
            if arr[mid] == target:
                return mid
            else:
                return -1
        elif mid_val < target_val:
            left = mid + 1
        else:
            right = mid - 1

    return -1


def main():
    if len(sys.argv) != 2:
        print("Usage: python binary_search.py <filename>")
        return

    filename = sys.argv[1]

    try:
        with open(filename, "r") as file:
            data = [line.strip() for line in file if line.strip()]

        best_time, worst_time, total_time = float("inf"), 0, 0
        num_tests = len(data) + 1  # includes not-found case

        for target in data:
            start = time.perf_counter()
            binary_search(data, target)
            end = time.perf_counter()
            elapsed = end - start

            best_time = min(best_time, elapsed)
            worst_time = max(worst_time, elapsed)
            total_time += elapsed

        # Add a not-found target
        not_found_target = "99999999,notfound"
        start = time.perf_counter()
        binary_search(data, not_found_target)
        end = time.perf_counter()
        elapsed = end - start

        worst_time = max(worst_time, elapsed)
        total_time += elapsed

        avg_time = total_time / num_tests

        output_path = f"./output/binary_search_{len(data)}.txt"
        os.makedirs(os.path.dirname(output_path), exist_ok=True)

        with open(output_path, "w") as out:
            out.write(f"Best Case (ms): {best_time * 1000:.6f}\n")
            out.write(f"Average Case (ms): {avg_time * 1000:.6f}\n")
            out.write(f"Worst Case (ms): {worst_time * 1000:.6f}\n")

        print(f"Binary search timing written to {output_path}")

    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()
