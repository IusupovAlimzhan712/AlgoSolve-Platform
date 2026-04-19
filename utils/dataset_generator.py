import random
import csv
import os


def generate_random_string(length):
    return "".join(random.choices("abcdefghijklmnopqrstuvwxyz", k=length))


def generate_dataset(dataset_size, output_dir="dataset"):
    os.makedirs(output_dir, exist_ok=True)
    output_file = os.path.join(output_dir, f"dataset_{dataset_size}.csv")

    print("Generating unique integers (≥ 1,000,000,000)...")
    unique_numbers = set()
    while len(unique_numbers) < dataset_size:
        value = random.randint(1_000_000_000, 2_147_483_647)
        unique_numbers.add(value)

        if len(unique_numbers) % 100000 == 0:
            print(f"Generated: {len(unique_numbers)}")

    print("Writing to CSV...")
    with open(output_file, mode="w", newline="") as file:
        writer = csv.writer(file)
        for number in unique_numbers:
            rand_str = generate_random_string(random.randint(5, 6))
            writer.writerow([number, rand_str])

    print(f"Dataset saved to: {output_file}")


if __name__ == "__main__":
    try:
        size = int(input("Enter the desired dataset size (e.g., 1000000): "))
        if size <= 0:
            raise ValueError("Dataset size must be positive.")
        generate_dataset(size)
    except ValueError as e:
        print(f"Invalid input: {e}")
