package com.syr.module_algorithm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.syr.module_common.net.core.RouterHub
import kotlin.math.max

@Route(path = RouterHub.ALGORITHM_ALGORITHMACTIVITY)
class AlgorithmActivity : AppCompatActivity() {
    private val tag = "AlgorithmActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algorithm)
        //62 不同路径
//        uniquePaths(1, 1)

        //860 柠檬水找零
//        lemonadeChange(intArrayOf(5, 5, 5, 10, 20))

        //387. 字符串中的第一个唯一字符
//        Log.e(tag, "${firstUniqChar("cc")}")

        //135. 分发糖果
//        Log.e("candy", "${candy(intArrayOf(1, 3, 5, 3, 2, 1))}")


        //34. 在排序数组中查找元素的第一个和最后一个位置
        val result = searchRange(intArrayOf(5, 7, 7, 8, 8, 10), 6)
        result.forEach { Log.e("searchRange", "$it") }

    }

    private fun searchRange(nums: IntArray, target: Int): IntArray {
        val result = IntArray(2) { _ -> -1 }
        var low = 0
        var high = nums.size - 1
        var middle = 0
        while (low <= high) {
            middle = (low + high) / 2
            if (nums[middle] < target) {
                low = middle + 1
            } else if (nums[middle] > target) {
                high = middle - 1
            } else {
                break
            }
        }
        if (low > high) return result
        Log.e("searchRange", "middle=$middle")
        result[0] = middle - 1
        result[1] = middle + 1
        while (result[0] >= 0 && nums[result[0]] == target) {
            result[0] = result[0] - 1
        }
        while (result[1] < nums.size && nums[result[1]] == target) {
            result[1] = result[1] + 1
        }
        result[0] = result[0] + 1
        result[1] = result[1] - 1
        return result
    }

    private fun candy(ratings: IntArray): Int {
        val left = IntArray(ratings.size) { i -> 1 }
        val right = IntArray(ratings.size) { i -> 1 }
        for (i in 1 until ratings.size) {
            if (ratings[i] > ratings[i - 1]) left[i] = left[i - 1] + 1
        }
        var count = left[ratings.size - 1]
        for (i in ratings.size - 2 downTo 0) {
            if (ratings[i] > ratings[i + 1]) right[i] = right[i + 1] + 1
            count += max(left[i], right[i])
        }
        return count
    }

    private fun firstUniqChar(s: String): Int {
        val ans = IntArray(26)
        s.forEach {
            ans[it - 'a']++
        }
        s.forEachIndexed { index, c ->
            if (ans[c - 'a'] == 1) return index
        }
        return -1;
    }

    private fun lemonadeChange(bills: IntArray): Boolean {
        var fiveNum = 0
        var tenNum = 0
        bills.forEach {
            when (it) {
                10 -> {
                    if (fiveNum < 1) return false
                    else {
                        fiveNum--
                        tenNum++
                    }
                }
                20 -> {
                    if (fiveNum > 0 && tenNum > 0) {
                        fiveNum--
                        tenNum--
                    } else if (fiveNum >= 3) {
                        fiveNum -= 3
                    } else {
                        return false
                    }
                }
                else -> {
                    fiveNum++
                }
            }
        }
        return true
    }

    private fun uniquePaths(m: Int, n: Int): Int {
        if (m < 1 || n < 1) return 0
        val temp = Array(m) { i -> IntArray(n) }
        for (i in 0 until m) {
            for (j in 0 until n) {
                if (i == 0 || j == 0) {
                    temp[i][j] = 1
                } else {
                    temp[i][j] = temp[i][j - 1] + temp[i - 1][j]
                }
            }
        }
        return temp[m - 1][n - 1]
    }
}