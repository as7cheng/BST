//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: BALST.java
// Files: BALST.java BALSTTest.java
// Course: Comp Sci 400, section 002
//
// Author: Shihan Cheng
// Email: scheng93@wisc.edu
// Lecturer's Name: Debra Deppeler
// Description: This program is designed to create a Data Structure storing the
// data of pairs of key and value, to create tests to test if the Data Structure
// works via using JUnit test. This Data Structure implements AVL tree
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class to implement a BalanceSearchTree. Can be of type AVL or Red-Black. Note
 * which tree you implement here and as a comment when you submit.
 * 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {

  /**
   * Inner class to create a BST Node
   * 
   * @author shihan
   *
   * @param <K> Node's key
   * @param <V> Node's value
   */
  class BSTNode<K, V> {

    // Fields for the inner class
    K key;
    V value;
    BSTNode<K, V> left;
    BSTNode<K, V> right;
    int balanceFactor;
    int height;


    /**
     * Constructor for BST Node
     * 
     * @param key        Node's key
     * @param value      Node's value
     * @param leftChild  Node's left child
     * @param rightChild Node's right child
     */
    BSTNode(K key, V value, BSTNode<K, V> leftChild, BSTNode<K, V> rightChild) {
      this.key = key;
      this.value = value;
      this.left = leftChild;
      this.right = rightChild;
      this.height = 0;
      this.balanceFactor = 0;
    }

    /**
     * Constructor for BST Node without children
     * 
     * @param key   Node's key
     * @param value Node's value
     */
    BSTNode(K key, V value) {
      this(key, value, null, null);
    }

  }


  // Fields for BST class
  private BSTNode<K, V> root;
  private int numKeys;


  // Constructor
  public BALST() {
  }

  /**
   * Method to get root of current AVL
   */
  @Override
  public K getKeyAtRoot() {
    return this.root.key;
  }

  /**
   * Helper method to get the node with
   * 
   * @param key Key of the node that to find
   * @return Node with matching key
   */
  private BSTNode<K, V> getNode(K key) {
    return getNodeHelper(key, this.root);
  }

  /**
   * Helper method to get a node with given key
   * 
   * @param node Root or the starting node
   * @param key  Key of the node that to find
   * @return Node with matching key
   *
   */
  private BSTNode<K, V> getNodeHelper(K key, BSTNode<K, V> node) {
    // Base case, node found
    if (node == null) {
      return null;
    }

    if (node.key.compareTo(key) == 0) {
      return node;
    } else if (node.key.compareTo(key) > 0) {
      // Recursion, if key is less than current node, go to right
      return getNodeHelper(key, node.left);
    } else { // If key is greater than current node, go to left
      return getNodeHelper(key, node.right);
    }
  }

  /**
   * Method to find a node's left child via its key
   * 
   * @param key Node's key
   * @return Key of Node's left child
   */
  @Override
  public K getKeyOfLeftChildOf(K key)
      throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (!this.contains(key))
      throw new KeyNotFoundException();

    return getNode(key).left.key;
  }

  /**
   * Method to find a node's right child via its key
   * 
   * @param key Node's key
   * @return Key of Node's right child
   */
  @Override
  public K getKeyOfRightChildOf(K key)
      throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (!this.contains(key))
      throw new KeyNotFoundException();

    return getNode(key).right.key;
  }

  /**
   * Method to get the height of AVL
   * 
   * @return Height of AVL
   */
  @Override
  public int getHeight() {
    return getHeightHelper(this.root);
  }

  /**
   * Method to get height of a node
   * 
   * @param node Node which tries to get height
   * @return Height of the node
   */
  private int getHeight(BSTNode<K, V> node) {
    return getHeightHelper(node);
  }

  /**
   * Method to get balance factor for current node
   * 
   * @param node Node needs balance factor
   * @return balance factor for the node
   */
  private int getBalanceFactor(BSTNode<K, V> node) {
    if (node.left == null && node.right == null)
      return 0;
    else
      return getHeight(node.left) - getHeight(node.right);
  }

  /**
   * Method to maintain the height of all nodes in AVL
   */
  private void resetHeight() {
    List<K> list = getInOrderTraversal();
    for (K key : list) {
      if (getNode(key) != null)
        getNode(key).height = getHeightHelper(getNode(key));
    }
  }

  /**
   * Helper method to get the height of current node
   * 
   * @param node Current node
   * @return Height of AVL
   */
  private int getHeightHelper(BSTNode<K, V> node) {
    // Base case, if current node is null
    if (node == null)
      return 0;
    // Recursion
    return java.lang.Math.max(1 + getHeightHelper(node.left),
        1 + getHeightHelper(node.right));
  }


  /**
   * Helper method to check if AVL is empty
   * 
   * @return true if AVL is empty, false otherwise
   */
  private boolean isEmpty() {
    if (this.root == null)
      return true;

    return false;
  }

  /**
   * Method to traversal AVL with In-Order
   * 
   * @return List contains all nodes with In-Order
   */
  @Override
  public List<K> getInOrderTraversal() {
    // If current BST is empty, return an empty tree
    if (isEmpty())
      return new ArrayList<K>();

    return inOderHelper(this.root);
  }

  /**
   * Helper method to arrange nodes with In-Order
   * 
   * @param node root for current tree or sub-tree
   * @return List of nodes with In-Order
   */
  private List<K> inOderHelper(BSTNode<K, V> node) {
    List<K> list = new ArrayList<K>();
    if (node == null) { // Base case
      return list;
    } else { // Recursion
      list.addAll(inOderHelper(node.left));
      list.add(node.key);
      list.addAll(inOderHelper(node.right));
      return list;
    }
  }

  /**
   * Method to traversal AVL with PreOrder
   * 
   * @return List contains all nodes with PreOrder
   */
  @Override
  public List<K> getPreOrderTraversal() {
    // If current BST is empty, return an empty tree
    if (isEmpty())
      return new ArrayList<K>();

    return preOrderHelper(this.root);
  }

  /**
   * Helper method to arrange nodes with PreOrder
   * 
   * @param node root for current tree or sub-tree
   * @return List of nodes with PreOrder
   */
  private List<K> preOrderHelper(BSTNode<K, V> node) {
    List<K> list = new ArrayList<K>();
    if (node == null) { // Base case
      return list;
    } else { // Recursion
      list.add(node.key);
      list.addAll(preOrderHelper(node.left));
      list.addAll(preOrderHelper(node.right));
      return list;
    }
  }

  /**
   * Method to traversal AVL with PostOrder
   * 
   * @return List contains all nodes with PostOrder
   */
  @Override
  public List<K> getPostOrderTraversal() {
    // If current BST is empty, return an empty tree
    if (isEmpty())
      return new ArrayList<K>();

    return postOrderHelper(this.root);
  }

  /**
   * Helper method to arrange nodes with PostOrder
   * 
   * @param node root for current tree or sub-tree
   * @return List of nodes with PostOrder
   */
  private List<K> postOrderHelper(BSTNode<K, V> node) {
    List<K> list = new ArrayList<K>();
    if (node == null) { // Base case
      return list;
    } else { // Recursion
      list.addAll(postOrderHelper(node.left));
      list.addAll(postOrderHelper(node.right));
      list.add(node.key);
      return list;
    }
  }

  /**
   * Method to traversal AVL with Level-Order
   * 
   * @return List contains all nodes with Level-Order
   */
  @Override
  public List<K> getLevelOrderTraversal() {
    // If current BST is empty, return an empty tree
    if (isEmpty())
      return new ArrayList<K>();

    int level = this.root.height;
    List<K> list = new ArrayList<K>();
    // Iterate AVL tree level by level and store keys of every level of nodes
    // into a list
    for (int i = 0; i < level; i++) {
      list.addAll(levelOrderHelper(this.root, i));
    }
    
    List<K> retList = new ArrayList<K>();
    
    for (K key: list) {
      if (key != null)
        retList.add(key);
    }
    return retList;
  }

  /**
   * Helper method to get a list storing all nodes in AVL with level-order
   * 
   * @return The list
   */
  private List<K> levelOrderHelper(BSTNode<K, V> node, int level) {
    List<K> list = new ArrayList<K>();
    if (node == null) {
      list.add(null);
      return list;
    }
    if (level == 0) {
      list.add(node.key);
    } else {
      list.addAll(levelOrderHelper(node.left, level - 1));
      list.addAll(levelOrderHelper(node.right, level - 1));
    }

    return list;
  }

  /**
   * Method to insert a node with key and value into AVL
   * 
   * @param key   Key of node to insert
   * @param value Value of node to insert
   */
  @Override
  public void insert(K key, V value)
      throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (this.contains(key))
      throw new DuplicateKeyException();

    this.root = insertHelper(key, value, this.root);
    resetHeight(); // Correct height for all nodes in AVL
  }

  /**
   * Helper method to insert a node maintain the balance of AVL
   * 
   * Key of node to insert
   * 
   * @param value Value of node to insert
   * @param node  Root of AVL or some sub-tree
   * @return the new root of AVL
   */
  public BSTNode<K, V> insertHelper(K key, V value, BSTNode<K, V> node) {
    // Base case. If current position is null, insert node here
    if (node == null) {
      // Size increases
      this.numKeys++;
      return new BSTNode<K, V>(key, value, null, null);
    } else if (node.key.compareTo(key) > 0) { // Recursions
      // If key is less than current node, go to the left side
      node.left = insertHelper(key, value, node.left);
    } else {
      // If key is greater than current node, go to the right side
      node.right = insertHelper(key, value, node.right);
    }

    // Then check balance
    int balanceFactor = getBalanceFactor(node);
    // If current root is not balanced, re-balance BST
    if (Math.abs(balanceFactor) >= 2)
      node = reBalance(node, balanceFactor);

    return node;
  }

  /**
   * Method to re-balance AVL or some sub-tree
   * 
   * @param node          Root for AVL or some sub-tree
   * @param balanceFactor root's balance factor
   * @return new root
   */
  private BSTNode<K, V> reBalance(BSTNode<K, V> node, int balanceFactor) {
    // If current node is left's height > right side
    if (balanceFactor >= 2) {
      // Integer to store node's balance factor
      int leftBF = getBalanceFactor(node.left);

      // Case 1: R - only needs a right rotation
      if (leftBF >= 0) {
        node = rightRotate(node);
      } else { // Case 2: LR - needs left and right rotation in order
        node.left = leftRotate(node.left);
        node = rightRotate(node);
      }
    } else if (balanceFactor <= -2) {
      int rightBF = getBalanceFactor(node.right);

      // Case 3: RL - needs right and left rotation in order
      if (rightBF >= 0) {
        node.right = rightRotate(node.right);
        node = leftRotate(node);
      } else { // Case 4: L - only needs a left rotation
        node = leftRotate(node);
      }
    }

    return node;
  }

  /**
   * Method to rotate a root node of AVL or some sub-tree to left
   * 
   * @param node Current root
   * @return New root
   */
  private BSTNode<K, V> leftRotate(BSTNode<K, V> node) {
    BSTNode<K, V> right = node.right; // Get root's right
    BSTNode<K, V> rightLeft = right.left; // Get left of root's right

    right.left = node; // Rotate root to left side of its right
    node.right = rightLeft; // Get root's right as left of root's right
                            // Now, the new root is old root's right, old root
                            // becomes new root's left,
                            // new root's original left becomes old root's right
    return right;
  }

  /**
   * Method to rotate a root node of AVL or some sub-tree to right
   * 
   * @param node Current root
   * @return New root
   */
  private BSTNode<K, V> rightRotate(BSTNode<K, V> node) {
    BSTNode<K, V> left = node.left; // Get root's left
    BSTNode<K, V> leftRight = left.right; // Get right of root's left

    left.right = node; // Rotate root to right side of its left
    node.left = leftRight; // Get root's left as right of root's left
                           // Now, the new root is old root's left, old root
                           // becomes new root's right,
                           // new root's original right becomes old root's left
    return left;
  }

  /**
   * Method to remove a node from AVL
   * 
   * @param key Key of the node to remove
   * @return true if remove the node successfully, false otherwise
   */
  @Override
  public boolean remove(K key)
      throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (!contains(key))
      throw new KeyNotFoundException();
    // If node is not null, call helper method, get new root and maintain height
    // of AVL
    if (getNode(key) != null) {
      this.root = removeHelper(this.root, key);
      resetHeight();
      return true;
    }

    return false;
  }

  /**
   * Helper method to find the in-order successor of current node
   * 
   * @param node root of AVL or some sub-tree
   * @return minimum of root
   */
  private BSTNode<K, V> inOrderSuccessor(BSTNode<K, V> node) {
    if (node.left == null)
      return node;

    return inOrderSuccessor(node.left);
  }

  /**
   * Helper method to remove a node with key value
   * 
   * @param node root of AVL or some sub-tree
   * @return new root of AVL
   */
  private BSTNode<K, V> removeHelper(BSTNode<K, V> node, K key) {
    // Base case
    if (node == null)
      return node;

    // Recursion. If key is less than current root's key, go left
    if (node.key.compareTo(key) > 0) {
      node.left = removeHelper(node.left, key);
    }
    // If key is greater than current root's key, go right
    else if (node.key.compareTo(key) < 0) {
      node.right = removeHelper(node.right, key);
    } else { // Otherwise, we found the node
      // Case 1: if current node/root has at most one child
      if ((node.left == null) || (node.right == null)) {
        BSTNode<K, V> temp = null;
        // Node may have right child, store it
        if (temp == node.left)
          temp = node.right;
        else // Otherwise, it may have left
          temp = node.left;

        // If temporary node is null, node has no child
        if (temp == null) {
          node = null; // Simply remove it
          this.numKeys--;
        } else { // Otherwise, node has one child, move node to its child
          node = temp;
          this.numKeys--;
        }
      } else { // Case 2: current node has two children
        // First, get its in-order successor, which is the smallest node bigger
        // than current root
        BSTNode<K, V> temp = inOrderSuccessor(node.right);

        // Copy the successor's data to current node
        node.key = temp.key;
        node.value = temp.value;
        // Delete the successor
        node.right = removeHelper(node.right, temp.key);
      }
    }

    // When current tree only has a root, return null
    if (node == null)
      return node;

    // Get current node's balance factor
    int balanceFactor = getBalanceFactor(node);
    // If current root is not balanced, re-balance BST
    if (Math.abs(balanceFactor) >= 2)
      node = reBalance(node, balanceFactor);

    return node;
  }

  /**
   * Method to get value of a node
   * 
   * @param key Node's key
   * @return value of a node
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (!contains(key))
      throw new KeyNotFoundException();

    return getNode(key).value;
  }

  /**
   * Method to check if a node is in AVL
   * 
   * @param key Node's key
   * @return true if the node is in, false otherwise
   */
  @Override
  public boolean contains(K key) throws IllegalNullKeyException {
    if (key == null)
      throw new IllegalNullKeyException();

    return getNode(key) != null;
  }

  /**
   * Method to get the amount of nodes currently in AVL
   * 
   * @return amount of nodes
   */
  @Override
  public int numKeys() {
    return this.numKeys;
  }

  /**
   * Method to display all nodes in AVL
   */
  @Override
  public void print() {
    // If list is empty, do not print
    if (this.isEmpty())
      return;

    // Update height
    resetHeight();

    int level = this.root.height; // Level for drawing the tree
    int mark = 0; // Parameter for calling level-order helper method

    // Iterate AVL to print each level by calling following helper method
    for (int i = level; i >= 0; i--) {
      printHelper(levelOrderHelper(this.root, mark), i);
      mark++;
    }
  }

  /**
   * Helper Method to print each level for AVL tree
   * 
   * @param list    List of Nodes at current level
   * @param Integer shows level Current level
   */
  private void printHelper(List<K> list, int level) {
    // First, count the spaces of first node to print
    double head = Math.pow(2, level - 1);
    for (int i = 0; i < head; i++) {
      System.out.print("  ");
    }

    // Iterate nodes in this level, if null, means no node at this position
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) == null) {
        System.out.print("  ");
      } else {
        System.out.print(list.get(i));
      }
      // Count the spaces between nodes
      if (level == 1) {
        System.out.print("  ");
      } else {
        int space = level * (level - 1) + 1;
        for (int j = 0; j < space; j++) {
          System.out.print("  ");
        }
      }
    }
    // New lines between each levels
    System.out.println();
    System.out.println();
  }

}
