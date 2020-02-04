//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: BALSTTest.java
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

import static org.junit.Assert.fail;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;

// TODO: Add tests to test the tree is balanced or not

// @SuppressWarnings("rawtypes")
public class BALSTTest {

  BALST<String, String> balst1;
  BALST<Integer, String> balst2;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    balst1 = createInstance();
    balst2 = createInstance2();
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    balst1 = null;
    balst2 = null;
  }

  protected BALST<String, String> createInstance() {
    return new BALST<String, String>();
  }

  protected BALST<Integer, String> createInstance2() {
    return new BALST<Integer, String>();
  }

  /** 
   * Insert three values in sorted order and then check 
   * the root, left, and right keys to see if rebalancing 
   * occurred.
   */
  @Test
  void testBALST_001_insert_sorted_order_simple() {
      try {
          balst2.insert(10, "10");
          if (!balst2.getKeyAtRoot().equals(10)) 
              fail("avl insert at root does not work");
          
          balst2.insert(20, "20");
          if (!balst2.getKeyOfRightChildOf(10).equals(20)) 
              fail("avl insert to right child of root does not work");
          
          balst2.insert(30, "30");
          Integer k = balst2.getKeyAtRoot();
          if (!k.equals(20)) 
              fail("avl rotate does not work");
          
          // IF rebalancing is working,
          // the tree should have 20 at the root
          // and 10 as its left child and 30 as its right child

          Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
          Assert.assertEquals(balst2.getKeyOfLeftChildOf(20),new Integer(10));
          Assert.assertEquals(balst2.getKeyOfRightChildOf(20),new Integer(30));

          balst2.print();
          
      } catch (Exception e) {
          e.printStackTrace();
          fail( "Unexpected exception AVL 001: "+e.getMessage() );
      }
  }

  /**
   * Insert three values in reverse sorted order and then check the root, left,
   * and right keys to see if rebalancing occurred in the other direction.
   */
  @Test
  void testBALST_002_insert_reversed_sorted_order_simple() {

    try {
      balst2.insert(30, "30");
      if (!balst2.getKeyAtRoot().equals(30))
        fail("avl insert at root does not work");

      balst2.insert(20, "20");
      if (!balst2.getKeyOfLeftChildOf(30).equals(20))
        fail("avl insert to right child of root does not work");

      balst2.insert(10, "10");
      Integer k = balst2.getKeyAtRoot();
      if (!k.equals(20))
        fail("avl rotate does not work");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(30));
   
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 002: " + e.getMessage());
    }

  }

  /**
   * Insert three values so that a right-left rotation is needed to fix the
   * balance.
   * 
   * Example: 10-30-20
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred in
   * the other direction.
   */
  @Test
  void testBALST_003_insert_smallest_largest_middle_order_simple() {

    try {
      balst2.insert(10, "10");
      if (!balst2.getKeyAtRoot().equals(10))
        fail("avl insert at root does not work");

      balst2.insert(30, "30");
      if (!balst2.getKeyOfRightChildOf(10).equals(30))
        fail("avl rotate does not work");

      balst2.insert(20, "20");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(30));

      if (balst2.getHeight() != 2 || balst2.getKeyAtRoot() != 20)
        fail("root should be 20");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 003: " + e.getMessage());
    }

  }

  /**
   * Insert three values so that a left-right rotation is needed to fix the
   * balance.
   * 
   * Example: 30-10-20
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred in
   * the other direction.
   */
  @Test
  void testBALST_004_insert_largest_smallest_middle_order_simple() {
    try {

      balst2.insert(30, "30");
      if (!balst2.getKeyAtRoot().equals(30))
        fail("avl insert at root does not work");

      balst2.insert(10, "10");
      if (!balst2.getKeyOfLeftChildOf(30).equals(10))
        fail("avl rotate does not work");

      balst2.insert(20, "20");
      
      if (balst2.getHeight() != 2 || balst2.getKeyAtRoot() != 20)
        fail("root should be 20");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child
      Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(30));

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 004: " + e.getMessage());
    }

  }

  /**
   * Check if remove a leaf node successfully.
   */
  @Test
  void testBALST_005_remove_leaf_node() {
    try {

      balst2.insert(30, "30");
      if (!balst2.getKeyAtRoot().equals(30))
        fail("avl insert at root does not work");

      balst2.insert(10, "10");
      if (!balst2.getKeyOfLeftChildOf(30).equals(10))
        fail("avl rotate does not work");

      balst2.insert(20, "20");
      balst2.remove(20);
      if (balst2.contains(20)) {
        fail("avl does not remove leaf successfully");
      }

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 005: " + e.getMessage());
    }
  }
  
  /**
   * Check if contains method works
   */
  @Test
  void testBALST_006_check_contains_after_inserting() {
    try {
      balst2.insert(30, "30");
      if (!balst2.contains(30)) {
        fail("avl tree does not contain 30");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 006: " + e.getMessage());
    }
  }
  
  /**
   * Check if the size of AVL correct
   */
  @Test
  void testBALST_007_numKeys() {
    try {

      balst2.insert(30, "30");
      balst2.insert(20, "20");
      balst2.insert(10, "10");
      balst2.insert(5, "5");
      
      int size = balst2.numKeys();
     
      if (size != 4) {
        fail("The size should be 4, but it is" + size);
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 007: " + e.getMessage());
    }

  }
  
  /**
   * Check if we can insert multiple nodes
   */
  @Test
  void testBALST_008_insert_multiple() {
    try {

      balst2.insert(30, "30");
      balst2.insert(20, "20");
      balst2.insert(10, "10");
      balst2.insert(5, "5");
      balst2.insert(8, "8");

      if (!balst2.contains(8)) {
        fail("avl does not remove leaf successfully");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 008: " + e.getMessage());
    }
  }
  
  /**
   * Check if we can remove a root
   */
  @Test
  void testBALST_009_remove_root() {
    try {

      balst2.insert(30, "30");
      balst2.insert(20, "20");
      balst2.insert(10, "10");
      balst2.remove(20);

      if (balst2.contains(20)) {
        fail("avl does not remove single node successfully");
      }
      
      balst2.print();

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }
  
  /**
   * Check if we can remove multiple children
   */
  @Test
  void testBALST_010_remove_multiple_node() {
    try {

      balst2.insert(30, "30");
      balst2.insert(20, "20");
      balst2.insert(10, "10");
      balst2.insert(5, "5");
      balst2.insert(8, "8");

      balst2.remove(20);
      balst2.remove(5);
      
      if (balst2.contains(20) || balst2.contains(5)) {
        fail(balst2.getKeyAtRoot() + "avl does not remove nodes successfully");
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Check if we can insert a lot of children successfully
   */
  @Test
  void testBALST_011_insert_large_number_nodes() {
    try {
      balst2.insert(5, "5");
      balst2.insert(1, "1");
      balst2.insert(2, "2");
      balst2.insert(4, "4");
      balst2.insert(6, "6");
      balst2.insert(7, "7");
      balst2.insert(3, "3");
      balst2.insert(11, "11");
      balst2.insert(20, "20");
      balst2.insert(18, "18");
      
      if (balst2.numKeys() != 10) 
        fail("size is " + balst2.numKeys());

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }
  
  /**
   * Check if we can insert and remove 10 nodes
   */
  @Test
  void testBALST_012_insert_10_remove_10_contains() {
    try {
      balst2.insert(5, "5");
      balst2.insert(1, "1");
      balst2.insert(2, "2");
      balst2.insert(4, "4");
      balst2.insert(6, "6");
      balst2.insert(7, "7");
      balst2.insert(3, "3");
      balst2.insert(11, "11");
      balst2.insert(20, "20");
      balst2.insert(18, "18");

      balst2.remove(5);
      balst2.remove(1);
      balst2.remove(2);
      balst2.remove(4);
      balst2.remove(6);
      balst2.remove(7);
      balst2.remove(3);
      balst2.remove(11);
      balst2.remove(20);
      balst2.remove(18);
      
      if (balst2.contains(5))
        fail("contians 5"); 
      if (balst2.numKeys() != 0)
        fail("size should be 0 but it is " + balst2.numKeys());  
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }
  
  /**
   * Check if the order traversal method works fine
   */
  @Test
  void testBALST_013_in_order_check() {
    try {
      balst2.insert(50, "5");
      balst2.insert(10, "1");
      balst2.insert(20, "2");
      balst2.insert(40, "4");
      balst2.insert(60, "6");
      balst2.insert(70, "7");
      balst2.insert(30, "3");
      balst2.insert(80, "11");
      balst2.insert(90, "20");
      
      balst2.print();
      
      System.out.println(balst2.getInOrderTraversal());
      System.out.println(balst2.getPreOrderTraversal());
      System.out.println(balst2.getPostOrderTraversal());
      System.out.println(balst2.getLevelOrderTraversal());  
      
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }
  
}
