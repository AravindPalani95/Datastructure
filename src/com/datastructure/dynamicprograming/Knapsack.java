package com.datastructure.dynamicprograming;

import java.util.Arrays;

// 0-1 knapsack problem
// ref 1: https://medium.com/@fabianterh/how-to-solve-the-knapsack-problem-with-dynamic-programming-eb88c706d3cf
// ref 2 for backtracking to print val and weight: https://www.geeksforgeeks.org/printing-items-01-knapsack/
public class Knapsack {
    public static void main(String[] args) {
        int val[] = {1, 4, 5, 7}; // item value
        int wt[] = {1, 3, 4, 5}; // item weight
        int W = 7; // knapsack weight
        int N = val.length;

        // dynamic programming table
        // row - item ex: 0,1,2...N
        // col - knapsack weight ex: 0,1,2...W
        int mat[][] = new int[N+1][W+1];

        // init mat table
        // row 0 is zero
        // col 0 is zero
        for(int i=0;i<W+1;i++)
            mat[0][i] = 0;
        for(int j=0;j<N+1;j++)
            mat[j][0] = 0;

        // 0-1 knapsack algorithm
        // row - item
        // col - weight
        // 1) optimum value not including N-th element
        //      max value of 0,1,2...N-1 - can obtained by mat[item-1][weight]
        // 2) optimum value including N-th element
        //      check N-th element weight <= current column weight
        //      max value of N - can be obtained by (N-th element value + mat[item-1][current column weight - Nth item weight]
        // mat[item][weight] = Max(optimum value not including N-th element, optimum value including N-th element)
        // solution is - mat[N][W]
        for(int item = 1; item < N+1; item++){
            for(int weight = 1; weight < W+1; weight++){
                int maxValueNotIncludingN = mat[item-1][weight];
                int maxValueIncludingN = 0; // initialize with zero

                int itemWeight = wt[item-1]; // item-1 to address the weight of current item
                if(itemWeight <= weight){
                    int itemValue = val[item-1]; // item-1 to address the value of current item
                    int remainWeight = weight - itemWeight;
                    maxValueIncludingN = itemValue + mat[item-1][remainWeight];
                }
                mat[item][weight] = Math.max(maxValueNotIncludingN, maxValueIncludingN);
            }
        }

        //print mat table
        for(int i=0;i<N+1;i++) {
            Arrays.stream(mat[i]).forEach(System.out::print);
            System.out.println();
        }

        // print weight included in knapsack
        // use backtracking
        // logic:
        // 1) iterate from last item and W with condition item > 0 && solution > 0
        // 1) if solution came from top then continue ex: mat[item-1][W] == solution -> continue;
        // 2) if solution did not come from top print current item weight
        //      a) reduce current item value from solution ex: solution = solution - val[item-1]
        //      b) reduce W by current item weight ex: W = W - wt[item - 1]
        System.out.println("Value and weights included in knapsack");
        System.out.println("V   W");
        int sol = mat[N][W];
        int colWeight = W;
        for(int item=N; item > 0 && sol > 0; item--){
            if(sol == mat[item-1][colWeight])
                continue;
            else{
                System.out.println(val[item-1]+"   "+wt[item-1]); // item -1 to address weight index in wt array
                sol = sol - val[item-1];
                colWeight = colWeight - wt[item-1];
            }
        }
        System.out.println("maximum value of item can be included in knapsack weight:" + mat[N][W]);
    }
}
