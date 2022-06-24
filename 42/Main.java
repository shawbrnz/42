/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 17, 24/6/22
 */
import java.io.File;//Allows file stuff
import java.io.FileWriter;//Allows the writing of files so saved.
import java.io.IOException;//Allows errors for files.
import java.util.Scanner;//Importing Scanner so keyboard inputs can be recorded.
public class Main
{
    // instance variables

    //Commands. This is in an array for easy modification
    String commands[]={
            "end",//Ends the simulation
            "go",//Goes one generation
            "lots",//Does lots of generations
            "grid",//Toggles grid 
            "advals",//Toggles the array of adjecent cells
            "swap",//Toggles swapmode
            "do",//Toggles domode
            "render",//renders the generation
            "customise",//Allows customisation
            "help",//Lists all commands
            "save",//Saves this generation
            "load"};//Loads from save file
    //What generation the game is in
    int gen=0;
    //X and Y size, in case the while loop breaks
    int ySize=0;
    int xSize=0;
    //Whether to add the grid to the arrays
    boolean renderGrid = true;
    //Whether to also render the array of adjectent cells
    boolean renderAdValues = false;
    //Creates array
    boolean thisGen[][];
    boolean lastGen[][];
    //Modifiable varibles
    int scanningRange=1;//The number of tiles each side that each tile scans
    int comeAlive[]={3};//Number of cells scanned required to come to life
    int stayAlive[]={2};//Number of cells scanned required to stay alive
    int lotsGenCount=100;//Amount of generations done when the lots of generations command is played
    //Save file names
    final String COMMANDS_FILE="commands.txt";
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
                            System.out.println("That is too small number");
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
                            System.out.println("That is too small number");
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
                            System.out.println("That is too small number");
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
                            if (!(stayAlive[i]<1)){
                                dontBreak=false;
                                i=comeAlive.length;
                            }else{
                                System.out.println("That is too small number");
                            }}
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
                            comeAlive[i]=Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", ""));//Removes spaces and sets scanner input to lower case
                            if (!(comeAlive[i]<1)){
                                dontBreak=false;
                                i=comeAlive.length;
                            }else{
                                System.out.println("That is too small number");
                            }
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
            //end command. It is first so user cannot softlock if user modifies preferences file
            if ((scannerOutput.equals(commands[0]))){
                running=false;
                validCommand=true;}
            //Do generations command
            if ((scannerOutput.equals(commands[1]))){//Does one generation
                dontRender=true;
                validCommand=true;
                System.out.println("Starting one gen?");
                doGen();
            }
            if ((scannerOutput.equals(commands[2]))){//Does more than one generation
                dontRender=true;
                validCommand=true;
                System.out.println("Starting "+lotsGenCount+" gen?");
                for(int i=0; i<lotsGenCount;i++){
                    doGen();
                }
            }
            //toggles grid
            if ((scannerOutput.equals(commands[3]))){
                renderGrid=swapBoolean(renderGrid);
                validCommand=true;}
            //toggles adjecent values (debug tool)
            if ((scannerOutput.equals(commands[4]))){
                renderAdValues=swapBoolean(renderAdValues);
                validCommand=true;}
            //swap mode
            if ((scannerOutput.equals(commands[5]))){
                validCommand=true;
                swapMode=true;
                dontRender=true;
                while(swapMode){
                    System.out.println("What do you wish to swap? (Type '"+commands[5]+"' to exit)");
                    String cellSwapping[]=scanner.nextLine().split(" ");
                    if(cellSwapping[0].equals(commands[5])){//Exits swap mode
                        swapMode=false;
                    }else if(cellSwapping[0].equals(commands[0])){//As a fail safe in case the user (me at the moment) cannot spell swao properly
                        running=false;
                        swapMode=false;
                    }else if(cellSwapping[0].equals(commands[1])){
                        swapMode=false;
                        System.out.println("Leaving swap mode and playing 1 generation");
                        doGen();
                    }else{//Otherwise it will try to swap the corrdinates
                        swap(cellSwapping);
                    }
                }
            }
            //do mode
            if ((scannerOutput.equals(commands[6]))){
                validCommand=true;
                doMode=true;
                dontRender=true;
                while(doMode){
                    System.out.println("Which cell do you wish to do? (Type '"+commands[6]+"' to exit)");
                    String cellDoing[]=scanner.nextLine().split(" ");
                    if(cellDoing[0].equals(commands[6])){//Exits swap mode
                        doMode=false;
                    }else if(cellDoing[0].equals(commands[0])){//As a fail safe in case the user (me at the moment) cannot spell swao properly
                        running=false;
                        doMode=false;
                    }else if(cellDoing[0].equals(commands[1])){
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
            if ((scannerOutput.equals(commands[7]))){
                validCommand=true;
                //All it does is stops the replies with the invalid command, and the auto render renders the generation
            }
            //Customise commands
            if ((scannerOutput.equals(commands[8]))){//Asks the user what it would like to change
                System.out.println("What do you want to change? (Use current command name)");
                String modifyingCommandName=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
                System.out.println("What do you want the new command to be? (Cannot be the name of an existing command)");
                String newCommandName=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
                boolean existingName=false;
                for(int i=0;i<commands.length;i++){
                    if(commands[i]==modifyingCommandName){
                        for(int j=0;j<commands.length;j++){
                            if(newCommandName.equals(commands[j])){
                                existingName=true;
                            }
                        }
                        if(!existingName){
                            commands[i]=newCommandName;
                            String stringOfCommands="";
                            for(int j=0;j<commands.length;j++){
                                stringOfCommands+=commands[j]+",";
                            }
                            save(COMMANDS_FILE, stringOfCommands);
                        }
                        i=commands.length;
                    }
                }
                validCommand=true;
                dontRender=true;
            }
            //Help command
            if ((scannerOutput.equals(commands[9]))){//Lists all commands
                for(int i=0;i<commands.length;i++){
                    System.out.println(commands[i]);
                }
                validCommand=true;
                dontRender=true;
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
        if(renderAdValues){
            System.out.println("Ad cells for "+gen);
            renderIntArray(adCellsAlive);
        }
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

    //Files stuff

    //Saves string to file
    public void save(String fileName, String stuffToSave){
        try{
            FileWriter writer=new FileWriter(fileName);
            writer.write(stuffToSave);
            writer.flush();
            writer.close();
        }catch(IOException e){
            System.out.println(fileName+" does not exist. To save, create a file named '"+fileName+"'");
        }
    }
    //Loads string from file
    public String load(String fileName){
        try{
            Scanner readFile=new Scanner(new File(fileName));
            return readFile.nextLine();
        }catch(IOException e){
            System.out.println(fileName+" does not exist. To load this file, create a file named '"+fileName+"'");
        }
        return "";
    }
}