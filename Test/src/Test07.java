public class Test07 {

    public static int[] removeNumber(int[] arr, int number) {
        int count = 0; // Initialize a count to keep track of non-number elements.

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != number) {
                arr[count] = arr[i]; // Move non-number elements to the front of the array.
                count++; // Increment the count.
            }
        }

        // Create a new array with the correct length to hold the non-number elements.
        int[] result = new int[count];
        System.arraycopy(arr, 0, result, 0, count);
        

        return result; // Return the modified array with numbers removed.
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 2, 2, 1};
        int num = 2;
        int[] modifiedArr = removeNumber(arr, num);

        // Print the modified array
        for (int i : modifiedArr) {
            System.out.print(i + " ");
        }
    }
}
