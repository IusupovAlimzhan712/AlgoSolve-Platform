import sys
import os


def binary_search(arr, target, steps):
    """
    Perform binary search with step-by-step logging.
    Returns the index of the target if found, -1 otherwise.
    """
    left, right = 0, len(arr) - 1
    target_id, target_label = target.split(",")

    while left <= right:
        mid = (left + right) // 2
        mid_id, mid_label = arr[mid].split(",")

        # 1-indexed mid output
        step_info = f"{mid + 1}: {mid_id}/{mid_label}"
        steps.append(step_info)

        mid_val = int(mid_id)
        target_val = int(target_id)

        if mid_val == target_val and mid_label == target_label:
            return mid
        elif mid_val < target_val:
            left = mid + 1
        else:
            right = mid - 1

    steps.append("-1")
    return -1


def main():
    if len(sys.argv) != 3:
        print("Usage: python binary_search_step.py <filename> <target>")
        return

    filename = sys.argv[1]
    target = sys.argv[2]
    data = []

    try:
        # Read and clean data
        with open(filename, "r") as file:
            for line in file:
                line = line.strip()
                if line:
                    data.append(line)

        steps = []
        result = binary_search(data, target, steps)

        # Save using ID only
        target_id = target.split(",")[0]
        output_filename = f"./output/binary_search_step_{target_id}.txt"
        os.makedirs(os.path.dirname(output_filename), exist_ok=True)

        with open(output_filename, "w") as f:
            for step in steps:
                f.write(f"{step}\n")

        print(f"Binary search steps written to {output_filename}")

    except FileNotFoundError:
        print(f"Error: File '{filename}' not found.")
    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()
