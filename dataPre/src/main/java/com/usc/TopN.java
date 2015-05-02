package com.usc;

import java.util.*;

/**
 * @author zjiang91
 */
public class TopN {
    private int N;

    public TopN(int N) {
        this.N = N;
    }

    public void find(Map<Float, Set<String>> map) {
        System.out.println();
        Set<Float> keys = map.keySet();
        Float[] tfIdf = keys.toArray(new Float[keys.size()]);
        Arrays.sort(tfIdf);
        System.out.println("Minimum " + N + " elements:");
        for(int i = 0; i < N; i++) {
            float key = tfIdf[i];
            String value = join(map.get(key).toArray());
            System.out.println("No." + i + " : " + key + " ===> " + value);
        }

        System.out.println();
        System.out.println("Maximum" + N + " elements:");
        for(int i = tfIdf.length - 1; i >= tfIdf.length - N; i--) {
            float key = tfIdf[i];
            String value = join(map.get(key).toArray());
            System.out.println("No. " + i + ". " + key + " ===> " + value);
        }
    }

    public String join(Object[] a) {
        if(a == null)
            return "null";
        int iMax = a.length - 1;
        if(iMax == -1)
            return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0;;i++) {
            sb.append(String.valueOf(a[i]));
            if(i == iMax)
                return sb.toString();
            sb.append(" ");
        }
    }

}
