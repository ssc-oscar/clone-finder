package kylin;

import java.util.*;

/**
 * Created by KyLinx on 2018/10/10.
 */
public class Main3 {

    public static List<List<Integer>> threeSum1(int[] nums) {
        Map<Integer, List<int[]>> twoSumMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                int ni = nums[i], nj = nums[j];
                if(ni > nj) {
                    int tmp = nj;
                    nj = ni;
                    ni = tmp;
                }
                int sum = ni + nj;
                int[] sumArray = new int[]{ni, nj, i, j};
                if(!twoSumMap.containsKey(sum)) {
                    List<int[]> list = new LinkedList<>();
                    list.add(sumArray);
                    twoSumMap.put(sum, list);
                } else {
                    List<int[]> list = twoSumMap.get(sum);
                    list.add(sumArray);
                }
            }
        }
        List<List<Integer>> result = new LinkedList<>();
        Set<Integer> existOne = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if(existOne.contains(nums[i])) continue;
            existOne.add(nums[i]);
            List<int[]> list = twoSumMap.get(-nums[i]);
            if(list != null) {
                Set<Integer> existTwo = new HashSet<>();
                for (int[] existSum : list) {
                    if(existSum[2] == i || existSum[3] == i) continue;
                    if(nums[i] > existSum[0]) continue;
                    if(existTwo.contains(existSum[0])) continue;
                    existTwo.add(existSum[0]);
                    List<Integer> oneResult = new ArrayList<>(3);
                    oneResult.add(nums[i]);
                    oneResult.add(existSum[0]);
                    oneResult.add(existSum[1]);
                    result.add(oneResult);
                }
            }
        }
        return result;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        Map<Integer, Set<Integer>> existMap = new HashMap<>();
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            int ni = nums[i];
            if(ni + ni + ni > 0) break;
            if(existMap.containsKey(ni)) continue;
            Set<Integer> existTwo = new HashSet<>();
            existMap.put(ni, existTwo);
            for (int j = i + 1; j < nums.length - 1; j++) {
                int nj = nums[j];
                if(ni + nj + nj > 0) break;
                if(existTwo.contains(nj)) continue;
                existTwo.add(nj);
                int pos = Arrays.binarySearch(nums,j + 1, nums.length, -(ni + nj));
                if(pos >= j + 1) {
                    List<Integer> aResult = new ArrayList<>();
                    aResult.add(ni);
                    aResult.add(nj);
                    aResult.add(nums[pos]);
                    result.add(aResult);
                }
            }
        }
        return result;
    }

    public void setZeroes(int[][] matrix) {
        int row = matrix.length;
        if(row <= 0) return;
        int column = matrix[0].length;
        boolean setRow0 = false;
        boolean setColumn0 = false;
        for (int i = 0; i < column; i++) {
            if(matrix[0][i] == 0) {
                setRow0 = true;
                break;
            }
        }
        for (int i = 0; i < row; i++) {
            if(matrix[i][0] == 0) {
                setColumn0 = true;
                break;
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if(matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }
        for (int i = 1; i < row; i++) {
            if(matrix[i][0] == 0) {
                for (int j = 1; j < column; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < column; i++) {
            if(matrix[0][i] == 0) {
                for (int j = 1; j < row; j++) {
                    matrix[j][i] = 0;
                }
            }
        }
        if(setRow0) {
            for (int i = 0; i < column; i++) {
                matrix[0][i] = 0;
            }
        }
        if(setColumn0) {
            for (int i = 0; i < row; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public List<List<String>> groupAnagrams1(String[] strs) {
        Map<String, List<String>> resultMap = new HashMap<>();
        for (String str : strs) {
            char[] ca = str.toCharArray();
            Arrays.sort(ca);
            resultMap.computeIfAbsent(new String(ca), key -> new LinkedList<>()).add(str);
        }
        return new ArrayList<>(resultMap.values());
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> resultMap = new HashMap<>();
        int[] alpha = new int[26];
        StringBuilder sb = new StringBuilder(200);
        for (String str : strs) {
            Arrays.fill(alpha, 0);
            char[] ca = str.toCharArray();
            for (char c : ca) {
                ++alpha[c - 'a'];
            }
            sb.delete(0, sb.length());
            for (int i = 0; i < 26; i++) {
                if(alpha[i] > 0) {
                    sb.append((char)('a'+i));
                    sb.append(alpha[i]);
                }
            }
            resultMap.computeIfAbsent(sb.toString(), key -> new LinkedList<>()).add(str);
        }
        return new ArrayList<>(resultMap.values());
    }

    public static int lengthOfLongestSubstring(String s) {
        char[] ca = s.toCharArray();
        Map<Character, Integer> lastIndex = new HashMap<>();
        int max = 0;
        int lastStr = 0;
        for (int i = 0; i < ca.length; i++) {
            char c = ca[i];
            if(lastIndex.containsKey(c) && lastStr <= lastIndex.get(c)) {
                if(max < i - lastStr) {
                    max = i - lastStr;
                }
                lastStr = lastIndex.get(c) + 1;
            }
            lastIndex.put(c, i);
        }
        if(max < ca.length - lastStr) {
            max = ca.length - lastStr;
        }
        return max;
    }

    public static String longestPalindrome(String s) {
        char[] ca = s.toCharArray();
        int resultBegin = 0, resultEnd = 0, resultLen = 0;
        for (int begin = 0; begin < ca.length; begin++) {
            if(ca.length - begin <= resultLen) break;
            for (int end = ca.length; end > begin; end--) {
                if(end - begin <= resultLen) break;
                boolean success = true;
                int i = begin, j = end - 1;
                while(i < j) {
                    if(ca[i++] == ca[j--]) continue;
                    success = false;
                    break;
                }
                if(success) {
                    resultBegin = begin;
                    resultEnd = end;
                    resultLen = end - begin;
                    break;
                }
            }
        }
        return new String(ca, resultBegin, resultLen);
    }

    public static boolean increasingTriplet(int[] nums) {
        if(nums.length < 3) return false;
        int one = nums[0];
        int i = 1;
        for(; i < nums.length; i++) {
            if(nums[i] > one) break;
            one = nums[i];
        }
        if(i == nums.length) return false;
        int two = nums[i++];
        int lowerTwo = two;
        while(i < nums.length) {
            for (; i < nums.length; i++) {
                if (nums[i] > lowerTwo) break;
                lowerTwo = nums[i];
                if (lowerTwo > one) {
                    two = lowerTwo;
                }
            }
            if (i == nums.length) return false;
            int three = nums[i++];
            if(three > two) return true;
            one = lowerTwo;
            two = three;
            lowerTwo = two;
        }
        return false;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }

    public static ListNode stringToListNode(String input) {
        // Generate array from the input
        int[] nodeValues = stringToIntegerArray(input);

        // Now convert that list into linked list
        ListNode dummyRoot = new ListNode(0);
        ListNode ptr = dummyRoot;
        for(int item : nodeValues) {
            ptr.next = new ListNode(item);
            ptr = ptr.next;
        }
        return dummyRoot.next;
    }

    public static String listNodeToString(ListNode node) {
        if (node == null) {
            return "[]";
        }

        String result = "";
        while (node != null) {
            result += Integer.toString(node.val) + ", ";
            node = node.next;
        }
        return "[" + result.substring(0, result.length() - 2) + "]";
    }

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        int add = 0;
        ListNode tail = null;
        ListNode head = null;
        while(l1 != null || l2 != null) {
            if(l1 != null) {
                add += l1.val;
                l1 = l1.next;
            }
            if(l2 != null) {
                add += l2.val;
                l2 = l2.next;
            }
            ListNode curr = new ListNode(add % 10);
            add = (add - curr.val) / 10;
            if(tail == null) {
                head = curr;
                tail = curr;
            } else {
                tail.next = curr;
                tail = curr;
            }
        }
        if(add > 0) {
            ListNode curr = new ListNode(add);
            tail.next = curr;
        }
        return head;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        long count = 1;
        long i1 = 0;
        while(l1 != null) {
            i1 += l1.val * count;
            count = count * 10;
            l1 = l1.next;
        }
        count = 1;
        long i2 = 0;
        while(l2 != null) {
            i2 += l2.val * count;
            count = count * 10;
            l2 = l2.next;
        }
        long i3 = i1 + i2;
        ListNode head = new ListNode((int)(i3 % 10));
        ListNode tail = head;
        i3 = i3 / 10;
        while(i3 != 0) {
            tail.next = new ListNode((int)(i3 % 10));
            tail = tail.next;
            i3 = i3 / 10;
        }
        return head;
    }

    public static ListNode oddEvenList(ListNode head) {
        if(head == null) return null;
        ListNode oddHead = head;
        ListNode oddTail = oddHead;
        ListNode evenHead = head.next;
        ListNode evenTail = evenHead;
        head = evenHead == null ? null : evenHead.next;
        int i = 3;
        while(head != null) {
            if(i % 2 == 0) {
                evenTail.next = head;
                evenTail = head;
            } else {
                oddTail.next = head;
                oddTail = head;
            }
            head = head.next;
            ++i;
        }
        oddTail.next = evenHead;
        if(evenTail != null) evenTail.next = null;
        return oddHead;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = 0;
        int lenB = 0;
        ListNode tmp = headA;
        ListNode tailA = headA;
        while(tmp != null) {
            ++lenA;
            tmp = tmp.next;
            if(tmp != null) tailA = tmp;
        }
        tmp = headB;
        ListNode tailB = headB;
        while(tmp != null) {
            ++lenB;
            tmp = tmp.next;
            if(tmp != null) tailB = tmp;
        }
        if(tailA != tailB) return null;
        int delta = lenB - lenA;
        while(delta < 0) {
            headA = headA.next;
            ++delta;
        }
        while(delta > 0) {
            headB = headB.next;
            --delta;
        }
        while(headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        if(root == null) return Collections.emptyList();
        List<Integer> result = new LinkedList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.getFirst();
            if(node.left != null) {
                queue.addFirst(node.left);
                node.left = null;
            } else {
                result.add(node.val);
                queue.removeFirst();
                if(node.right != null) {
                    queue.addFirst(node.right);
                }
            }
        }
        return result;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if(root == null) return result;
        LinkedList<TreeNode> curr = new LinkedList<>();
        LinkedList<TreeNode> next = new LinkedList<>();
        curr.add(root);
        int level = 1;
        while(!curr.isEmpty()) {
            List<Integer> values = new ArrayList<>(curr.size());
            while(!curr.isEmpty()) {
                TreeNode node = curr.removeFirst();
                values.add(node.val);
                if(level % 2 == 0) {
                    if(node.right != null) next.addFirst(node.right);
                    if(node.left != null) next.addFirst(node.left);
                } else {
                    if(node.left != null) next.addFirst(node.left);
                    if(node.right != null) next.addFirst(node.right);
                }
            }
            result.add(values);
            level++;
            LinkedList<TreeNode> tmp = curr;
            curr = next;
            next = tmp;
        }
        return result;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree0(preorder, inorder, 0, 0, preorder.length);
    }

    public TreeNode buildTree0(int[] preorder, int[] inorder, int preorderBegin, int inorderBegin, int len) {
        if(len <= 0) return null;
        TreeNode thisNode = new TreeNode(preorder[preorderBegin]);
        if(len == 1) return thisNode;
        int thisAtInorder = inorderBegin - 1;
        int thisValue = thisNode.val;
        while(inorder[++thisAtInorder] != thisValue);
        int leftLen = thisAtInorder - inorderBegin;
        thisNode.left = buildTree0(preorder, inorder, preorderBegin + 1, inorderBegin, leftLen);
        thisNode.right = buildTree0(preorder, inorder, preorderBegin + 1 + leftLen, thisAtInorder + 1, len - leftLen - 1);
        return thisNode;
    }

    public static class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;
        TreeLinkNode(int x) { val = x; }
    }

    public void connect(TreeLinkNode root) {

    }

    public static void main(String[] args) {
        System.out.println(listNodeToString(addTwoNumbers(
                stringToListNode("[9]"),
                stringToListNode("[1,9,9,9,9,9,9]")
        )));
    }

}
