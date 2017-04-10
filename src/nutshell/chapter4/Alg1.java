package nutshell.chapter4;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by 高文文 on 2017/3/6.
 */
public class Alg1 {

    public static void main(String[] args) {

        int[] arr = {3, 4,2,19, 10, 1, 32, 56, 87, 59};
//        int[] arr = {1, 4, 8, 9, 11, 15, 7, 12, 13, 6};
//        insertSort(arr);
//        medianSort(arr, 0, arr.length - 1);
//        heapSort(arr);
//        countSort(arr, 200);
//        bucketSort(arr);
        pigeonHoleSort(arr);
        System.out.println(Arrays.toString(arr));

    }











    /***************************************************************************/
    public static void heapSort(int[] A) {
        buildHeap(A);
        //需要n-1交换操作
        for(int i= A.length - 1; i > 0; i--) {
            swap(A, 0, i);
            heapify(A, 0, i);
        }
    }
    public static void buildHeap(int[] A) {
        //最后一个非叶结点 [n/2] - 1
        for(int i = A.length / 2 - 1; i >= 0; i--) {
            heapify(A, i, A.length);
        }
    }
    /**max: 表示heap的最大下标*/
    private static void heapify(int[] A, int idx, int max) {
        int left  = 2 * idx + 1;
        int right = 2 * idx + 2;
        int largerIdx = idx;
        if(left < max && A[largerIdx] < A[left])
            largerIdx = left;
        if (right < max && A[largerIdx] < A[right])
            largerIdx = right;
        if(largerIdx != idx) {
            swap(A, idx, largerIdx);
            heapify(A, largerIdx, max);
        }
    }

    /***************************************************************************/
    public static void insertSort(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            int value = arr[i];
            int j = i - 1;
            for(; j >= 0 && arr[j] > value; j--);
            if(j + 1 == i)continue;
            //大块内存移动
            System.arraycopy(arr, j + 1, arr, j + 2, i - j - 1);
            arr[j + 1] = value;
        }
    }

    /***************************************************************************/
    public static void medianSort(int[] a, int left, int right) {
        if(left >= right) return;
        int mid = (right - left + 1) / 2;
        int me = selectKth(a, left, right, mid + 1);
        medianSort(a, left, left + mid - 1);
        medianSort(a, left + mid + 1, right);
    }

    public static int partition(int[] a, int left, int right, int pivotIdx) {
        // 将指定中值移动到末尾
        swap(a, pivotIdx, right);
        int pivot = a[right];
        int index = left - 1;

        for(int i = left; i <= right - 1; i++) {
            if(a[i] <= pivot) {
                index++;
                swap(a, index, i);
            }
        }
        swap(a, index + 1, right);
        return index + 1;
    }

    public static int selectKth(int[] a, int left, int right, int k) {

        //选择一个pivot index，可以使随机，第一个、最后一个，这里选择最后一个
        int idx = left;
        int pivotIndex = partition(a, left, right, idx);
        if(left + k - 1 == pivotIndex) return pivotIndex;

        if(left + k - 1 < pivotIndex)
            return selectKth(a, left, pivotIndex - 1, k);
        else
            return selectKth(a, pivotIndex + 1, right, k - (pivotIndex - left + 1));
    }

    /***************************************************************************/
    //sort a中的元素，元素范围在[0,k)
    public static void countSort(int[] a, int k) {
        int count[] = new int[k];
        for(int i = 0; i < a.length; i++)
            count[a[i]]++;

        int idx = 0;
        for(int j = 0; j < count.length; j++) {
            while (count[j]-- > 0)
                a[idx++] = j;
        }
        count = null;
    }

    /**************************************************************************/
    /**
     * 鸽巢排序是对桶排序算法的改进，只是桶中装对应下标放元素出现的次数；
     * 桶排序较鸽巢排序更加的实用,只有在差值(或者可被映射在差值)很小的范围内的数值排序的情况下实用
     * 它很少可以在灵活性, 简便性, 尤是速度上超过其他排序算法
     * 原理：
     * 数组的索引位置就表示值,该索引位置的值表示出现次数,如果全部为1次或0次那就是桶排序
     */
    public static void pigeonHoleSort(int[] a) {
        int max = a[0], min = a[0];
        for(int i = 1; i < a.length; i++) {
            max = Math.max(a[i], max);
            min = Math.min(a[i], min);
        }
        int range = max - min + 1;
        LinkedList<Integer>[] pigeonHole = new LinkedList[range];
        for(int i = 0; i < pigeonHole.length; i++) {
            pigeonHole[i] = new LinkedList<>();
        }

        for(int i = 0; i < a.length; i++) {
            int idx = a[i] - min;
           pigeonHole[idx].add(a[i]);
        }

        int idx = 0;
        for(int i = 0; i < pigeonHole.length; i++) {
            for(int j = 0; j < pigeonHole[i].size(); j++) {
                a[idx++] = pigeonHole[i].get(j);
            }
        }
    }

    /**************************************************************************/

    /**
     *  hash(e) = e / p ; => e < p*p 数组中的元素必须小于p*p
     *  numBucket=a.length;
     */
    public static void bucketSort(int[] a) {
        int numBucket = a.length;
        LinkedList<Integer>[] bucket = new LinkedList[numBucket];
        for(int i = 0; i < bucket.length; i++) {
            bucket[i] = new LinkedList<>();
        }
        //user hash function mapping elements to bucket;
        for(int i = 0; i < a.length; i++) {
            int bucketIdx = a[i] / a.length;
            bucket[bucketIdx].add(a[i]);
        }
        int idx = 0;
        for(int i = 0; i < bucket.length; i++) {
            Collections.sort(bucket[i]);
            for(int j = 0; j < bucket[i].size(); j++) {
                a[idx++] = bucket[i].get(j);
            }
        }
    }

    /**************************************************************************/







    public static void swap(int[] arr, int a, int b) {
        if( a == b) return;
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

}
