# 【算法】序列DP

## 0 概述

### 什么是序列dp？

序列 dp 是一类 `最长，最多的子序列` 的相关问题。

状态转移方程中 `dp[i]` 表示前 `i` 个元素 `a[0],a[1],…,a[i-1]` 的某种性质。

- 坐标型 dp 状态转移方程中 `f[i]` 表示以元素 `a[i]` 结尾的某种性质。
- 两个数组、序列、字符串的dp，通常设置 `dp[i][j]` 表示串1中以 `i-1` 结尾，并且串2中以 `j-1` 结尾的某种性质。 

## 1 经典题单

### 1.1 LCS & LIS

这类题需要注意一个概念上的区别：

- 子序列：不要求连续
- 子数组：要求连续

> 具体的定义根据题意进行推理！

| 题目                                                         | 类型       | 描述                                                         |
| :----------------------------------------------------------- | ---------- | ------------------------------------------------------------ |
| [300.最长递增子序列](https://leetcode.cn/problems/longest-increasing-subsequence/description/) | 序列DP     | 常规的dp方法可设置dp[i]为以i结尾的最长子序列长度，该方法 $O(N^2)$ |
| [674.最长连续递增序列](https://leetcode.cn/problems/longest-continuous-increasing-subsequence/) | 序列DP     | 是300的简单版本，只需要考虑连续即可，对每一个i，只能从i-1转移而来 |
| [718.最长重复子数组](https://leetcode.cn/problems/maximum-length-of-repeated-subarray/) | 二维序列DP | 两个数组中求解某种性质，二维dp                               |
| [1143.最长公共子序列](https://leetcode.cn/problems/longest-common-subsequence/) | 二维序列DP | 思路一样，都是讨论以...结尾的性质                            |
| [1035.不相交的线](https://leetcode.cn/problems/uncrossed-lines/) | 二维序列DP | 就是1143的变体，一模一样                                     |
| [53. 最大子序和](https://leetcode.cn/problems/maximum-subarray/description/) | 序列DP     | 跟买股票一次买卖的一样，可以使用DP，也可以使用贪心+前缀和    |
|                                                              |            |                                                              |

[300.最长递增子序列](https://leetcode.cn/problems/longest-increasing-subsequence/description/) 存在最优解，即使用「贪心+二分+DP」使时间复杂度降低至 $O(NlogN)$ 。



### 1.2 编辑距离





### 1.4 三叶题单

| 题目                                                         | 题解                                                         | 难度 | 推荐指数 |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---- | -------- |
| [139. 单词拆分](https://leetcode.cn/problems/word-break/)    | [LeetCode 题解链接](https://leetcode.cn/problems/word-break/solution/by-ac_oier-gh00/) | 中等 | 🤩🤩🤩🤩🤩    |
| [334. 递增的三元子序列](https://leetcode-cn.com/problems/increasing-triplet-subsequence/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/increasing-triplet-subsequence/solution/gong-shui-san-xie-zui-chang-shang-sheng-xa08h/) | 中等 | 🤩🤩🤩🤩     |
| [354. 俄罗斯套娃信封问题](https://leetcode-cn.com/problems/russian-doll-envelopes/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/russian-doll-envelopes/solution/zui-chang-shang-sheng-zi-xu-lie-bian-xin-6s8d/) | 困难 | 🤩🤩🤩🤩🤩    |
| [368. 最大整除子集](https://leetcode-cn.com/problems/largest-divisible-subset/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/largest-divisible-subset/solution/gong-shui-san-xie-noxiang-xin-ke-xue-xi-0a3jc/) | 中等 | 🤩🤩🤩🤩     |
| [390. 消除游戏](https://leetcode-cn.com/problems/elimination-game/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/elimination-game/solution/gong-shui-san-xie-yue-se-fu-huan-yun-yon-x60m/) | 中等 | 🤩🤩🤩🤩     |
| [446. 等差数列划分 II - 子序列](https://leetcode-cn.com/problems/arithmetic-slices-ii-subsequence/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/arithmetic-slices-ii-subsequence/solution/gong-shui-san-xie-xiang-jie-ru-he-fen-xi-ykvk/) | 困难 | 🤩🤩🤩🤩🤩    |
| [472. 连接词](https://leetcode-cn.com/problems/concatenated-words/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/concatenated-words/solution/gong-shui-san-xie-xu-lie-dpzi-fu-chuan-h-p7no/) | 困难 | 🤩🤩🤩🤩     |
| [522. 最长特殊序列 II](https://leetcode.cn/problems/longest-uncommon-subsequence-ii/) | [LeetCode 题解链接](https://leetcode.cn/problems/longest-uncommon-subsequence-ii/solution/by-ac_oier-vuez/) | 中等 | 🤩🤩🤩🤩🤩    |
| [583. 两个字符串的删除操作](https://leetcode-cn.com/problems/delete-operation-for-two-strings/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/delete-operation-for-two-strings/solution/gong-shui-san-xie-cong-liang-chong-xu-li-wqv7/) | 中等 | 🤩🤩🤩🤩     |
| [629. K个逆序对数组](https://leetcode-cn.com/problems/k-inverse-pairs-array/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/k-inverse-pairs-array/solution/gong-shui-san-xie-yi-dao-xu-lie-dp-zhuan-tm01/) | 中等 | 🤩🤩🤩🤩🤩    |
| [646. 最长数对链](https://leetcode.cn/problems/maximum-length-of-pair-chain/) | [LeetCode 题解链接](https://leetcode.cn/problems/maximum-length-of-pair-chain/solution/by-ac_oier-z91l/) | 中等 | 🤩🤩🤩🤩🤩    |
| [673. 最长递增子序列的个数](https://leetcode-cn.com/problems/number-of-longest-increasing-subsequence/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/number-of-longest-increasing-subsequence/solution/gong-shui-san-xie-lis-de-fang-an-shu-wen-obuz/) | 中等 | 🤩🤩🤩🤩     |
| [689. 三个无重叠子数组的最大和](https://leetcode-cn.com/problems/maximum-sum-of-3-non-overlapping-subarrays/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/maximum-sum-of-3-non-overlapping-subarrays/solution/gong-shui-san-xie-jie-he-qian-zhui-he-de-ancx/) | 困难 | 🤩🤩🤩🤩     |
| [740. 删除并获得点数](https://leetcode-cn.com/problems/delete-and-earn/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/delete-and-earn/solution/gong-shui-san-xie-zhuan-huan-wei-xu-lie-6c9t0/) | 中等 | 🤩🤩🤩🤩🤩    |
| [873. 最长的斐波那契子序列的长度](https://leetcode.cn/problems/length-of-longest-fibonacci-subsequence/) | [LeetCode 题解链接](https://leetcode.cn/problems/length-of-longest-fibonacci-subsequence/solution/by-ac_oier-beo2/) | 中等 | 🤩🤩🤩🤩🤩    |
| [926. 将字符串翻转到单调递增](https://leetcode.cn/problems/flip-string-to-monotone-increasing/) | [LeetCode 题解链接](https://leetcode.cn/problems/flip-string-to-monotone-increasing/solution/by-ac_oier-h0we/) | 中等 | 🤩🤩🤩🤩🤩    |
| [940. 不同的子序列 II](https://leetcode.cn/problems/distinct-subsequences-ii/) | [LeetCode 题解链接](https://leetcode.cn/problems/distinct-subsequences-ii/solution/by-ac_oier-ph94/) | 困难 | 🤩🤩🤩🤩🤩    |
| [978. 最长湍流子数组](https://leetcode-cn.com/problems/longest-turbulent-subarray/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/longest-turbulent-subarray/solution/xiang-jie-dong-tai-gui-hua-ru-he-cai-dp-3spgj/) | 中等 | 🤩🤩🤩      |
| [1035. 不相交的线](https://leetcode-cn.com/problems/uncrossed-lines/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/uncrossed-lines/solution/gong-shui-san-xie-noxiang-xin-ke-xue-xi-bkaas/) | 中等 | 🤩🤩🤩🤩     |
| [1092. 最短公共超序列](https://leetcode.cn/problems/shortest-common-supersequence/) | [LeetCode 题解链接](https://leetcode.cn/problems/shortest-common-supersequence/solution/by-ac_oier-s346/) | 困难 | 🤩🤩🤩🤩     |
| [1143. 最长公共子序列](https://leetcode-cn.com/problems/longest-common-subsequence/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/longest-common-subsequence/solution/gong-shui-san-xie-zui-chang-gong-gong-zi-xq0h/) | 中等 | 🤩🤩🤩🤩     |
| [1218. 最长定差子序列](https://leetcode-cn.com/problems/longest-arithmetic-subsequence-of-given-difference/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/longest-arithmetic-subsequence-of-given-difference/solution/gong-shui-san-xie-jie-he-tan-xin-de-zhua-dj1k/) | 中等 | 🤩🤩🤩🤩🤩    |
| [1235. 规划兼职工作](https://leetcode.cn/problems/maximum-profit-in-job-scheduling/) | [LeetCode 题解链接](https://leetcode.cn/problems/maximum-profit-in-job-scheduling/solution/by-ac_oier-rgup/) | 困难 | 🤩🤩🤩🤩     |
| [1473. 粉刷房子 III](https://leetcode-cn.com/problems/paint-house-iii/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/paint-house-iii/solution/gong-shui-san-xie-san-wei-dong-tai-gui-h-ud7m/) | 困难 | 🤩🤩🤩🤩     |
| [1537. 最大得分](https://leetcode.cn/problems/get-the-maximum-score/) | [LeetCode 题解链接](https://leetcode.cn/problems/get-the-maximum-score/solution/by-ac_oier-ht78/) | 困难 | 🤩🤩🤩🤩     |
| [1668. 最大重复子字符串](https://leetcode.cn/problems/maximum-repeating-substring/) | [LeetCode 题解链接](https://leetcode.cn/problems/maximum-repeating-substring/solution/by-ac_oier-xjhn/) | 简单 | 🤩🤩🤩🤩🤩    |
| [1691. 堆叠长方体的最大高度](https://leetcode.cn/problems/maximum-height-by-stacking-cuboids/) | [LeetCode 题解链接](https://acoier.com/2022/12/10/1691.%20%E5%A0%86%E5%8F%A0%E9%95%BF%E6%96%B9%E4%BD%93%E7%9A%84%E6%9C%80%E5%A4%A7%E9%AB%98%E5%BA%A6%EF%BC%88%E5%9B%B0%E9%9A%BE%EF%BC%89/) | 困难 | 🤩🤩🤩🤩     |
| [1713. 得到子序列的最少操作次数](https://leetcode-cn.com/problems/minimum-operations-to-make-a-subsequence/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/minimum-operations-to-make-a-subsequence/solution/gong-shui-san-xie-noxiang-xin-ke-xue-xi-oj7yu/) | 困难 | 🤩🤩🤩🤩🤩    |
| [1751. 最多可以参加的会议数目 II](https://leetcode-cn.com/problems/maximum-number-of-events-that-can-be-attended-ii/) | [LeetCode 题解链接](https://leetcode-cn.com/problems/maximum-number-of-events-that-can-be-attended-ii/solution/po-su-dp-er-fen-dp-jie-fa-by-ac_oier-88du/) | 困难 | 🤩🤩🤩🤩     |