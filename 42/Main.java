
/**
 * Primary class
 *
 * @Brendan Shaw
 * @version 1, 4/5/22
 */
public class Main
{
    // instance variables - replace the example below with your own
    public boolean[][] grid = 
    {{true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
    {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
    {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
    {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
    {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
    {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
    {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
    {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false},
    {true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false}};
    /**
     * Constructor for objects of class Main
     */
    public Main()
    {
        // initialise instance variables
        System.out.println("_____________");
        System.out.println(grid.length);
        System.out.println(grid[1].length);
        System.out.println(grid[0].length);
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
