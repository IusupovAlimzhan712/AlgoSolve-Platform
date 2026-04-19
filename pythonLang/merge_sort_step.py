import sys
import os


def convert_to_slash_format(arr):
    """
    Convert array to comma-separated list where each element is in number/text (slash) format.
    Example: [123/foo, 456/bar]
    """
    converted = [item.replace(",", "/") for item in arr]
    return "[" + ", ".join(converted) + "]"


def compare(s1, s2):
    """
    Compare two strings by their numeric part.
    """
    val1 = int(s1.split(",")[0])
    val2 = int(s2.split(",")[0])
    return val1 - val2


def merge(arr, low, mid, high, steps):
    """
    Merge function with logging after each full merge.
    """
    left = arr[low : mid + 1]
    right = arr[mid + 1 : high + 1]

    i = j = 0
    k = low

    while i < len(left) and j < len(right):
        if compare(left[i], right[j]) <= 0:
            arr[k] = left[i]
            i += 1
        else:
            arr[k] = right[j]
            j += 1
        k += 1

    while i < len(left):
        arr[k] = left[i]
        i += 1
        k += 1

    while j < len(right):
        arr[k] = right[j]
        j += 1
        k += 1

    # Log current state of full array
    steps.append(convert_to_slash_format(arr))


def merge_sort(arr, low, high, steps):
    """
    Recursive MergeSort with step logging after each merge.
    """
    if low < high:
        mid = (low + high) // 2
        merge_sort(arr, low, mid, steps)
        merge_sort(arr, mid + 1, high, steps)
        merge(arr, low, mid, high, steps)


def main():
    if len(sys.argv) != 4:
        print("Usage: python merge_sort_step.py <filename> <startRow> <endRow>")
        return

    filename = sys.argv[1]
    start_row = int(sys.argv[2]) - 1
    end_row = int(sys.argv[3]) - 1

    data = []

    try:
        with open(filename, "r") as file:
            for i, line in enumerate(file):
                if start_row <= i <= end_row:
                    line = line.strip()
                    if line:
                        data.append(line)

        steps = []
        steps.append(convert_to_slash_format(data))  # initial state

        merge_sort(data, 0, len(data) - 1, steps)

        output_filename = f"./output/merge_sort_step_{start_row + 1}_{end_row + 1}.txt"
        os.makedirs(os.path.dirname(output_filename), exist_ok=True)

        with open(output_filename, "w") as output_file:
            for step in steps:
                output_file.write(f"{step}\n")

        print(f"Merge Sort steps written to {output_filename}")

    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()
