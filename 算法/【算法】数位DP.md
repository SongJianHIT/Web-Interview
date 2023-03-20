# 【算法】数位DP

## 1 基本介绍

基本思路：**集合和二进制是可以一一对应的！**

集合可以用二进制表示，二进制从低到高第 $i$ 位为 1 表示 $i$ 在集合中，为 0 表示 $i$ 不在集合中。例如集合 $\{0,2,3\}$ 对应的二进制数为 $1101_{(2)}$ 。

### 常用的两个二进制操作

- 检查一个元素 `d` 是否出现在集合中：`x >> d & 1`
- 把一个元素 `d` 添加到集合中：`x | (1 << d)`

### 常见 DP 参数设置

- `mask` ：前面选过的数字集合，换句话说，第 i 位要选的数字不能在 `mask` 中
- `isLimit`  ：表示当前是否受到了 n 的约束。若为真，则第 i 位填入的数字至多为 `s[i]` ，否则可以是 `9` 。如果在受到约束的情况下填了 `s[i]` ，那么后续填入的数字仍会受到 n 的约束
  - 例如： `n=134`，如果第二位选择了 `2` ，则第三位可以选择 `0~9` 的任意数；如果第二位选了 `3` ，则第三位必须受到约束，最高只能选择 `4`
- `isNum` ：表示 i 前面的数位是否填了数字。若为假，则当前位可以跳过（不填数字），或者要填入的数字至少为 1；若为真，则当前必须要填一个数字，填入数字的范围根据 `isLimit` 和 `isNum` 来决定。

>注：由于 `mask` 中记录了数字，可以通过判断 `mask` 是否为 0 来判断前面是否填了数字，所以 `isNum` 可以省略。下面的代码保留了 `isNum`，主要是为了方便大家掌握这个模板。因为有些题目不需要 `mask`，但需要 `isNum` 。

第一个参数通常根据题目要求进行变化，后面两个参数可以自选。

### 主要有几个注意点

- 记忆化搜索 `dp` 数组，记录的是 **不受限的** 方案数；在记录和使用前要判断
- 初始递归时，设置 `isLimit=true`，第一位肯定要受限，不然数字乱来
- 初始化 dp 数组大小，第二维度是 **集合的子集个数**

数位DP模版，以 [1012.至少有 1 位重复的数字](https://leetcode.cn/problems/numbers-with-repeated-digits/description/) 为例：

```java
class Solution {

    // 记忆化搜索
    int[][] dp;
    char[] s;

    public int numDupDigitsAtMostN(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        // 1 << 10: 集合 {0,1,2,3,4,5,6,7,8,9} 的子集个数。
        dp = new int[m][1 << 10];
        // 初始化记忆化搜索数组
        for (int i = 0; i < m; ++i) {
            // -1 表示没有计算过
            Arrays.fill(dp[i], -1);
        }
        return n - dfs(0, 0, true, false);
    }


    private int dfs(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length) {
            // 如果 isNum 为 true，则说明找到一个合法数字
            return isNum ? 1 : 0;
        }
        if (!isLimit && isNum && dp[i][mask] != -1) {
            // dp 数组：在不受到约束时的合法方案数
            return dp[i][mask];
        }
        int res = 0;
        if (!isNum) {
            // 前一位不是数字，当前可以选是否跳过
            // 跳过之后，后续的数字都不受约束了！
            res = dfs(i + 1, mask, false, false);
        }
        // 当前位的上限，是否被约束
        int up = isLimit ? s[i] - '0' : 9;
        // 枚举要填入的数字 d
        for (int d = isNum ? 0 : 1 ; d <= up; ++d) {
            // 根据题意筛选
            if ((mask >> d & 1) == 0) {
                // d 不在 mask 中，找出所有满足的结果加入res
                res += dfs(i + 1, mask | (1 << d), isLimit & d == up, true);
            }
        }
        // 只有不受约束的才被记忆化
        if (!isLimit && isNum) {
            dp[i][mask] = res;
        }
        return res;
    }       
}
```

## 2 经典题目

| 题目                                                         | 注释                | 补充 |
| ------------------------------------------------------------ | ------------------- | ---- |
| [1012.至少有 1 位重复的数字](https://leetcode.cn/problems/numbers-with-repeated-digits/description/) | 正难则反            |      |
| [面试题 17.06.2出现的次数](https://leetcode.cn/problems/number-of-2s-in-range-lcci/description/) | 注意 dfs 的参数变化 |      |
|                                                              |                     |      |





































