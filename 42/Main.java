/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 9, 27/5/22
 */
import java.io.File;//Allows file stuff
import java.io.FileWriter;//Allows the writing of files so saved.
import java.io.IOException;//Allows errors for files.
import java.util.Scanner;//Importing Scanner so keyboard inputs can be recorded.
public class Main
{
    // instance variables - replace the example below with your own
    final int xSize=20;
    final int ySize=50;
    public boolean[][] grid = new boolean[ySize][xSize];
    public boolean[][] lastGrid = new boolean[ySize][xSize];
    final String SWAP_COMMAND="swap";
    final String END_COMMAND="end";
    final String START_COMMAND="start";
    final String STOP_COMMAND="stop";
    final String ONE_GEN_COMMAND="go";
    final String ONE_K_GEN_COMMAND="";
    final String UNDO_COMMAND="undo";
    final String RENDER_COMMAND="render";
    int gen=0;
    /**
     * Constructor for objects of class Main
     */
    public Main()
    {
        boolean running=true;
        //int gen=0;
        while (running){
            //Reads input
            Scanner scanner = new Scanner(System.in);//Set up the scanner
            System.out.println("What do you want to do?");
            String scannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
            boolean validCommand=false;//If none of the commands work, then this should tell the user
            //Loops if active
            boolean active=false;
            boolean generation=false;
            //Swap mode
            boolean swapMode = false;
            //If true, then it wont render at the end of this loop. Used for the generation loop so 
            boolean dontPrint=false;
            //Do generations command
            ///*      Code to display last generation
            System.out.println("Last Generation");
            renderArray(lastGrid);//*/
            if ((scannerOutput.equals(START_COMMAND))){
                dontPrint=true;
                active=true;
                validCommand=true;
                System.out.println("Starting?");}
            if ((scannerOutput.equals(ONE_GEN_COMMAND))){
                dontPrint=true;
                generation=true;
                active=true;
                validCommand=true;
                System.out.println("Starting one gen?");
                //lastGrid=grid;
                doGen();}
            if ((scannerOutput.equals(ONE_K_GEN_COMMAND))){
                dontPrint=true;
                active=true;
                validCommand=true;
                System.out.println("Starting one k gen?");
                for(int i=0; i<1000;i++){
                    //lastGrid=grid;
                    doGen();
                }}
            /*      Code to display last generation
            System.out.println("Last Generation");
            renderArray(lastGrid);//*/
            boolean endCommands=false;
            while(!endCommands){
                endCommands=true;
            }
            //end command
            if ((scannerOutput.equals(END_COMMAND))){
                running=false;
                validCommand=true;
                System.out.println("end?");}
            //swap command
            if ((scannerOutput.equals(SWAP_COMMAND))){
                validCommand=true;
                swapMode=true;
                dontPrint=true;
                while(swapMode){
                    System.out.println("What do you wish to swap? (Type '"+SWAP_COMMAND+"' to exit)");
                    String cellSwapping[]=scanner.nextLine().split(" ");
                    if(cellSwapping[0].equals(SWAP_COMMAND)){
                        swapMode=false;
                    }else if(cellSwapping[0].equals(END_COMMAND)){
                        running=false;
                        swapMode=false;
                    }else{
                        swap(cellSwapping);
                    }
                }
            }

            //undo command
            if ((scannerOutput.equals(UNDO_COMMAND))){
                validCommand=true;
                if(!(grid==lastGrid)){
                    grid=lastGrid;
                    System.out.println("undone?");
                    gen--;
                }
                else{
                    System.out.println("Cannot undo");
                }
                if ((scannerOutput.equals(RENDER_COMMAND))){
                    validCommand=true;
                }
                if(!validCommand){
                    dontPrint=true;
                    System.out.println("You can't this!");
                }
            } 
            //Prints cells if it hasnt been told not to
            if(!dontPrint){
                renderArray(grid);
            }
        }
    }

    public void renderArray(boolean array[][]){
        for(int i=0;i<array.length;i++){
            String line="";
            for(int j=0;j<array[i].length;j++) {
                if(grid[i][j]){
                    line+="O "; 
                }else{
                    line+="  ";
                }
            }
            System.out.println(line);
        }
    }

    public void swap(String coordinates[]){
        try{ 
            if(grid[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]){
                grid[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=false;
                lastGrid[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=false;
            }else{
                grid[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=true;
                lastGrid[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=true;
            }
        }
        catch(Exception e){
            //e.printStackTrace();
            System.out.println("Thats outside of this world!");
        }
        renderArray(grid);
    }

    public void doGen(){

        int[][] test=new int[xSize][xSize];
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++) {
                test[i][j]=testCell(i,j);
            }
        }
        gen++;
        System.out.println("Generation "+gen);
        renderArray(grid);
        System.out.println("Pot for "+gen);
        for(int i=0;i<test.length;i++){//Shows how many alive tiles adjacent
            String line="";
            for(int j=0;j<test[i].length;j++) {                  
                if(grid[i][j]&&lastGrid[i][j]){
                    line+=test[i][j]+"A";//If it was alive last grid, add an 'A'
                }else if(lastGrid[i][j]){line+=test[i][j]+"D";}//If it just died, add a 'D'
                else if(grid[i][j]){line+=test[i][j]+"R";}//If it just became alive, add an 'R'
                else{line+=test[i][j]+" ";}//Else, add a space
            }
            System.out.println(line);
        }
        System.out.println("End of pot for "+gen);
        for(int i=0;i<grid.length;i++){//Converts last grid to this grid
            for(int j=0;j<grid[i].length;j++) {                  
                lastGrid[i][j]=grid[i][j];
            }
        }
    }

    public int testCell(int y, int x){
        int nextToCells=0;
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                nextToCells+=testAdCell(i,j,y,x);
            }
        }
        if(nextToCells==3){//If the cell has three neighbors alive, then the cell is alive
            grid[y][x]=true;
        }else if(nextToCells==2){//If the cell was alive and has two neighbors alive, then the cell is alive
            grid[y][x]=lastGrid[y][x];
        }else{//Else the cell is dead
            grid[y][x]=false;
        }
        //lastGrid[y][x]=grid[y][x];
        return nextToCells;//Returns alive adjecent cells for the array that shows alive adjecent cells
    }

    public int testAdCell(int yLook, int xLook, int yCurrent, int xCurrent){
        if((yLook+yCurrent)<0){yLook=(ySize-1);}
        if((yLook+yCurrent)>=(ySize)){yLook=0-(ySize-2);}
        if((xLook+xCurrent)<0){xLook=(xSize-1);}
        if((xLook+xCurrent)>=(xSize)){xLook=0-(xSize-2);}
        if (yLook==0&&xLook==0){//If it is testing the center cell, return 0
            return 0;}
        else if (lastGrid[yLook+yCurrent][xLook+xCurrent]){//If the tested cell in the last generation was alive, return 1
            return 1;
        } else {//Else, return 0
            return 0;
        }

    }
}
