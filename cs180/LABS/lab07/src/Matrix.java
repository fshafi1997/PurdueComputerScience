/**
 * Created by farhanshafi on 10/7/16.
 */
public class Matrix {
    public boolean isSymmetric(int[][] matrix) {


        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                if (matrix.length != matrix[row].length) {
                    return false;
                }
                else if (matrix[row][column] != matrix[column][row]) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isDiagnol(int[][] matrix){
        for (int row = 0;row<matrix.length;row++){
            for (int column=0;column<matrix[row].length;column++){
                if (row!=column && matrix[row][column]!=0){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isIdentity(int[][] matrix){
        for (int row = 0;row<matrix.length;row++){
            for (int column=0;column<matrix[row].length;column++){
                if (matrix.length != matrix[row].length) {
                    return false;
                }
                else if (row!=column && matrix[row][column]!=0) {
                    return false;
                }
                else if (row==column && matrix[row][column]!=1){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isUpperTriangular(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                if (matrix.length != matrix[row].length) {
                    return false;
                }
                if (row>column) {
                    if (matrix[row][column] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isTridiagnol(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                if (matrix.length != matrix[row].length) {
                    return false;
                }
                if (Math.abs(row-column)>1) {
                    if (matrix[row][column] !=0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Matrix x = new Matrix();
        int[][] matrix = {{1, 1, 0, 0},
                          {1, 1, 1, 0},
                          {0, 1, 1, 1},
                          {0, 0, 1, 1}};
        System.out.println(x.isSymmetric(matrix));
        System.out.println(x.isDiagnol(matrix));
        System.out.println(x.isIdentity(matrix));
        System.out.println(x.isUpperTriangular(matrix));
        System.out.println(x.isTridiagnol(matrix));
    }
}