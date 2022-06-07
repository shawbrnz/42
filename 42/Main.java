/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 14, 8/6/22
 */
import java.io.File;//Allows file stuff
import java.io.FileWriter;//Allows the writing of files so saved.
import java.io.IOException;//Allows errors for files.
import java.util.Scanner;//Importing Scanner so keyboard inputs can be recorded.
public class Main
{
    // instance variables

    //Basic commands
    final String SWAP_COMMAND="swap";//Toggles swapmode
    final String DO_COMMAND="do";//Toggles domode
    final String END_COMMAND="end";//Ends the simulation
    final String ONE_GEN_COMMAND="go";//Goes one generation
    final String LOTS_GEN_COMMAND="";//Does lots of generations
    final String RENDER_COMMAND="render";//renders the generation
    final String GRID_COMMAND="grid";//Toggles grid 
    final String AD_VAL_COMMAND="advals";//Toggles the array of adjecent cells
    final String HELP_COMMAND="help";//Lists all commands
    final String CUSTOM_COMMAND="customise";//Allows customisation
    //Modifiable values
    int scanningRange=1;//The number of tiles each side that each tile scans
    int comeAlive[]={3};//Number of cells scanned required to come to life
    int stayAlive[]={2};//Number of cells scanned required to stay alive
    //What generation the game is in
    int gen=0;
    //X and Y size, in case the while loop breaks
    int ySize=0;
    int xSize=0;
    //Amount of generations done when the lots of generations command is played
    int lotsGenCount=100;
    //Whether to add the grid to the arrays
    boolean renderGrid = true;
    //Whether to also render the array of adjectent cells
    boolean renderAdValues = false;
    //Testty
    boolean thisGen[][];
    boolean lastGen[][];
    //The main command
    public Main()
    {
        Scanner scanner = new Scanner(System.in);//Set up the scanner
        System.out.println("Would you like start a 'basic' setup or an 'advanced' setup?");
        String setupScannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
        if(setupScannerOutput.equals("advanced")){//Advanced setup
            boolean inSetup=true;
            while(inSetup){
                boolean dontBreak=true;
                while (dontBreak){
                    try{//Y size
                        System.out.println("How wide do you want the world to be? NB- Large world size ");
                        ySize=(Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", "")));//Removes spaces and sets scanner input to lower case
                        if (!(ySize<1)){
                            dontBreak=false;
                        }else{
                            System.out.println("That isn't a number");
                        }
                    }catch(Exception e){
                        System.out.println("That isn't a number");
                    }
                }
                dontBreak=true;
                while (dontBreak){
                    try{//X size
                        System.out.println("How tall do you want the world to be");
                        xSize=(Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", "")));//Removes spaces and sets scanner input to lower case
                        if (!(xSize<1)){
                            dontBreak=false;
                        }else{
                            System.out.println("That isn't a number");
                        }
                        dontBreak=false;
                    }catch(Exception e){
                        System.out.println("That isn't a number");
                    }
                }
                dontBreak=true;
                while (dontBreak){
                    try{//Scanner size
                        System.out.println("How many adjecent cells would you like the cells to scan");
                        scanningRange=(Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", "")));//Removes spaces and sets scanner input to lower case
                        if (!(scanningRange<1)){
                            dontBreak=false;
                        }else{
                            System.out.println("That isn't a number");
                        }
                        dontBreak=false;
                    }catch(Exception e){
                        System.out.println("That isn't a number");
                    }
                }
                dontBreak=true;
                while (dontBreak){
                    try{//Required amount of cell to stay alive
                        System.out.println("How many numbers of cells do you want to be required for the cell to stay the same?");
                        int stayAlive[]=new int[Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", ""))];//Removes spaces and sets scanner input to lower case
                        for(int i=0;i<stayAlive.length;i++){
                            System.out.println("What number of cells required to stay the same?");
                            stayAlive[i]=Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", ""));//Removes spaces and sets scanner input to lower case
                        }
                        dontBreak=false;
                    }catch(Exception e){
                        System.out.println("That isn't a number");
                    }
                }
                dontBreak=true;
                while (dontBreak){
                    try{//Required amount of cell to become alive
                        System.out.println("How many numbers of cells do you want to be required for the cell to become?");
                        setupScannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
                        for(int i=0;i<comeAlive.length;i++){
                            System.out.println("What number of cells required to become alive?");
                            stayAlive[i]=Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", ""));//Removes spaces and sets scanner input to lower case
                        }
                        dontBreak=false;
                    }catch(Exception e){
                        System.out.println("That isn't a number");
                    }
                }
                dontBreak=true;
                while (dontBreak){
                    try{//Check to see if the settings are okay
                        System.out.println("Are the settings okay? 'Yes' or 'No'");
                        setupScannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
                        if(setupScannerOutput.equals("yes")){//If the user decides that yes or no is not a good enough answer, they will have to redo the setup
                            inSetup=false;
                        }
                        dontBreak=false;
                    }catch(Exception e){
                        System.out.println("Error");
                    }
                }
            }
        }else{//In case the user decides that they don't want to type one of my commands, a basic setup will occur
            //Setup values
            int scanningRange=1;//The number of tiles each side that each tile scans
            int comeAlive[]={3};//Number of cells scanned required to come to life
            int stayAlive[]={2};//Number of cells scanned required to stay alive
            //Create arrays
            xSize=30;
            ySize=40;
        }
        thisGen = new boolean[ySize][xSize];
        lastGen = new boolean[ySize][xSize];
        //Keeps the game running until it is killed
        boolean running=true;
        while (running){
            //Reads input
            System.out.println("What do you want to do?");
            String scannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
            //If none of the commands work, then this should tell the user
            boolean validCommand=false;
            //Swap mode
            boolean swapMode = false;
            //do mode
            boolean doMode = false;
            //If true, then it wont render at the end of this loop. Used for the generation loop so 
            boolean dontRender=false;
            //Do generations command
            if ((scannerOutput.equals(ONE_GEN_COMMAND))){//Does one generation
                dontRender=true;
                validCommand=true;
                System.out.println("Starting one gen?");
                doGen();
            }
            if ((scannerOutput.equals(LOTS_GEN_COMMAND))){//Does more than one generation
                dontRender=true;
                validCommand=true;
                System.out.println("Starting "+lotsGenCount+" gen?");
                for(int i=0; i<lotsGenCount;i++){
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
            //do mode
            if ((scannerOutput.equals(DO_COMMAND))){
                validCommand=true;
                doMode=true;
                dontRender=true;
                while(doMode){
                    System.out.println("Which cell do you wish to do? (Type '"+DO_COMMAND+"' to exit)");
                    String cellDoing[]=scanner.nextLine().split(" ");
                    if(cellDoing[0].equals(DO_COMMAND)){//Exits swap mode
                        doMode=false;
                    }else if(cellDoing[0].equals(END_COMMAND)){//As a fail safe in case the user (me at the moment) cannot spell swao properly
                        running=false;
                        doMode=false;
                    }else if(cellDoing[0].equals(ONE_GEN_COMMAND)){
                        swapMode=false;
                        System.out.println("Leaving do mode and playing 1 generation");
                        doGen();
                    }else{//Otherwise it will try to swap the corrdinates
                        try{
                            testCell(Integer.parseInt(cellDoing[0]),Integer.parseInt(cellDoing[1]));
                            renderBooleanArray(thisGen);//Render so user can see modification
                        }catch(Exception e){
                            System.out.println("You can't do that");
                        }
                    }
                }
            }
            //Render command
            if ((scannerOutput.equals(RENDER_COMMAND))){
                validCommand=true;
                //All it does is stops the replies with the invalid command, and the auto render renders the generation
            }
            //Customise commands
            if ((scannerOutput.equals(CUSTOM_COMMAND))){//Asks the user what it would like to change and does nothing since my settings are best
                System.out.println("What do you want to change?");
                String customOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
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
            if(thisGen[Integer.parseInt(coordinates[0])-1][Integer.parseInt(coordinates[1])-1]){
                thisGen[Integer.parseInt(coordinates[0])-1][Integer.parseInt(coordinates[1])-1]=false;
                lastGen[Integer.parseInt(coordinates[0])-1][Integer.parseInt(coordinates[1])-1]=false;
            }else{
                thisGen[Integer.parseInt(coordinates[0])-1][Integer.parseInt(coordinates[1])-1]=true;
                lastGen[Integer.parseInt(coordinates[0])-1][Integer.parseInt(coordinates[1])-1]=true;
            }
        }
        catch(Exception e){
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
        for(int i=-scanningRange;i<=scanningRange;i++){
            for(int j=-scanningRange;j<=scanningRange;j++){
                nextToCells+=testAdCell(i,j,y,x);
            }
        }
        //Updates the cell 
        boolean autoKill= true;//Will kill the cell if non of the otehr condtions are met
        for(int i=0; i<stayAlive.length;i++){
            if(nextToCells==stayAlive[i]){//If the cell was alive and has two neighbors alive, then the cell is alive
                thisGen[y][x]=lastGen[y][x];
                autoKill=false;
            }
        }  
        for(int i=0; i<comeAlive.length;i++){
            if(nextToCells==comeAlive[i]){//If the cell has three neighbors alive, then the cell is alive
                thisGen[y][x]=true;
                autoKill=false;
            }
        }
        if(autoKill){//Else the cell is dead
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