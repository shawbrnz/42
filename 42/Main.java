/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 11, 30/5/22
 */
import java.io.File;//Allows file stuff
import java.io.FileWriter;//Allows the writing of files so saved.
import java.io.IOException;//Allows errors for files.
import java.util.Scanner;//Importing Scanner so keyboard inputs can be recorded.
public class Main
{
    // instance variables
    //Create arrays
    final int xSize=30;
    final int ySize=40;
    public boolean[][] thisGen = new boolean[ySize][xSize];
    public boolean[][] lastGen = new boolean[ySize][xSize];
    //Basic commands
    final String SWAP_COMMAND="swap";
    final String END_COMMAND="end";
    final String ONE_GEN_COMMAND="go";
    final String LOTS_GEN_COMMAND="";
    final String RENDER_COMMAND="render";
    final String GRID_COMMAND="grid";
    final String AD_VAL_COMMAND="advals";
    //What generation the game is in
    int gen=0;
    //Amount of generations done when the lots of generations command is played
    int lotsGenCount=100;
    //Whether to add the grid to the arrays
    boolean renderGrid = true;
    //Whether to also render the array of adjectent cells
    boolean renderAdValues = true;
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
            boolean dontRender=false;
            //Do generations command
            if ((scannerOutput.equals(ONE_GEN_COMMAND))){//Does one generation
                dontRender=true;
                validCommand=true;
                System.out.println("Starting one gen?");
                //lastGrid=grid;
                doGen();}
            if ((scannerOutput.equals(LOTS_GEN_COMMAND))){//Does more than one generation
                dontRender=true;
                validCommand=true;
                System.out.println("Starting "+lotsGenCount+" gen?");
                for(int i=0; i<lotsGenCount;i++){
                    //lastGrid=grid;
                    doGen();
                }
            }
            //end command
            if ((scannerOutput.equals(END_COMMAND))){
                running=false;
                validCommand=true;}
            //toggles grid
            if ((scannerOutput.equals(GRID_COMMAND))){
                renderGrid=swapBoolean(renderGrid);
            validCommand=true;}
            //toggles adjecent values (debug tool)
            if ((scannerOutput.equals(AD_VAL_COMMAND))){
                renderAdValues=swapBoolean(renderAdValues);
            validCommand=true;}
            //swap mode
            if ((scannerOutput.equals(SWAP_COMMAND))){
                validCommand=true;
                swapMode=true;
                dontRender=true;
                while(swapMode){
                    System.out.println("What do you wish to swap? (Type '"+SWAP_COMMAND+"' to exit)");
                    String cellSwapping[]=scanner.nextLine().split(" ");
                    if(cellSwapping[0].equals(SWAP_COMMAND)){//Exits swap mode
                        swapMode=false;
                    }else if(cellSwapping[0].equals(END_COMMAND)){//As a fail safe in case the user (me at the moment) cannot spell swao properly
                        running=false;
                        swapMode=false;
                    }else if(cellSwapping[0].equals(ONE_GEN_COMMAND)){
                        swapMode=false;
                        System.out.println("Leaving swap mode and playing 1 generation");
                        doGen();
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
            if(!dontRender){
                renderBooleanArray(thisGen);
            }
            //Invalid command
            if(!validCommand){
                System.out.println("You can't this!");
            }
        }
        //If the loop stops, then displays this message
        System.out.println("After "+gen+" generation"+plural(gen)+", the Game of Life ends");
    }
    
    //Functions that make my life easier
    
    //Tests to see if number should be plural
    public String plural(int number){
        if(number==1){return "";}
        return "s";
    }
    //Swaps the values of booleans
    public boolean swapBoolean(boolean testedBoolean){
        if(testedBoolean){
            return false;
        }
        return true;
    }
    
    
    //Processes swap commands
    public void swap(String coordinates[]){
        try{ 
            if(thisGen[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]){
                thisGen[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=false;
                lastGen[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=false;
            }else{
                thisGen[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=true;
                lastGen[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])]=true;
            }
        }
        catch(Exception e){
            //e.printStackTrace();
            System.out.println("Thats outside of this world!");
        }
        renderBooleanArray(thisGen);//Render so user can see modification
    }

    //Playing generation commands
    //Do a generation command
    public void doGen(){
        for(int i=0;i<thisGen.length;i++){//Converts last grid to this grid
            for(int j=0;j<thisGen[i].length;j++) {                  
                lastGen[i][j]=thisGen[i][j];
            }
        }
        //Tests to see how many adjecent cells are alive
        int[][] adCellsAlive=new int[ySize][xSize];
        for(int i=0;i<thisGen.length;i++){
            for(int j=0;j<thisGen[i].length;j++) {
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
        renderBooleanArray(thisGen);
        //Renders ajacent cells alive last generation
        System.out.println("Ad cells for "+gen);
        renderIntArray(adCellsAlive);
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
            thisGen[y][x]=true;
        }else if(nextToCells==2){//If the cell was alive and has two neighbors alive, then the cell is alive
            thisGen[y][x]=lastGen[y][x];
        }else{//Else the cell is dead
            thisGen[y][x]=false;
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
        else if (lastGen[yLook+yCurrent][xLook+xCurrent]){//If the tested cell in the last generation was alive, return 1
            return 1;
        } else {//Else, return 0
            return 0;
        }

    }

    
    //Render Arrays

    
    //Boolean arrays

    //Renders a 2d boolean array with a grid
    public void gridedBooleanArray(String trueValue, String falseValue, boolean array[][]){
        //The little gap
        String line=" ";
        for(int i=1;i<((array.length+"").length());i++){
            line+=" ";
        }
        //The top bar
        for(int i=1;i<array[0].length+1;i++){
            line+="|";
            for(int j=0;j<((array.length+"").length())-((i+"").length());j++){
                line+=" ";
            }
            line+=i;
        }
        line+="|";
        System.out.println(line);
        //The grid
        for(int i=1;i<array.length+1;i++){
            //The empty lines`
            line="";
            for(int j=0;j<((array.length+"").length());j++){
                line+="-";
            }
            line+="+";
            for(int j=0;j<array[0].length;j++){
                for(int k=0;k<((array[0].length+"").length());k++){
                    line+="-";
                }
                line+="+";
            }
            System.out.println(line);
            //The y axis one
            line=i+"";
            for(int j=0;j<((array.length+"").length())-((i+"").length());j++){
                line+=" ";
            }
            line+="|";
            //The grid itself
            for(int j=1;j<array[0].length+1;j++) {
                for(int k=0;k<((array.length+"").length())-1;k++){
                    line+=" ";
                }
                if(array[i-1][j-1]){
                    line+=trueValue+"|"; //Shows alive cell as '0'
                }else{
                    line+=falseValue+"|";//Shows dead cell as ' '
                }
            }
            System.out.println(line);
        }
    }
    //Renders a 2d boolean array without a grid
    public void gridlessBooleanArray(String trueValue, String falseValue, boolean array[][]){
        for(int i=0;i<array.length;i++){
            String line="";
            for(int j=0;j<array[i].length;j++) {
                if(array[i][j]){
                    line+=trueValue+" "; //Shows alive cell as '0'
                }else{
                    line+=falseValue+" ";//Shows dead cell as ' '
                }
            }
            System.out.println(line);
        }
    }

    //Value arrays

    //Renders a 2d Array based on numbers from each cell without a grid
    public void gridlessIntArray(int array[][]){
        for(int i=0;i<array.length;i++){
            String line="";
            for(int j=0;j<array[i].length;j++) {                  
                line+=array[i][j]+" ";
            }
            System.out.println(line);
        }
    }
    //Renders a 2d Array based on numbers from each cell without a grid
    public void gridedIntArray(int array[][]){
        //The little gap
        String line=" ";
        for(int i=1;i<((array.length+"").length());i++){
            line+=" ";
        }
        //The top bar
        for(int i=1;i<array[0].length+1;i++){
            line+="|";
            for(int j=0;j<((array.length+"").length())-((i+"").length());j++){
                line+=" ";
            }
            line+=i;
        }
        line+="|";
        System.out.println(line);
        //The grid
        for(int i=1;i<array.length+1;i++){
            //The empty lines
            line="-";
            for(int j=0;j<((array.length+"").length())-1;j++){
                line+="-";
            }
            line+="+";
            for(int j=0;j<array[0].length;j++){
                for(int k=0;k<((array[0].length+"").length());k++){
                    line+="-";
                }
                line+="+";
            }
            System.out.println(line);
            //The y axis one
            line=i+"";
            for(int j=0;j<((array.length+"").length())-((i+"").length());j++){
                line+=" ";
            }
            line+="|";
            //The grid itself
            for(int j=1;j<array[0].length+1;j++) {
                for(int k=0;k<((array.length+"").length())-1;k++){
                    line+=" ";
                }
                line+=array[i-1][j-1]+"|";
            }
            System.out.println(line);
        }
    }

    //Other array stuff

    //Decides whether to add a grid to the array
    public void renderBooleanArray(boolean array[][]){
        if(renderGrid){
            gridedBooleanArray("O"," ",array);
        }else{
            gridlessBooleanArray("O"," ",array);
        }
    }

    public void renderIntArray(int array[][]){
        if(renderGrid){
            gridedIntArray(array);
        }else{
            gridlessIntArray(array);
        }
    }
}

