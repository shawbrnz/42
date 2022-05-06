
/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 2, 5/5/22
 */
public class Main
{
    // instance variables - replace the example below with your own
    public boolean[][] grid = 
        {{true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false}};
    public boolean[][] lastGrid = 
        {{true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,false,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,true,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,true,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,true,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,true,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false},
            {true,false,true,false,false,true,false,true,false,false,true,false,true,false,false,true,false,true,false,false}};
    /**
     * Constructor for objects of class Main
     */
    public Main()
    {
        System.out.println("SOC");
        boolean running=true;
        int gen=0;
        while (running){
            System.out.println("SOL");
            lastGrid=grid;
            //Modifying cells
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[i].length;j++) {
                    int jChange=j+1;
                    int iChange=i+1;
                    int nextToCells=0;
                    if (jChange==grid[i].length){
                        jChange=0; 
                    }
                    if(iChange==grid.length){
                        iChange=0; 
                    }
                    if(lastGrid[iChange][jChange]){
                        nextToCells++; 
                    }
                    if(lastGrid[i][jChange]){
                        nextToCells++;
                    }
                    iChange=i-1;
                    if(iChange==-1){
                        iChange=grid.length-1; 
                    }
                    if(lastGrid[iChange][jChange]){
                        nextToCells++; 
                    }
                    if(lastGrid[iChange][j]){
                        nextToCells++; 
                    }
                    jChange=j-1;
                    if(jChange==-1){
                        jChange=grid.length-1; 
                    }
                    if(lastGrid[iChange][jChange]){
                        nextToCells++; 
                    }
                    if(lastGrid[i][jChange]){
                        nextToCells++; 
                    }
                    iChange=i+1;
                    if (iChange==grid[i].length){
                        iChange=0; 
                    }
                    if(lastGrid[iChange][jChange]){
                        nextToCells++; 
                    }
                    if(lastGrid[iChange][j]){
                        nextToCells++; 
                    }
                    if(nextToCells==3){
                        grid[i][j]=true;
                    }else if(nextToCells==2&&grid[i][j]){
                        grid[i][j]=true;
                    }else{
                        grid[i][j]=false;
                    }
                }
            }
            //Printing cells
            for(int i=0;i<grid.length;i++){
                String line="";
                for(int j=0;j<grid[i].length;j++) {
                    if(grid[i][j]){
                        line+="A "; 
                    }else{
                        line+="D ";
                    }
                }
                System.out.println(line);
            }
            /*      Code to display last generation
            System.out.println("Last Generation");
            for(int i=0;i<lastGrid.length;i++){
            String line="";
            for(int j=0;j<lastGrid[i].length;j++) {
            if(lastGrid[i][j]){
            line+="A "; 
            }else{
            line+="D ";
            }
            }
            System.out.println(line);
            }*/
            if(gen>1000){
                running=false;
            }
            gen++;
            boolean endCommands=false;
            while(!endCommands){
                endCommands=true;
            }
        }
        System.out.println("EOC");
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return y;
    }
}
