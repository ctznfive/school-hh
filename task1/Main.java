import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int n = console.nextInt();
        int m = console.nextInt();

        long[] amountsOfMoney = new long[n];
        long totalCapital = 0;

        for (int i = 0; i < amountsOfMoney.length; i++) {
            amountsOfMoney[i] = console.nextLong();
            totalCapital += amountsOfMoney[i];
        }

        long bonusMax = totalCapital / m;
        long high = bonusMax + 1;
        long low = 1;

        if (bonusMax != 0) {
            while (high > low + 1) {
                long counter = 0;
                for (int i = 0; i < amountsOfMoney.length; i++)
                    counter += (amountsOfMoney[i] / bonusMax);
                if (counter >= m) {
                    low = bonusMax;
                    bonusMax += (high - low) / 2;
                } else {
                    high = bonusMax;
                    bonusMax -= (high - low) / 2;
                }
            }
            System.out.println(low);
        }
        else 
            System.out.println(bonusMax);
    }
}
