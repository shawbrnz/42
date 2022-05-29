/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 10, 29/5/22
 */
import java.io.File;//Allows file stuff
import java.io.FileWriter;//Allows the writing of files so saved.
import java.io.IOException;//Allows errors for files.
import java.util.Scanner;//Importing Scanner so keyboard inputs can be recorded.
public class Main
{
    // instance variables - replace the example below with your own
    final int xSize=30;
    final int ySize=40;
    public boolean[][] grid = new boolean[ySize][xSize];
    public boolean[][] lastGrid = new boolean[ySize][xSize];
    final String SWAP_COMMAND="swap";
    final String END_COMMAND="end";
    final String ONE_GEN_COMMAND="go";
    final String ONE_K_GEN_COMMAND="";
    final String RENDER_COMMAND="render";
    int gen=0;
    //The main command
    public Main()
    {
        //Keeps the game running until it is killed
        boolean running=true;
        while (running){
            //Reads input
            Scanner scanner = new Scanner(System.in);//Set up the scanner
            System.out.println("What do you want to do?");
            String scannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
            //If none of the commands work, then this should tell the user
            boolean validCommand=false;
            //Swap mode
            boolean swapMode = false;
            //If true, then it wont render at the end of this loop. Used for the generation loop so 
            boolean dontPrint=false;
            //Do generations command
            if ((scannerOutput.equals(ONE_GEN_COMMAND))){//Does one generation
                dontPrint=true;
                validCommand=true;
                System.out.println("Starting one gen?");
                //lastGrid=grid;
                doGen();}
            if ((scannerOutput.equals(ONE_K_GEN_COMMAND))){//Does more than one generation
                dontPrint=true;
                validCommand=true;
                System.out.println("Starting one k gen?");
                for(int i=0; i<1000;i++){
                    //lastGrid=grid;
                    doGen();
                }
            }
            //end command
            if ((scannerOutput.equals(END_COMMAND))){
                running=false;
                validCommand=true;}
            //swap mode
            if ((scannerOutput.equals(SWAP_COMMAND))){
                validCommand=true;
                swapMode=true;
                dontPrint=true;
                while(swapMode){
                    System.out.println("What do you wish to swap? (Type '"+SWAP_COMMAND+"' to exit)");
                    String cellSwapping[]=scanner.nextLine().split(" ");
                    if(cellSwapping[0].equals(SWAP_COMMAND)){//Exits swap mode
                        swapMode=false;
                    }else if(cellSwapping[0].equals(END_COMMAND)){//As a fail safe in case the user (me at the moment) cannot spell swao properly
                        running=false;
                        swapMode=false;
                    }else{//Otherwise it will try to swap the corrdinates
                        swap(cellSwapping);
                    }
                }
            }
            //Render command
            if ((scannerOutput.equals(RENDER_COMMAND))){
                validCommand=true;
            }
            //Prints cells if it hasnt been told not to
            if(!dontPrint){
                renderArray(grid);
            }
            //Invalid command
            if(!validCommand){
                System.out.println("You can't this!");
            }
        }
        //If the loop stops, then displays this message
        System.out.println("After "+gen+" generation"+plural(gen)+", the Game of Life ends");
    }
    //Tests to see if number should be plural
    public String plural(int number){
        if(number==1){return "";}
        return "s";
    }
    //Renders a 2d boolean array
    public void renderArray(boolean array[][]){
        for(int i=0;i<array.length;i++){
            String line="";
            for(int j=0;j<array[i].length;j++) {
                if(array[i][j]){
                    line+="O "; //Shows alive cell as '0'
                }else{
                    line+="  ";//Shows dead cell as ' '
                }
            }
            System.out.println(line);
        }
    }
    //Renders a 2d Array based on numbers from each cell
    public void valueArrayRender(int value[][]){
        for(int i=0;i<value.length;i++){
            String line="";
            for(int j=0;j<value[i].length;j++) {                  
                line+=value[i][j]+" ";
            }
            System.out.println(line);
        }
    }
    //Processes swap commands
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
        renderArray(grid);//Render so user can see modification
    }
    //Do a generation command
    public void doGen(){
        for(int i=0;i<grid.length;i++){//Converts last grid to this grid
            for(int j=0;j<grid[i].length;j++) {                  
                lastGrid[i][j]=grid[i][j];
            }
        }
        //Tests to see how many adjecent cells are alive
        int[][] adCellsAlive=new int[ySize][xSize];
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[i].length;j++) {
                try{
                    adCellsAlive[i][j]=testCell(i,j);
                }catch(Exception e){
                    System.out.println("Error 123");//In case the universe explodes, then I will be told about it
                }
            }
        }
        gen++;
        //Renders this generation
        System.out.println("Generation "+gen);
        renderArray(grid);
        //Renders ajacent cells alive last generation
        System.out.println("Ad cells for "+gen);
        valueArrayRender(adCellsAlive);
    }
    //Tests a cell for how many adjcent cells are alive
    public int testCell(int y, int x){
        //Tests the cell
        int nextToCells=0;
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                nextToCells+=testAdCell(i,j,y,x);
            }
        }
        //Updates the cell 
        if(nextToCells==3){//If the cell has three neighbors alive, then the cell is alive
            grid[y][x]=true;
        }else if(nextToCells==2){//If the cell was alive and has two neighbors alive, then the cell is alive
            grid[y][x]=lastGrid[y][x];
        }else{//Else the cell is dead
            grid[y][x]=false;
        }
        return nextToCells;//Returns alive adjecent cells for the array that shows alive adjecent cells
    }
    //Tests the selected adjacent cell
    public int testAdCell(int yLook, int xLook, int yCurrent, int xCurrent){
        //If it is currently tring to look a cell that is out of the world
        if((yLook+yCurrent)<0){yLook=(ySize-1);}
        if((yLook+yCurrent)>(ySize-1)){yLook=0;}
        if((xLook+xCurrent)<0){xLook=(xSize-1);}
        if((xLook+xCurrent)>(xSize-1)){xLook=0;}
        //Returns either 1 or 0 based on whether the selcted cell is alive or dead
        if (yLook==0&&xLook==0){//If it is testing the center cell, return 0
            return 0;}
        else if (lastGrid[yLook+yCurrent][xLook+xCurrent]){//If the tested cell in the last generation was alive, return 1
            return 1;
        } else {//Else, return 0
            return 0;
        }

    }
}
