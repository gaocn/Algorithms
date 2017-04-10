package pm.pc.vol5;


/**
 * Created by 高文文 on 2017/1/16.
 * Problem ID: 110503	The Archeologists' Dilemma
 *
 */
public class Alg503 {


    public static void main(String[] args) {

        Alg503 alg = new Alg503();
        alg.doArchaeologistsDilemma(1);
        alg.doArchaeologistsDilemma(2);
        alg.doArchaeologistsDilemma(10);
    }

    public void doArchaeologistsDilemma(int P ) {
        double C = Math.log(10) / Math.log(2);
        double LP = Math.log(P) / Math.log(2);
        double LPP = Math.log(P + 1) / Math.log(2);

        double T = Math.floor(Math.log10(P)) + 2;

        for(; Math.ceil(LP + T * C) != Math.floor(LPP + T * C); T++);
        System.out.println((long) Math.floor(LPP + T * C));
    }
}


/*
So we’re looking for a power of 2 that has the same first digits as the input. Let’s call the input P, the exponent of 2 N, and the difference in digits between P and 2^N T. So basically, we want to find T and N such P = floor( 2^N/10^T ), now comes a little fun math.

By definition of floor we have
P <= 2^N/10^T < P + 1
Multiply 10^T
P * 10^T <= 2^N < (P + 1)*10^T
Take log2 of both sides
log2(P*10^T) <= N < log2((P + 1)*10^T)
Soem log identities
log2(P) + T*log2(10) <= N < log2(P+1) + T*log2(10)

So now we want to find an Integer N and T that satisfy this relation.
Let ceil(log2(P) + T*log2(10)) = m
By definition

log2(P) + T*log2(10) <= m < log2(P) + T*log2(10) + 1

Let floor(log2(P+1) + T*log2(10)) = n
By definition

log2(P+1) + T*log2(10) – 1 < n <= log2(P+1) + T*log2(10)

Thus, if we find values for T that cause n = m = some integer k, we have the following true inequality
log2(P) + T*log2(10) <= k < log2(P) + T*log2(10) + 1
log2(P+1) + T*log2(10) – 1 < k <= log2(P+1) + T*log2(10)
which be restated as
log2(P) + T*log2(10) <= k <= log2(P+1) + T*log2(10), which satisfies the inequality we want, making k = N;
 */
