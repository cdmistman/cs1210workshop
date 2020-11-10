package LongestIncreasingSubsequence.colton;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
	// This is my solution to https://www.hackerrank.com/challenges/longest-increasing-subsequent/problem
	// I completed this in ~10 minutes, and did not work to optimize it.
    // Currently, it fails the HackerRank test suite because of time - 
    // this is fine, given it's otherwise correct and technical interviews don't
    // care about how fast your code runs, only that it runs correctly.
	//
    // On average, this computes in O(n^2)
    static int longestIncreasingSubsequence(int[] arr) {
		// Treat each index of the array as the end of the LIS.
		List<List<Integer>> unvisited = new ArrayList<>();
		for (int ii = 0; ii < arr.length; ii++) {
			List<Integer> toVisit = new ArrayList<>();
			toVisit.add(ii);
			unvisited.add(toVisit);
		}

		// For each LIS in unvisited, find every index that belongs
		// to that LIS before the currently-built LIS. In other words,
		// if there is another value that can be added to the beginning
		// of the LIS, then it's not done being built, so we must continue
		// to find more indices that belong.
		List<Integer> longest = null;
		while (!unvisited.isEmpty()) {
			List<Integer> curr = unvisited.remove(0);
			if (longest == null || curr.size() > longest.size()) {
				longest = curr;
			}

			// This is where we find every potential index that could
			// prepend the given LIS. We prepend the index and add it
			// to unvisited so that it could potentially be built later.
			int currMin = arr[curr.get(0)];
			for (int ii = 0; ii < curr.get(0); ii++) {
				if (arr[ii] < currMin) {
					List<Integer> toVisit = new ArrayList<>(curr);
					toVisit.add(0, ii);
					unvisited.add(toVisit);
				}
			}
		}

		if (longest == null) {
			return 0;
		} else {
			return longest.size();
		}
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            int arrItem = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            arr[i] = arrItem;
        }

        int result = longestIncreasingSubsequence(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
