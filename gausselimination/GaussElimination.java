/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gausselimination;

import java.util.Scanner;
import java.text.DecimalFormat;

/**
 * Performs Gaussian elimination on a user-inputted matrix
 * @author Tim Swyzen
 */
public class GaussElimination {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // declarations
        Scanner scan = new Scanner( System.in );
        DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
        double tempDub;
        int width,height;
        boolean hasallZero = false;
        
        //ask for size of matrix
        System.out.print( "Enter height of array (number of rows): ");
        height = scan.nextInt();
        
        System.out.print( "Enter width of array (number of columns): ");
        width = scan.nextInt();
        
        double[][] matrixUse = new double[height][width];
        
        //get user input for matrix
        for (int i = 1; i <= height; i++)
        {
            for (int j = 1; j <= width; j++)
            {
                System.out.println( "Enter value of matrix at " + i + "," + j );
                tempDub = scan.nextDouble();
                matrixUse[i-1][j-1] = tempDub;
            }
        }
        
        //confirm matrix
        printMatrix( matrixUse, width, height );
        
        //Pre-emptive row swapping for efficiency
        for (int i = 0; i < height; i++)
        {
            if ( matrixUse[i][i] == 0.0 )
            {
                if ( i+1 < height )
                   System.out.println( "Swapping rows " + (i+1) + " and " + (i+2) + "." );
                else
                    System.out.println( "Swapping rows " + (i+1) + " and " + (i) + "." );
                
                double[] saverDub = new double[width];
                
                hasallZero = true;
                for (int m=0;m<width;m++)
                {
                    //while we're at it, check for zero rows
                    if ( matrixUse[i][m] != 0.0 )
                        hasallZero = false;
                    
                    if ( i+1 < height )
                    {
                       saverDub[m] = matrixUse[i][m];
                       matrixUse[i][m] = matrixUse[i+1][m];
                       matrixUse[i+1][m] = saverDub[m];
                    }
                    else
                    {
                       saverDub[m] = matrixUse[i][m];
                       matrixUse[i][m] = matrixUse[i-1][m];
                       matrixUse[i-1][m] = saverDub[m];
                    }
                }
                
                //abort if we found one
                if (hasallZero)
                {
                    System.out.println( "Zero row detected. Aborting." );
                    scan.next();
                    return;
                }

                printMatrix( matrixUse, width, height );
            }
        }
        
        //start elimination! 
        for (int i = 0; i < height; i++)
        {
            hasallZero = true;
            for (int j = 0; j < width; j++) //looping thru rows/cols
            {
                //swap rows again if ever necessary
                if ( matrixUse[i][i] == 0.0 )
                {
                    if ( i+1 < height )
                       System.out.println( "Swapping rows " + (i+1) + " and " + (i+2) + "." );
                    else
                       System.out.println( "Swapping rows " + (i+1) + " and " + (i) + "." );
                 
                    double[] saverDub = new double[width];
                    for (int m=0;m<width;m++)
                    {
                        if ( i+1 < height )
                        {
                           saverDub[m] = matrixUse[i][m];
                           matrixUse[i][m] = matrixUse[i+1][m];
                           matrixUse[i+1][m] = saverDub[m];
                        }
                        else
                        {
                           saverDub[m] = matrixUse[i][m];
                           matrixUse[i][m] = matrixUse[i-1][m];
                           matrixUse[i-1][m] = saverDub[m];
                        }
                    }

                    printMatrix( matrixUse, width, height );
                }
                
                if ( i > 0 && j < i ) //make sure you want to eliminate
                {  
                    if ( matrixUse[i][j] != 0 ) //if its not already 0, do the elimination
                    {
                        double savedDub = matrixUse[i][j];
                        for (int k=0; k<width; k++)
                        {
                            matrixUse[i][k] = matrixUse[i][k]*matrixUse[j][j]-savedDub*matrixUse[j][k]; //multiply the number by the one above it for the sake of simplicity
                        }
                    }
                    
                    printMatrix( matrixUse, width, height );        
                }
                
            }
        }
        
        //second run through without swaps 
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++) //looping thru rows/cols
            {
                if ( i > 0 && j < i ) //make sure you want to eliminate
                {  
                    if ( matrixUse[i][j] != 0 ) //if its not already 0, do the elimination
                    {
                        double savedDub = matrixUse[i][j];
                        for (int k=0; k<width; k++)
                        {
                            matrixUse[i][k] = matrixUse[i][k]*matrixUse[j][j]-savedDub*matrixUse[j][k];
                        }
                    }
                    
                    printMatrix( matrixUse, width, height );        
                }
            }
        }
        
        //make it more easy to solve
        for (int h=0;h<height;h++)
        {
            if ( matrixUse[h][h] != 1 )
            {
                double savedDub = matrixUse[h][h];
                if (savedDub == 0)
                {
                    System.out.println( "Dividing by 0, exiting to save humanity. Try manually swapping rows." );
                    return;
                }
                
                for (int i=0;i<width;i++)
                {
                    matrixUse[h][i] = matrixUse[h][i]/savedDub;
                } 
            }
        }
        
        printMatrix( matrixUse, width, height );
        
        System.out.println( "Enter whatever you want to exit." );
        scan.next();
    }
    
    /**
     * Prints the given matrix
     * @param matrix The matrix to print out
     * @param width Number of columns
     * @param height Number of rows
     */
    public static void printMatrix( double[][] matrix, int width, int height )
    {
        DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
        System.out.println( "\n[----------------YOUR MATRIX-----------------]");
        for (int i = 1; i <= height; i++)
        {
            for (int j = 1; j <= width; j++)
            {
                //fixing weird java stuff
                if ( matrix[i-1][j-1] == -0.0 ) 
                    matrix[i-1][j-1] = 0.0;
                
                System.out.print( df2.format(matrix[i-1][j-1]) + "              " );
            }
            System.out.println("");
        }
    } 
    
}
