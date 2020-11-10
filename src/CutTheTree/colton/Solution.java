package CutTheTree.colton;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
    // This is my solution to https://www.hackerrank.com/challenges/cut-the-tree/problem
    // I completed this in ~20 minutes, and did not work to optimize it.
    // Currently, it fails the HackerRank test suite because of time - 
    // this is fine, given it's otherwise correct and technical interviews don't
    // care about how fast your code runs, only that it runs correctly.
    // 
    // On average, this computes in O(n log n), where [ n = edges.size() ].
	static int cutTheTree(List<Integer> data, List<List<Integer>> edges) {
        // I use null here to indicate that the value hasn't been set.
        // While it's bad practice to use null, the other option would be to set
        // this value equal to Integer.MAX, which would be wrong in the case of
        // 1 node and 0 edges.
        Integer minDiff = null;
        for (List<Integer> edge : edges) {
            // The only nodes that we *know* which tree they belong to
            // are the ones that are in the edge being cut, and they
            // belong in the opposite trees to each other.
            Set<Integer> nodesLeft = new HashSet<>();
            nodesLeft.add(edge.get(0));
            Set<Integer> nodesRight = new HashSet<>();
            nodesRight.add(edge.get(1));

            // Construct a temporary edges list that we can use to
            // simulate cutting the current edge.
            List<List<Integer>> tEdges = new ArrayList<>(edges);
            tEdges.remove(edge);

            while (!tEdges.isEmpty()) {
                List<Integer> mEdge = tEdges.remove(0);
                Integer nLeft = mEdge.get(0);
                Integer nRight = mEdge.get(1);

                // If one of the currently-built trees contains one
                // of the nodes in mEdge, then add both nodes to the
                // tree. Since I'm using a Set to track the nodes being
                // added, I don't have to worry about redundant nodes
                // in the tree.
                if (nodesLeft.contains(nLeft) || nodesLeft.contains(nRight)) {
                    nodesLeft.add(nLeft);
                    nodesLeft.add(nRight);
                } else if (nodesRight.contains(nLeft) || nodesRight.contains(nRight)) {
                    nodesRight.add(nLeft);
                    nodesRight.add(nRight);
                } else {
                    // Since we don't know which tree the two current nodes
                    // belong in, we add this to the end of the list so we
                    // can determine which tree they belong to later.
                    tEdges.add(mEdge);
                }
            }


            // Calculate the weights of the two built trees for the
            // current simulation.
            int wLeft = 0;
            for (Integer node : nodesLeft) {
                wLeft += data.get(node - 1);
            }

            int wRight = 0;
            for (Integer node : nodesRight) {
                wRight += data.get(node - 1);
            }

            // if we haven't computed a minimum difference yet or
            // if the tracked minimum difference is larger than the
            // current difference, then we have a new value for the
            // tracked minimum difference.
            int diff = Math.abs(wLeft - wRight);
            if (minDiff == null || minDiff > diff) {
                minDiff = diff;
            }
        }

        if (minDiff == null) {
            return -1;
        } else {
            return minDiff.intValue();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        String[] dataTemp = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        List<Integer> data = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int dataItem = Integer.parseInt(dataTemp[i]);
            data.add(dataItem);
        }

        List<List<Integer>> edges = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            String[] edgesRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> edgesRowItems = new ArrayList<>();

            for (int j = 0; j < 2; j++) {
                int edgesItem = Integer.parseInt(edgesRowTempItems[j]);
                edgesRowItems.add(edgesItem);
            }

            edges.add(edgesRowItems);
        }

        int result = cutTheTree(data, edges);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
	}
}