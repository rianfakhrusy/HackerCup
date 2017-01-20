import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

public class HackerCup {
    public static long numPossibilities(int numDice, int sum, int mata) {
        if (numDice == sum)
            return 1;
        else if (numDice == 0 || sum < numDice)
            return 0;
        else
        {
            if (hmap.get(new Key(numDice,sum,mata))!=null)
                return hmap.get(new Key(numDice,sum,mata));
                
            return numPossibilities(numDice, sum - 1, mata) +
                   numPossibilities(numDice - 1, sum - 1, mata) -
                   numPossibilities(numDice - 1, sum - mata - 1, mata);
        }
    }
    public static HashMap<Key, Long> hmap = new HashMap<>();
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
    public static long pascalTriangle(int r, int k)
    {
        if(r == 1 || k <= 1 || k >= r) return 1L;
        return pascalTriangle(r-1, k-1) + pascalTriangle(r-1, k);
    }
    
    public static void main(String[] args) {      
        
        int matadadu = 10;
        int rolltime = 14;
        int batas = 63;   
        int minval = rolltime;
        int maxval = rolltime*matadadu;
        for (int i=1;i<=batas;i++){
            long val = 0;//numPossibilities(rolltime,i,matadadu);
            if (hmap.get(new Key(rolltime,i,matadadu))==null){
                if (i<=(maxval+minval)/2){
                    val = numPossibilities(rolltime,i,matadadu);
                    hmap.put(new Key(rolltime,i,matadadu), val);
                } else {
                    val = numPossibilities(rolltime,maxval+minval-i,matadadu);
                }
                hmap.put(new Key(rolltime,i,matadadu), val);
                hmap.put(new Key(rolltime,maxval+minval-i,matadadu), val);
            } else
                val = hmap.get(new Key(rolltime,i,matadadu));
            
            System.out.println(i + " " + val);
        }
         /*       
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(','); 
        DecimalFormat df = new DecimalFormat("0.000000", otherSymbols);
        System.out.println(df.format(value));*/
        //System.out.println((double)Math.round(value * 100000d) / 100000d);
    }
    
}
