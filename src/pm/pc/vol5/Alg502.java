package pm.pc.vol5;


/**
 * Created by 高文文 on 2017/1/16.
 * Problem ID: 110502	Reverse and Add
 *
 */
public class Alg502 {


    public static void main(String[] args) {

        Alg502 alg = new Alg502();
        int n = 195;
        alg.doReverseAndAdd(195);
        alg.doReverseAndAdd(265);
        alg.doReverseAndAdd(750);

        alg.doReverseAndAdd(32767);
        alg.doReverseAndAdd(65535);
        alg.doReverseAndAdd(4294967295L);
        alg.doReverseAndAdd(0);
    }

    public void doReverseAndAdd(long number) {

        long reverseNum = reverse(number);
        long sum = number + reverseNum;
        long addTimes = 1;
        while(!isPolindrome(sum)) {
            number = sum;
            reverseNum = reverse(number);
            sum = reverseNum + number;
            addTimes++;
        }
        System.out.println(addTimes + " " + sum);
    }
    private long reverse(long number) {
        long reversedNum = 0;
        while(number != 0) {
            reversedNum *= 10L;
            reversedNum += number % 10;
            number = number / 10;
        }
//        System.out.println("reversedNum: " + reversedNum);
        return reversedNum;
    }


    private boolean isPolindrome(long number) {
        String numStr = String.valueOf(number);

        for(int i = 0, j = numStr.length() - 1; i < j; i++, j--) {
            if(numStr.charAt(i) != numStr.charAt(j)) {
                return false;
            }
        }

        return true;
    }
}
