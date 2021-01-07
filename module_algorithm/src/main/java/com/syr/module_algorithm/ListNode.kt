package com.syr.module_algorithm

/**
 * Created by songyaru on 2020/12/29.
 */
class ListNode(var `val`: Int) {
    var next: ListNode? = null
    override fun toString(): String {
        var result = "${`val`}"
        while (next != null) {
            result = result.plus("->${next?.`val`}")
            next = next?.next
        }
        return result
    }
}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}