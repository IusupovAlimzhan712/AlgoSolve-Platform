import sys
import os


def convert_to_slash_format(arr):
    """
    Convert array to slash-separated format for logging.
    """
    formatted = [entry.split(",")[0] + "/" + entry.split(",")[1] for entry in arr]
    return "[" + ", ".join(formatted) + "]"


def partition(arr, low, high, steps):
    """
    Partition function for QuickSort with step logging.
    """
    pivot_val = int(arr[high].split(",")[0])
    i = low - 1

    for j in range(low, high):
        curr_val = int(arr[j].split(",")[0])
        if curr_val <= pivot_val:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]

    arr[i + 1], arr[high] = arr[high], arr[i + 1]

    # Log partition step with full list format
    steps.append(f"pi={i + 1} {convert_to_slash_format(arr)}")

    return i + 1


def quick_sort(arr, low, high, steps):
    """
    QuickSort implementation with step-by-step logging.
    """
    if low < high:
        pi = partition(arr, low, high, steps)
        quick_sort(arr, low, pi - 1, steps)
        quick_sort(arr, pi + 1, high, steps)


def main():
    if len(sys.argv) != 4:
        print("Usage: python quick_sort_step.py <filename> <startRow> <endRow>")
        return

    filename = sys.argv[1]
    start_row = int(sys.argv[2]) - 1  # Adjust to 0-based indexing
    end_row = int(sys.argv[3]) - 1

    data = []

    try:
        # Read only the selected rows
        with open(filename, "r") as file:
            for i, line in enumerate(file):
                if start_row <= i <= end_row:
                    line = line.strip()
                    if line:
                        data.append(line)

        steps = []
        steps.append(convert_to_slash_format(data))  # Initial state

        quick_sort(data, 0, len(data) - 1, steps)

        output_filename = f"./output/quick_sort_step_{start_row + 1}_{end_row + 1}.txt"
        os.makedirs(os.path.dirname(output_filename), exist_ok=True)

        with open(output_filename, "w") as output_file:
            for step in steps:
                output_file.write(step + "\n")

        print(f"Quick Sort steps written to {output_filename}")

    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()
