package com.captcha.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
public class Captcha {
    @PostMapping("/submitGrid")
    public CaptchaResponse submitGrid(@RequestBody GridRequest gridRequest) {
        int[] flatGrid = gridRequest.getGridList();
        int gridSize = (int) Math.sqrt(flatGrid.length); // Assuming the grid is square

        // Convert 1D array to 2D array
        int[][] grid = convertTo2DArray(flatGrid, gridSize);

        // Validate the grid
        String validationMessage = validateGrid(grid);
        if (!validationMessage.equals("Valid")) {
            System.out.println(validationMessage);
            return new CaptchaResponse(false, validationMessage); // Return failure response
        }

        System.out.println("Valid input");
        return new CaptchaResponse(true, "Successful verification!"); // Return success response
    }

    private int[][] convertTo2DArray(int[] flatGrid, int gridSize) {
        int[][] grid = new int[gridSize][gridSize];
        for (int i = 0; i < flatGrid.length; i++) {
            grid[i / gridSize][i % gridSize] = flatGrid[i];
        }
        return grid;
    }

    private String validateGrid(int[][] grid) {
        int gridSize = grid.length;

        // Validate each row
        for (int[] row : grid) {
            if (!isRowValid(row)) {
                return "Invalid grid: A row has more than two consecutive suns or moons.";
            }
            if (!hasEqualSunsAndMoons(row)) {
                return "Invalid grid: A row does not have an equal number of suns and moons.";
            }
        }

        // Validate each column
        for (int col = 0; col < gridSize; col++) {
            if (!isColumnValid(grid, col)) {
                return "Invalid grid: A column has more than two consecutive suns or moons.";
            }
            if (!hasEqualSunsAndMoons(getColumn(grid, col))) {
                return "Invalid grid: A column does not have an equal number of suns and moons.";
            }
        }

        return "Valid";
    }

    private boolean isRowValid(int[] row) {
        for (int i = 0; i < row.length - 2; i++) {
            if (row[i] == row[i + 1] && row[i] == row[i + 2]) {
                return false; // Found three consecutive suns or moons
            }
        }
        return true;
    }

    private boolean isColumnValid(int[][] grid, int col) {
        for (int row = 0; row < grid.length - 2; row++) {
            if (grid[row][col] == grid[row + 1][col] && grid[row][col] == grid[row + 2][col]) {
                return false; // Found three consecutive suns or moons
            }
        }
        return true;
    }

    private boolean hasEqualSunsAndMoons(int[] line) {
        int suns = 0, moons = 0;
        for (int cell : line) {
            if (cell == 1) suns++;
            else if (cell == 0) moons++;
        }
        return suns == moons;
    }

    private int[] getColumn(int[][] grid, int col) {
        int[] column = new int[grid.length];
        for (int row = 0; row < grid.length; row++) {
            column[row] = grid[row][col];
        }
        return column;
    }

    }

    class GridRequest {
        private int[] gridList;

        // Getter and Setter
        public int[] getGridList() {
            return gridList;
        }

        public void setGridList(int[] gridList) {
            this.gridList = gridList;
        }
    }

