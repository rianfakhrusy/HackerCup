import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ZombieFight{
    //static String inname = "fighting_the_zombie.txt";  
    static String inname = "input.txt"; 
    static String outname = "output.txt"; 
    public static int numPossibilities(int numDice, int sum, int mata) {
        if (numDice == sum)
            return 1;
        else if (numDice == 0 || sum < numDice)
            return 0;
        else
        {
            if (hmap.get(new Key(numDice,sum,mata))!=null)
                return hmap.get(new Key(numDice,sum,mata));
            /*
            int total = 0;
            for (int i=0;i<mata;i++){
                total += numPossibilities(numDice - 1, sum - 1);
            }*/
            return //total;
            
                   numPossibilities(numDice, sum - 1, mata) +
                   numPossibilities(numDice - 1, sum - 1, mata) -
                   numPossibilities(numDice - 1, sum - mata - 1, mata);
        }
    }
    public static class Key {
        int key1;
        int key2;
        int key3;
        public Key(int key1, int key2, int key3) {
            this.key1 = key1;
            this.key2 = key2;
            this.key3 = key3;
        }
    }
    public static HashMap<Key, Integer> hmap = new HashMap<>();
    public static void main(String[] args){
        try{
            Scanner in = new Scanner(new BufferedReader(new FileReader(inname)));
            //Scanner in = new Scanner(System.in);
            BufferedWriter out = new BufferedWriter(new FileWriter(outname));
            int t = in.nextInt();
            in.nextLine();
            
            for (int cas = 1; cas <= t; cas++){
                double ans = 0f;
                int bat = in.nextInt();
                int nspell = in.nextInt();
                for (int j=0;j<nspell;j++){
                    int batas = bat;
                    String temp = in.next();
                    //System.out.println(temp);
                    String[] parts = temp.split("d");
                    //System.out.println(parts[0]);
                    //System.out.println(parts[1]);
                    int rolltime = Integer.parseInt(parts[0]);
                    int matadadu = 0;
                    int add = 0;
                    if ((temp.indexOf('-')==-1)&&(temp.indexOf('+')==-1))
                        matadadu = Integer.parseInt(parts[1]);
                    else if (temp.indexOf('-')!=-1){
                        String[] part = parts[1].split("-");
                        matadadu = Integer.parseInt(part[0]);
                        add = Integer.parseInt(part[1]);
                    } else if (temp.indexOf('+')!=-1){
                        String[] part = parts[1].split("\\+");
                        matadadu = Integer.parseInt(part[0]);
                        add = Integer.parseInt(part[1]) * -1;
                    }
                    //System.out.println(rolltime + " " + matadadu + " " + add);
                    
                    batas = batas + add;
                    System.out.println(rolltime + " " + matadadu + " " + batas);
                    //minimum value & maxvalue
                    int sum = 0;
                    double value = 0;
                    int minval = rolltime;
                    int maxval = rolltime*matadadu;
                    int minbat = batas - minval;
                    int maxbat = maxval - batas;
                    if (minbat < maxbat){
                        for (int i = minval; i <= batas-1; i++)
                            sum  += numPossibilities(rolltime,i,matadadu);
                        value = 1-((double)sum/Math.pow(matadadu, rolltime));
                    } else {
                        for (int i = batas; i <= maxval+1; i++){
                            int val = 0;/*
                            if (hmap.get(new Key(rolltime,i,matadadu))==null){
                                val = numPossibilities(rolltime,i,matadadu);
                                hmap.put(new Key(rolltime,i,matadadu), val);
                                hmap.put(new Key(rolltime,maxval+minval-i-1,matadadu), val);
                            } else
                                val = hmap.get(new Key(rolltime,i,matadadu));*/
                            System.out.println(sum);
                            if (i<=(maxval+minval)/2){
                                val = numPossibilities(rolltime,i,matadadu);
                                hmap.put(new Key(rolltime,i,matadadu), val);
                            } else {
                                val = numPossibilities(rolltime,maxval+minval-i,matadadu);
                            }
                            if (val!=0)
                                sum += val;
                            else
                                break;
                        }
                        value = (double)sum/Math.pow(matadadu, rolltime);
                    }
                    System.out.println(value);
                    if (value > ans)
                        ans = value;
                }
                DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
                otherSymbols.setDecimalSeparator('.');
                otherSymbols.setGroupingSeparator(','); 
                DecimalFormat df = new DecimalFormat("0.000000", otherSymbols);
                //System.out.println();
                //in.nextLine();
                System.out.print("Case #" + cas + ": " + df.format(ans) + "\n");
                out.write("Case #" + cas + ": " + df.format(ans) + "\n");
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}