import java.util.*;
import java.io.*;

public class SlyStacking{
    //static String inname = "lazy_loading.txt";  
    static String inname = "input.txt"; 
    static String outname = "output.txt"; 
    public static void main(String[] args){
        try{
            Scanner in = new Scanner(new BufferedReader(new FileReader(inname)));
            //Scanner in = new Scanner(System.in);
            BufferedWriter out = new BufferedWriter(new FileWriter(outname));
            int t = in.nextInt();
            in.nextLine();
            for (int cas = 1; cas <= t; cas++){
                int ans = 0;
                int poi = in.nextInt();
                
                ArrayList<Integer> L = new ArrayList<>();
                for (int i =0;i<poi;i++){
                    L.add(in.nextInt());
                }
                Collections.sort(L); //sort
                while (!L.isEmpty()){ //gali sampai kosong
                    int stak = L.remove(L.size()-1); //buang yang paling gede
                    int i=0;
                    if (L.size()>=50/(stak+0.0000001f)-1) //dibulatkan keatas
                        for (i = 0; i< 50/(stak+0.0000001f)-1;i++)
                            if (!L.isEmpty()){ //kalau gak kosong baru bisa di remove
                                L.remove(0);
                            }
                    if (stak*(i+1)>=50) //hanya yang lebih dari 50 yang boleh diantar
                        ans++;
                    //System.out.println(stak*(i+1));
                }
                
                System.out.print("Case #" + cas + ": " + ans + "\n");
                //out.write("Case #" + cas + ": " + ans + "\n");
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}