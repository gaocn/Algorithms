package pm.pc.vol4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 高文文 on 2017/1/6.
 *
 * Problem ID: 110404	Longest Nap
 */
public class Alg404 {

    private final static int START_HOUR = 10;
    private final static int END_HOUR = 18;

    static class Time implements Comparable<Time> {
        private int hour;
        private int minute;
        private int minutesFrom10;

        public Time(int hour, int minute) {
            this.hour = hour;
            this.minute = minute;
            minutesFrom10 = minute + (hour - 10) * 60;
        }

        @Override
        public int compareTo(Time other) {
            if(this.hour > other.hour) {
                return 1;
            } else if(this.hour < other.hour) {
                return -1;
            } else {
                if(this.minute > other.minute) {
                    return 1;
                } else if(this.minute < other.minute) {
                    return -1;
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            return "Time{" + hour +
                    " : " + minute +
                    '}';
        }
    }

    static class TimeInterval implements Comparable<TimeInterval> {
        Time startTime;
        Time endTime;

        public TimeInterval(Time startTime, Time endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public int compareTo(TimeInterval o) {
            return this.startTime.compareTo(o.startTime);
        }

        @Override
        public String toString() {
            return "TimeInterval{"  + startTime +
                    ", " + endTime +
                    '}';
        }
    }

    public void doLongestNap(List<TimeInterval> intervalList) {

        Collections.sort(intervalList);
        System.out.println(intervalList);

        int maxNapMinute = intervalList.get(0).startTime.minutesFrom10;
        int napIndex = 0;
        for(int i = 1; i < intervalList.size(); i++) {
            TimeInterval pre = intervalList.get(i - 1);
            TimeInterval cur = intervalList.get(i);
            int napMinute = cur.startTime.minutesFrom10 - pre.endTime.minutesFrom10;
            if(napMinute > maxNapMinute) {
                maxNapMinute = napMinute;
                napIndex = i;
            }
        }
        Time endTime = new Time(18, 0);
        int napMinute = endTime.minutesFrom10 - intervalList.get(intervalList.size() - 1).endTime.minutesFrom10;
        if(napMinute > maxNapMinute) {
            maxNapMinute = napMinute;
            napIndex = intervalList.size();
        }

        int hour = maxNapMinute / 60;
        int minutes = maxNapMinute % 60;
        String startHour = "";
        String startMinute = "";
        if(napIndex == 0) {
            startHour = "10";
            startMinute = "00";
        } else {
            startHour = intervalList.get(napIndex - 1).endTime.hour +"";
            startMinute = intervalList.get(napIndex - 1).endTime.minute < 10 ? "0" : "";
            startMinute += intervalList.get(napIndex - 1).endTime.minute;
        }


        System.out.print("the longest nap starts at "
                + startHour
                + ":"
                + startMinute
        );
        System.out.print(" and will last for ");
        if(hour != 0) {
            System.out.print(hour + " hours");
        }
        if(hour != 0 && minutes != 0) {
            System.out.print(" and ");
        }
        if(minutes != 0) {
            System.out.print( + minutes + " minutes");
        }
        System.out.println(".");
    }


    public static void main(String[] args) {
        Alg404 alg = new Alg404();

        TimeInterval time1 = new TimeInterval(new Time(10,0), new Time(12,0));
        TimeInterval time2 = new TimeInterval(new Time(12,0), new Time(13,0));
        TimeInterval time3 = new TimeInterval(new Time(13,0), new Time(15,0));
        TimeInterval time4 = new TimeInterval(new Time(15,30), new Time(17,45));

        List<TimeInterval> intervalList = new ArrayList<>();
        intervalList.add(time1);
        intervalList.add(time2);
        intervalList.add(time3);
        intervalList.add(time4);
        alg.doLongestNap(intervalList);

        List<TimeInterval> intervalList1 = new ArrayList<>();
        TimeInterval time5 = new TimeInterval(new Time(12,0), new Time(13,0));
        intervalList1.add(time5);
        alg.doLongestNap(intervalList1);
    }
}
