package com.bigboy;

import java.util.*;
import java.math.*;

public class BigBoy09 {
    public static void main(String[] args) {
	String usage = "BigBoy09 [length > 25] [windowsize < length]";
	int size = -1;
	int winsize = 25;
	if(args.length != 1 && args.length != 2) {
	    System.err.println(usage);
	    return;
	}

	try {
	    size = Integer.parseInt(args[0]);
	    if(size < 25) {
		System.err.println(usage);
		return;
	    }
	} catch (Exception e) {
	    System.err.println(usage);
	    return;
	}

	if(args.length == 2) {
	    try {
		winsize = Integer.parseInt(args[1]);
		if(winsize > size-3 || winsize < 3) {
		    System.err.println(usage);
		    return;
		}
	    } catch (Exception e) {
		System.err.println(usage);
		return;
	    }
	}

	//winsize must be in range [3 <= winsize <= len - 3]
	Random rand = new Random(2020);
	
	System.err.printf("SIZE: %d, WINSIZE: %d%n", size, winsize);
	BigInteger abberation = null;

        //LinkedList<BigInteger> output = new LinkedList<BigInteger>();                                            
        LinkedList<BigInteger> window = new LinkedList<BigInteger>();
        for(int i = 0; i < winsize; i++) {
            int output_i = i*7 - i%3 - 1 + rand.nextInt(17);
            var tmp = BigInteger.valueOf(output_i);
            System.out.println(output_i);
            window.add(tmp);
        }

        int spike = rand.nextInt(size/4) + (3*size)/4 - 7;
        System.err.printf("SPIKE: %d%n", spike);
        for(int i = 25; i < size; i++) {
            int left = rand.nextInt(winsize);
            int right = rand.nextInt(winsize - 1);
            if(right == left)
                right = winsize - 1;

            assert left != right : "My math is wrong";

            var tmp = window.get(left).add(window.get(right));

            if(i == spike) {
                tmp = tmp.add(BigInteger.ONE);//get two random numbers from our set                                
                abberation = tmp;
                System.err.printf("ABBERATION: %s%n", abberation.toString());
            }
            else {
                window.pop();
                window.add(tmp);
                System.out.println(tmp);
            }
        }

        //generate a set of numbers which adds up to the abberation                                                
        BigInteger total = BigInteger.ZERO;
        int num_steps = 1;
        System.out.println(abberation);
        BigInteger min = abberation;
        BigInteger max = BigInteger.ZERO;
	BigInteger myMin = abberation.sqrt();
        while(total.compareTo(abberation) < 0) {
            num_steps++;
            BigInteger diff = abberation.subtract(total);
	    if(diff.compareTo(myMin) <= 0) {
		System.out.println(diff);
		total = total.add(diff);
		continue;
	    }
            int len = diff.bitLength();
            BigInteger res = new BigInteger(len, rand);
            if(res.compareTo(diff) >= 1)
                res = res.mod(diff);

            if(res.equals(BigInteger.ZERO))
                continue;

            min = min.min(res);
            max = max.max(res);
            System.out.println(res);
            total = total.add(res);
        }

        assert total.compareTo(abberation) == 0 :"Did not add the correct set for the abberation";

	//so you can check on these values later: front to back, you should never find these
	//otherwise, just comment this part out
        System.out.println(0);
        System.out.println(total);
	System.out.println(min);
	System.out.println(max);
	
	
        System.err.printf("num_steps: %d%n", num_steps);
        System.err.printf("MIN: %s%nMAX: %s%nTOTAL: %s%n",
               min.toString(),
               max.toString(),
               min.add(max).toString());

    }
}
