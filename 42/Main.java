/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 23, 8/7/22
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
        "load",//Loads from save file
            "end",//Ends the simulation
            "go",//Goes one generation
            "grid",//Toggles grid 
            "advals",//Toggles the array of adjecent cells
            "swap",//Toggles swapmode
            "do",//Toggles domode
            "lots",//Does lots of generations
            "customise",//Allows customisation
            "help",//Lists all commands
            "save",//Saves this generation 
            "render"//renders the generation
    };
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
    int comeAlive[];//Number of cells scanned required to come to life
    int stayAlive[];//Number of cells scanned required to stay alive
    //Amount of generations done when the lots of generations command is played
    int lotsGenCount=100;
    //Save file names
    final String COMMANDS_FILE="commands.txt";
    final String SAVES_FILE="saves.txt";
    //The main command
    public Main()
    {
        Scanner scanner = new Scanner(System.in);//Set up the scanner
        System.out.println("Would you like start a 'basic' setup, an 'advanced' setup or '"+commands[0]+"'?");
        String setupScannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
        if(setupScannerOutput.equals("advanced")){//Advanced setup
            boolean inSetup=true;
            while(inSetup){
                boolean dontBreak=true;
                while (dontBreak){
                    try{//Y size
                        System.out.println("How wide do you want the world to be? NB- Large worlds can cause rendering issues");
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
                        System.out.println("How tall do you want the world to be? NB- Large worlds can cause rendering issues");
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
                        System.out.println("How many adjecent cells would you like the cells to scan? (default 1)");
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
                        System.out.println("How many numbers of cells do you want to be required for the cell to stay the same? (default 1)");
                        stayAlive=new int[Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", ""))];//Removes spaces and sets scanner input to lower case
                        for(int i=0;i<stayAlive.length;i++){
                            System.out.println("What number of cells required to stay the same? (default 2)");
                            stayAlive[i]=Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", ""));//Removes spaces and sets scanner input to lower case
                            if ((stayAlive[i]<1)){
                                i=stayAlive.length;
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
                    try{//Required amount of cell to become alive
                        System.out.println("How many numbers of cells do you want to be required for the cell to become alive? (default 1)");
                        comeAlive=new int[Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", ""))];//Removes spaces and sets scanner input to lower case
                        for(int i=0;i<comeAlive.length;i++){
                            System.out.println("What number of cells required to become alive? (default 3)");
                            comeAlive[i]=Integer.parseInt(scanner.nextLine().toLowerCase().replace(" ", ""));//Removes spaces and sets scanner input to lower case
                            if ((comeAlive[i]<1)){
                                i=comeAlive.length;
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
                thisGen = new boolean[ySize][xSize];
                lastGen = new boolean[ySize][xSize];
            }
            //Stuff for loading
        }else if(setupScannerOutput.equals(commands[0])){
            String saveFiles[]=load(SAVES_FILE).split(",");
            System.out.println("Save file"+plural(saveFiles.length)+"-");
            for(int i=0;i<saveFiles.length;i++){
                System.out.println(saveFiles[i]);
            }
            boolean dontBreak=true;
            while (dontBreak){
                try{//Check to make sure that it is an actual file
                    System.out.println("Which save file to you wish to load? (.txt not needed)");
                    setupScannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
                    String arrayOfThisGen[]=load(setupScannerOutput+".txt").split(",");
                    dontBreak=false;
                    //settings Order- comeAlive[.]~stayAlive[.]~ySize~xSize~renderGrid(1true,0false)~renderAdValues(1true,0false).
                    String settings[]=arrayOfThisGen[0].split("~");
                    //Requires string temp varibles to convert to int
                    //Loads comeAlive
                    String comeAliveString[]=settings[0].split("`");
                    comeAlive=new int[comeAliveString.length];
                    for (int i=0;i<comeAliveString.length;i++){
                        comeAlive[i]=Integer.parseInt(comeAliveString[i]);
                    }
                    //Loads stayAlive
                    String stayAliveString[]=settings[1].split("`");
                    stayAlive=new int[stayAliveString.length];
                    for (int i=0;i<stayAliveString.length;i++){
                        stayAlive[i]=Integer.parseInt(stayAliveString[i]);
                    }
                    //Loads life size
                    ySize=Integer.parseInt(settings[2]);
                    xSize=Integer.parseInt(settings[3]);
                    //Loads render settings
                    if(settings[4].equals("1")){
                        renderGrid=true;
                    }else{
                        renderGrid=false;
                    }
                    if(settings[5].equals("1")){
                        renderAdValues=true;
                    }else{
                        renderAdValues=false;
                    }
                    //Loads the data itself
                    thisGen = new boolean[ySize][xSize];
                    lastGen = new boolean[ySize][xSize];
                    for(int i=1;i<ySize;i++){
                        String yLine[]=arrayOfThisGen[i].split("~");
                        for(int j=0;j<xSize;j++){
                            if(yLine[j].equals("1")){//If this cell has a '1' then make it alive
                                thisGen[j][i-1]=true;
                                lastGen[j][i-1]=true;
                            }//Dont need else because defult is false
                        }
                    }
                }catch(Exception e){
                    dontBreak=true;
                    System.out.println("Save file"+plural(saveFiles.length)+"-");
                    for(int i=0;i<saveFiles.length;i++){
                        System.out.println(saveFiles[i]);
                    }
                }
            }
        }else{//In case the user decides that they don't want to type one of my commands, a basic setup will occur
            //Setup values
            int scanningRange=1;//The number of tiles each side that each tile scans
            comeAlive=new int[1];comeAlive[0]=3;//Number of cells scanned required to come to life
            stayAlive=new int[1];stayAlive[0]=2;//Number of cells scanned required to stay alive
            //Create arrays
            xSize=10;
            ySize=10;
            thisGen = new boolean[ySize][xSize];
            lastGen = new boolean[ySize][xSize];
        }
        //Keeps the game running until it is killed
        boolean running=true;
        while (running){
            //Reads input
            System.out.println("What do you want to do?");
            String scannerOutput=scanner.nextLine().toLowerCase().replace(" ", "");//Removes spaces and sets scanner input to lower case
            //If none of the commands work, then this should tell the user
            boolean validCommand=false;
            //The modes
            boolean swapMode = false;
            boolean doMode = false;
            boolean lotsMode = false;
            //If true, then it wont render at the end of this loop. Used for the generation loop so 
            boolean dontRender=false;
            //end command. It is first so user cannot softlock if user modifies preferences file
            if ((scannerOutput.equals(commands[1]))){
                running=false;
                validCommand=true;}
            //Do generations command
            if ((scannerOutput.equals(commands[2]))){//Does one generation
                validCommand=true;
                System.out.println("Starting one gen?");
                doGen();
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
                    }else if(cellSwapping[0].equals(commands[1])){//As a fail safe in case the user (me at the moment) cannot spell swao properly
                        running=false;
                        swapMode=false;
                    }else if(cellSwapping[0].equals(commands[2])){
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
                    }else if(cellDoing[0].equals(commands[1])){//As a fail safe in case the user (me at the moment) cannot spell swao properly
                        running=false;
                        doMode=false;
                    }else if(cellDoing[0].equals(commands[2])){
                        doMode=false;
                        System.out.println("Leaving do mode and playing 1 generation");
                        doGen();
                    }else{//Otherwise it will try to swap the corrdinates
                        try{
                            int temp = testCell(Integer.parseInt(cellDoing[0])-1,Integer.parseInt(cellDoing[1])-1);
                            System.out.println(temp);
                            renderBooleanArray(thisGen);//Render so user can see modification
                        }catch(Exception e){
                            System.out.println("You can't do that");
                        }
                    }
                }
            }
            //lots mode
            if ((scannerOutput.equals(commands[7]))){
                validCommand=true;
                dontRender=true;
                lotsMode=true;
                while(lotsMode){
                    System.out.println("How many generations do you want to process? (Type '"+commands[7]+"' to exit)");
                    String genCount=scanner.nextLine();
                    if(genCount.equals(commands[7])){//Exits swap mode
                        lotsMode=false;
                    }else if(genCount.equals(commands[1])){//As a fail safe in case the user (me at the moment) cannot spell swao properly
                        running=false;
                        lotsMode=false;
                    }else if(genCount.equals(commands[2])){
                        lotsMode=false;
                        System.out.println("Leaving lots mode and playing 1 generation");
                        doGen();
                    }else{//Otherwise it will try to swap the corrdinates
                        try{
                            for(int i=0; i<Integer.parseInt(genCount);i++){
                                   doGen();
                            }
                            System.out.println("Processed "+genCount+" generation"+plural(Integer.parseInt(genCount)));
                            //Renders this generation
                            System.out.println("Generation "+gen);
                            renderBooleanArray(thisGen);
                        }catch(Exception e){
                            System.out.println("Thats not a number");
                        }
                    }
                }
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
                    System.out.println((i+1)+"- "+commands[i]);
                }
                validCommand=true;
                dontRender=true;
            }
            //Save command
            if ((scannerOutput.equals(commands[10]))){//Saves current state of life
                boolean dontBreak=true;
                boolean newSave=false;
                String saveFileName="error.txt";//Forces the program to write to error.txt in case of errorsav
                String saveFiles[]=load(SAVES_FILE).split(",");
                System.out.println("Save file"+plural(saveFiles.length)+"-");
                for(int i=0;i<saveFiles.length;i++){
                    System.out.println(saveFiles[i]);
                }
                while (dontBreak){
                    newSave=false;
                    try{//Check to see if the user wishs to save to a file which it should not
                        System.out.println("Where would you like to save to? (Does not need .txt)");
                        saveFileName=(scanner.nextLine().toLowerCase().replace(" ", ""))+".txt";//Removes spaces and sets scanner input to lower case
                        if(saveFileName.equals(SAVES_FILE)||saveFileName.equals(COMMANDS_FILE)){//If the user decides that yes or no is not a good enough answer, they will have to redo the setup
                            System.out.println("Cannot save to this file, please save to another file");
                        }else{
                            String responce="yes";
                            for(int i=0;i<saveFiles.length;i++){
                                if(saveFileName.equals(saveFiles[i]+".txt")){//Asks user if they would like to override the selected file
                                    System.out.println("There is a file with that name already, do you wish to override?");
                                    responce=(scanner.nextLine().toLowerCase().replace(" ", ""));//Removes spaces and sets scanner input to lower case 
                                    newSave=true;
                                }
                            }
                            if(responce.equals("yes")){//Requires the responce to be yes, so if the user does a typo, the file wont be overriden
                                dontBreak=false;
                            }
                        }
                    }catch(Exception e){
                        System.out.println("Error, please try again");
                    }
                }
                String thisGenInStr="";
                //Get settings Order- comeAlive[.]~stayAlive[.]~ySize~xSize~renderGrid(1true,0false)~renderAdValues(1true,0false)

                for(int i=0;i<comeAlive.length;i++){//Gets come alive values
                    thisGenInStr+=comeAlive[i];
                    thisGenInStr+="`";
                }
                thisGenInStr+="~";
                for(int i=0;i<stayAlive.length;i++){//Gets stay alive values
                    thisGenInStr+=stayAlive[i];
                    thisGenInStr+="`";
                }
                //Gets size of life
                thisGenInStr+="~";
                thisGenInStr+=ySize;
                thisGenInStr+="~";
                thisGenInStr+=xSize;
                //Gets rendering settings
                thisGenInStr+="~";
                if(renderGrid){
                    thisGenInStr+="1";
                }else{
                    thisGenInStr+="0";
                }
                thisGenInStr+="~";
                if(renderAdValues){
                    thisGenInStr+="1";
                }else{
                    thisGenInStr+="0";
                }
                thisGenInStr+=",";
                for(int i=0;i<thisGen.length;i++){//Converts this generation into a single string
                    for(int j=0;j<thisGen[i].length;j++){
                        if(thisGen[i][j]){
                            thisGenInStr+="1~";
                        }else{
                            thisGenInStr+="0~";
                        }
                    }
                    thisGenInStr+=",";
                }
                save(saveFileName,thisGenInStr);
                if(!newSave){
                    save(SAVES_FILE,load(SAVES_FILE)+saveFileName.replace(".txt", "")+",");
                }
                validCommand=true;
                dontRender=true;
            }
            //Render command
            if ((scannerOutput.equals(commands[11]))){
                validCommand=true;
                //All it does is stops the replies with the invalid command, and the auto render renders the generation
            }
            //Prints cells if it hasnt been told not to
            if(!dontRender){
                //Renders this generation
                System.out.println("Generation "+gen);
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
        if((yLook+yCurrent)>(ySize-1)){yLook=(0-yCurrent);}
        if((xLook+xCurrent)<0){xLook=(xSize-1);}
        if((xLook+xCurrent)>(xSize-1)){xLook=(0-xCurrent);}
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
            return "";
        }
    }
}