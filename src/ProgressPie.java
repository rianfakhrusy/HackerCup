import java.util.*;
import java.io.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ProgressPie{
    static String filename = "input";
    static String inname = filename + ".txt";    
    static String outname = "output.txt"; 
    public static void main(String[] args){
        try{
            Scanner in = new Scanner(new BufferedReader(new FileReader(inname)));
            //Scanner in = new Scanner(System.in);
            BufferedWriter out = new BufferedWriter(new FileWriter(outname));
            int t = in.nextInt();
            in.nextLine();
            for (int cas = 1; cas <= t; cas++){
                String ans = "white"; 
                int p = in.nextInt();
                int x = in.nextInt();
                int y = in.nextInt();
                if ((Math.sqrt((x-50)*(x-50) + (y-50)*(y-50))<=50)&&(p!=0)){
                    if ((p>0)&&(x==50)&&(y==50))
                        ans = "black"; //if the angle is NaN & progress > 0 it is black
                    double f = 50;
                    double n = (double) p;
                    double g = (double) Math.toRadians(n/100f*360f);
                    double x0 = (double) (f*sin(g) + f);
                    double y0 = (double) (f*cos(g) + f);
                    //System.out.println(Math.toDegrees(g));
                    
                    double x1 = 50, y1 = 50, x2 = 50, y2 = 100;
                    double x3 = 50, y3 = 50, x4 = x, y4 = y;
                    double dx1 = x2-x1;
                    double dy1 = y2-y1;
                    double dx2 = x4-x3;
                    double dy2 = y4-y3;

                    double d = dx1*dx2 + dy1*dy2;   // dot product of the 2 vectors
                    double l2 = (dx1*dx1+dy1*dy1)*(dx2*dx2+dy2*dy2); // product of the squared lengths
                    
                    double angle = (double) Math.toDegrees(Math.acos(d/Math.sqrt(l2)));
                    if (x<50) //angle can be more than 180 degree
                        angle = 360 - angle;
                    //System.out.println(angle);
                    
                    //sudut lebih kecil dari sudut persentase berarti didalam hitam
                    if (Math.toDegrees(g)>=angle)
                        ans = "black";
                }
                //String s = in.nextLine();
                
                //System.out.print("Case #" + cas + ": " + ans + "\n");
                out.write("Case #" + cas + ": " + ans + "\n");
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}