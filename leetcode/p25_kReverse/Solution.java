package p25_kReverse;

/**
 * Solves the "Reverse Nodes in k-Group" problem.
 */
public class Solution {

    /**
     * Reverses the nodes of a linked list k at a time.
     *
     * This method iterates through the linked list and reverses nodes in groups of k.
     * If the number of remaining nodes at the end is less than k, they are left unchanged.
     * The reversal is done in-place, using constant extra space.
     *
     * Time Complexity: O(n) - Each node is processed a couple of times.
     * Space Complexity: O(1) - The reversal is performed in-place.
     *
     * @param head The head of the linked list.
     * @param k The size of the group of nodes to reverse.
     * @return The new head of the modified linked list.
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k <= 1) {
            return head;
        }

        // First, count the number of nodes in the list to know how many full groups we have.
        int n = 0;
        for (ListNode p = head; p != null; p = p.next) {
            n++;
        }

        // Use a dummy node to simplify list manipulation, especially for the first group.
        ListNode dummy = new ListNode(0, head);
        // p0 is the node just before the current k-group.
        ListNode p0 = dummy;
        ListNode current = head;

        // Iterate through the list, reversing one k-group at a time.
        for (int i = 0; i < n / k; i++) {
            ListNode prev = null;
            ListNode groupStart = current;

            // Standard in-place reversal for k nodes.
            for (int j = 0; j < k; j++) {
                ListNode nextTemp = current.next;
                current.next = prev;
                prev = current;
                current = nextTemp;
            }

            // Re-wire the pointers to connect the reversed group back into the list.
            p0.next = prev; // `p0` now points to the new head of the reversed group.
            groupStart.next = current; // The tail of the reversed group points to the start of the next group.

            // Move p0 to the end of the just-reversed group for the next iteration.
            p0 = groupStart;
        }

        return dummy.next;
    }
}