package com.mentormate.brickwork;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Brickwork {

	public static void main(String[] args) {
		/*
		 * Two integer variables for rows(M) and columns(N) and a scanner object for
		 * user input.
		 */
		Scanner scanner = new Scanner(System.in);
		int M, N;
		/* A try-catch-finally block to check the user input. */
		try {
			System.out.println("Please insert the number of rows and columns(Up to 100 each).");
			System.out.print("Number of rows: ");
			M = scanner.nextInt();
			System.out.print("Number of columns: ");
			N = scanner.nextInt();

			/* Validation for rows and columns. */
			if ((M < 2 || M > 100) || (N < 2 || N > 100)) {
				System.out.println("Invalid input! Rows and collumns must be between 2 and 100!");
			} else {
				/* Initializing two two dimensional arrays for the brick layouts */
				int[][] brickLayoutOne = new int[M][N];
				int[][] brickLayoutTwo = new int[M][N];

				/* User input for the first layer of bricks. */
				System.out.println("Enter the first brick layout's elements:");
				for (int row = 0; row < brickLayoutOne.length; row++) {
					for (int column = 0; column < brickLayoutOne[0].length; column++) {
						brickLayoutOne[row][column] = scanner.nextInt();
					}
				}

				/* A boolean object that indicates whether there is a solution or not. */
				boolean success = populateSecondBrickLayer(brickLayoutTwo, brickLayoutOne);
				if (success) {
					System.out.println("Layout Two output:");
					printBrickLayout(brickLayoutTwo);
					System.out.println("Layout One output:");
					printBrickLayout(brickLayoutOne);
				}
			}
		} catch (InputMismatchException ex) {
			System.out.println("Incorrect input type!");
		} finally {
			/* Closing the scanner object resource. */
			scanner.close();
		}
	}

	public static boolean populateSecondBrickLayer(int[][] arrayToPopulate, int[][] referenceArray) {
		try {
			/* Integer variables used to populate the the target array with the help of the
			 * the while loop below. 
			 */
			int rowBegin = 0;
			int rowEnd = arrayToPopulate.length - 1;
			int columnBegin = 0;
			int columnEnd = arrayToPopulate[0].length - 1;

			/* Reference array indices used in the while loop below. */
			int refBottomColumnEnd = referenceArray[0].length - 1;
			int refBottomMid = referenceArray[0].length - referenceArray.length - 1;
			int refTopColumnEnd = referenceArray[0].length - 1;
			int refTopMid = referenceArray[0].length - referenceArray.length - 1;

			int tempRowEnd = rowEnd;
			// int tempRowBegin = rowBegin + 1;

			/* A while loop used to traverse and populate the target array
			 * with a spiral matrix algorithm(starting from top-right down) 
			 * that uses data from the reference array
			 * as follows: from bottom-end to beginning-top.
			 */
			while (rowBegin <= rowEnd && columnBegin <= columnEnd) {

				for (int i = rowBegin; i <= rowEnd; i++) {
					arrayToPopulate[i][columnEnd] = referenceArray[rowEnd][refBottomColumnEnd];
					refBottomColumnEnd--;
				}
				columnEnd--;

				if (rowBegin <= rowEnd) {
					for (int i = columnEnd; i > columnBegin; i--) {
						if (refBottomMid == -1) {
							refBottomMid = referenceArray[0].length - 1;
							--tempRowEnd;
						}
						arrayToPopulate[rowEnd][i] = referenceArray[tempRowEnd][refBottomMid];
						refBottomMid--;
					}
				}

				if (columnBegin <= columnEnd) {
					for (int i = rowEnd; i >= rowBegin; i--) {
						arrayToPopulate[i][columnBegin] = referenceArray[rowBegin][refTopColumnEnd];
						refTopColumnEnd--;
					}
					rowEnd--;
					columnBegin++;
				}

				for (int i = columnBegin; i <= columnEnd; i++) {
					if (refTopMid == -1) {
						refTopMid = referenceArray[0].length - 1;
						// ++tempRowBegin;
					}
					arrayToPopulate[rowBegin][i] = referenceArray[rowBegin][refTopMid];
					refTopMid--;
				}
				rowBegin++;
			}
			/* Validation that no brick from the second layer lies exactly on a
			 * brick from the first layer.
			 */
			for(int i = 0; i < arrayToPopulate[0].length - 1; i++) {
				if(arrayToPopulate[arrayToPopulate.length - 1][i] == referenceArray[0][i] 
						&& arrayToPopulate[arrayToPopulate.length - 1][i+1] == referenceArray[0][i+1]) {
					System.out.println("-1, No brick from the second layer should lie "
							+ "exactly on a brick from the first layer!");
					return false;
				}
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException exx) {
			System.out.println("-1, No solution exists!");
			return false;
		}
	}

	/* 
	 * A print method that surrounds each brick with dash symbols "-".
	 * */
	public static void printBrickLayout(int[][] array) {
		for (int row = 0; row < array.length; row++) {
			for (int column = 0; column < array[0].length; column++) {
				if (column < (array[0].length - 1) && array[row][column] == array[row][column + 1]
						|| column == array[0].length - 1) {
					System.out.print(array[row][column] + " ");
				} else {
					System.out.print(array[row][column] + "-");
				}
			}
			System.out.println();
		}
	}
}
