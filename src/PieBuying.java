import java.util.*;
import java.io.*;

public class PieBuying{  
    static String inname = "input.txt"; 
    static String outname = "output.txt"; 
    public static class Key implements Comparable<Key>{
        public int p;
        public int k2;
        public Key(int key1, int key2) {
            this.p = key1;
            this.k2 = key2;
        }
        public int compareTo(Key that) {
            if(this.p > that.p) return 1;
            if(this.p < that.p) return -1;
            return 0;
        }
    }
    public static void main(String[] args){
        try{
            Scanner in = new Scanner(new BufferedReader(new FileReader(inname)));
            //Scanner in = new Scanner(System.in);
            BufferedWriter out = new BufferedWriter(new FileWriter(outname));
            int t = in.nextInt();
            in.nextLine();
            for (int cas = 1; cas <= t; cas++){
                int ans = 0;
                int n = in.nextInt();
                int m = in.nextInt();
                
                boolean[][] bool = new boolean[n][m];
                List<List<Integer>> l = new ArrayList<>();
                
                int[] dp = new int[n];
                
                
                int k = 0;
                for (int j=0;j<n;j++){
                    List<Integer> subl = new ArrayList<>();
                    for (int i=0;i<m;i++){
                        subl.add(in.nextInt());
                    }
                    Collections.sort(subl);
                    l.add(subl);
                }
                for (int j=0;j<n;j++){
                    //System.out.println(subl.toString());
                    if (k==0){
                        dp[k] = l.get(0).get(0)+1;
                        bool[0][0] = true;
                        k++;
                        System.out.println("dp yang ke-" + j + " = " + dp[j]);
                    } else {
                        int x = 0;
                        int y = 0;
                        int mins = 1000001;
                        int last = 1000001;
                        for (int i=0;i<k;i++){
                            int idx = 0;
                            mins = 1000001;
                            boolean pas = false;
                            while (bool[i][idx]==true){
                                idx++;
                                if (idx==m){
                                    pas = true;
                                    break;
                                }
                            }
                            if (!pas){
                                if (l.get(i).get(idx)==l.get(i).get(idx-1)){
                                    //int cnt = 0;
                                    //int curs = l.get(i).get(idx-1);
                                    //int kurang = m - curs;
                                    //try {
                                        //while ((l.get(i).get(curs)==l.get(i).get(idx))){
                                        //    curs--;
                                        //    cnt++;
                                        //    if (curs<0) break;
                                        //}
                                    //} catch (Exception e){}
                                    //System.out.println("cunt " + cnt);
                                    //System.out.println("curs " + curs);
                                    int newmins = dp[k-1];
                                    int curs = l.get(i).get(idx);
                                    int add = 0;
                                    try {
                                        while ((l.get(i).get(curs)==l.get(i).get(idx))){
                                            add++;
                                            curs--;
                                        }
                                    } catch (Exception e){}
                                    int adc = add - 1;
                                    int ls = l.get(i).get(idx);
                                    //System.out.println("newkur " + newmins);
                                    newmins -= adc*ls + adc*adc;
                                    //System.out.println("newplus " + newmins);
                                    newmins += add*ls + add*add;
                                    System.out.println("kanas " + newmins);
                                    if (newmins < mins){
                                        mins = newmins;
                                        x = k-1;
                                        y = idx;
                                    }
                                    if (j==n-1)
                                        if (newmins < last){
                                            last = newmins;
                                        }
                                } else {
                                    int newmins = dp[k-1]+l.get(i).get(idx)+1;
                                    System.out.println("kanan " + newmins);
                                    if (newmins < mins){
                                        mins = newmins;
                                        x = k-1;
                                        y = idx;
                                    }
                                    //System.out.println(j + " " + n);
                                    if (j==n-1)
                                        if (newmins < last){
                                            last = newmins;
                                        }
                                }
                            }
                        }
                        try {
                            System.out.println("bawah " + (dp[j-1]+l.get(k).get(0)+1));
                            //System.out.println(mins);
                            if (dp[j]==0){
                                if (j==n-1)
                                    dp[j] = Math.min(last,dp[j-1]+l.get(k).get(0)+1);
                                else
                                    dp[j] = Math.min(mins,dp[j-1]+l.get(k).get(0)+1);
                                if (dp[j]==dp[j-1]+l.get(k).get(0)+1){
                                    bool[k][0] = true;
                                    k++;
                                } else {
                                    System.out.println("x = " + x);
                                    System.out.println("y = " + y);
                                    bool[x][y] = true;
                                }
                                System.out.println("dp yang ke-" + j + " = " + dp[j]);
                            }
                        } catch (Exception e){

                        }
                    }
                    //l.add(subl);
                }
                //for (int i=0;i<n;i++){
                    //System.out.println(dp[i]);
                //}
                ans = dp[n-1];
                /*
                int[] a = new int[n*m];
                int count = 0;
                for (int i =0;i<n;i++){
                    Map<Integer, Integer> freq = new HashMap<>();
                    for (int j=0;j<m;j++){
                        int an = in.nextInt();
                        if(freq.containsKey(an)){
                            freq.put(an, freq.get(an)+1);
                        }
                        else
                        {
                            freq.put(an, 1);
                        }
                    }
                    //System.out.println(freq.toString());
                    Object[] k = freq.keySet().toArray();
                    Object[] v = freq.values().toArray();
                    for (int l =0;l<(freq.size()*(int)v[0]);l++){
                        //System.out.println(s[i]);
                        int ke = (int) k[0];
                        int ve = (int) v[0];
                        //System.out.println(ke + " " + ve);
                        a[count++] = ke + ve;
                    }
                    for (int l = 0;l<count;l++){
                        //System.out.println(a[l]);
                    }
                }
                Arrays.sort(a);
                
                for (int i = 0;i<n;i++){
                    System.out.println(a[i]);
                    ans += a[i];
                }
                
                Map<Integer, Integer> freq = new HashMap<>();
                for (int i =0;i<n;i++){
                    if(freq.containsKey(a[i].p)){
                        freq.put(a[i].p, freq.get(a[i].p)+1);
                    }
                    else
                    {
                        freq.put(a[i].p, 1);
                    }
                }
                
                for (int i =0;i<freq.size();i++){
                    Object[] k = freq.keySet().toArray();
                    Object[] v = freq.values().toArray();
                    //System.out.println(s[i]);
                    int ke = (int) k[i];
                    int ve = (int) v[i];
                    System.out.println(ke + " " + ve);
                    ans += ke*ve + ve*ve;
                }*/
                
                
                
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