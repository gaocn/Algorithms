package pm.pc.vol3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 高文文 on 2016/12/28.
 * Problem：110305	Automated Judge Script
 *
 */
public class Alg305 {
    /** count judged problems */
    private int count;
    public Alg305() {
        count = 1;
    }

    /**
     * *           Accepted：输入solution与提交答案完全相等
     * * Presentation Error：将所有非数字部分删除后相等
     * *      Wrong Answer： 不属于上面任意一种情况
     */
    public void doAutomatedJudgeScript(List<String> solutions, List<String> submitted) {
        if(solutions == null || submitted == null)return;

        if(solutions.size() != submitted.size()) {
            System.out.println("Run #" + (count++) +": Wrong Answer");
        }

        if(solutions.equals(submitted)) {
            System.out.println("Run #" + (count++) +": Accepted");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for(String str : solutions) {
                for(char ch : str.toCharArray()) {
                    if(ch >= '0' && ch <= '9' ) {
                        stringBuilder.append(ch);
                    }
                }
            }
            String solutionNumbers = stringBuilder.toString();
//            System.out.println(""  + solutionNumbers);
            stringBuilder.delete(0, stringBuilder.length());
            for(String str : submitted) {
                for(char ch : str.toCharArray()) {
                    if(ch >= '0' && ch <= '9' ) {
                        stringBuilder.append(ch);
                    }
                }
            }
            String submittedNumbers = stringBuilder.toString();
//            System.out.println(""  + submittedNumbers);

            if(solutionNumbers.equals(submittedNumbers)) {
                System.out.println("Run #" + (count++) +": Presentation Error");
            } else {
                System.out.println("Run #" + (count++) +": Wrong Answer");
            }
        }
    }

    public static void main(String[] args) {

        Alg305 alg = new Alg305();

        List<String> solutions = new ArrayList<>();
        solutions.add("The answer is: 10");
        solutions.add("The answer is: 5");
        List<String> submitted = new ArrayList<>();
        submitted.add("The answer is: 10");
        submitted.add("The answer is:  5");
        alg.doAutomatedJudgeScript(solutions, submitted);

        solutions.clear();
        solutions.add("The answer is: 10");
        solutions.add("The answer is: 5");
        submitted.clear();
        submitted.add("The answer is: 10");
        submitted.add("The answer is: 15");
        alg.doAutomatedJudgeScript(solutions, submitted);

        solutions.clear();
        solutions.add("Input Set #1: YES");
        solutions.add("Input Set #2: NO");
        solutions.add("Input Set #3: NO");
        submitted.clear();
        submitted.add("Input Set #0: YES");
        submitted.add("Input Set #1: NO");
        submitted.add("Input Set #2: NO");
        alg.doAutomatedJudgeScript(solutions, submitted);


        solutions.clear();
        solutions.add("The judges are mean!");
        submitted.clear();
        submitted.add("The judges are good!");
        alg.doAutomatedJudgeScript(solutions, submitted);


    }
}
