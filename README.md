---

<img width="500" alt="Screenshot 2022-08-08 at 13 17 00" src="https://user-images.githubusercontent.com/72214330/183416036-8378c373-0ad7-45e8-bf8b-0469ab309146.png">

---

### Table of Contents
1. [Introduction](#introduction)
2. [The tree type](#the-tree-type)
3. [What to do](#what-to-do)
4. [About the search requirement](#about-the-search-requirement)
5. [About inserting and deleting elements](#about-inserting-and-deleting-elements)
6. [Authors](#authors)

---

## Introduction

In most cases, access to information stored in a data structure is not random. It is normal that the recently requested item is requested again. To this end, we will create a data structure motivated by this search requirement, that is, that the most recently entered items should be accessed faster.

A ```zzTree``` is a binary search tree where the operations satisfy this search requirement.

Let's look at an example in which the number 9 is entered: 

![Screenshot 2022-08-08 at 12 38 33](https://user-images.githubusercontent.com/72214330/183409708-45df6f82-e9df-4938-92a5-722d4cf50578.png)

The number 9 was left at the root, so that it can be accessed faster in a future operation.

---

## The tree type

The following interface shows the public services that we want to include in our implementation:

![Screenshot 2022-08-08 at 12 43 42](https://user-images.githubusercontent.com/72214330/183410541-0550892f-0fa2-43f1-b2dc-32e0e62b8f8e.png)

Read the javadoc of the ```ZZTree.java``` file for more information about the above operations.
The generic type **Key** must implement the type **Comparable**. That is, our trees only store keys of types that implement a total order. Examples of comparable types include **Integer** and **String**.

Each node of the tree must store two pieces of information: a key (unique) and an associated value. If a new pair (key,value) is inserted where the key already exists, only the value must be replaced. In the figures in the statement, for simplicity, we are only visualizing the keys. In these examples, the key ́e of type **Integer**, but it can be of another type as long as it is **Comparable**.

The invariant that all zzTree trees must satisfy: at the beginning and end of each operation the zzTree must remain binary search trees. The **keepsInvariant()** method is used to validate the correctness of your code.

## What to do

You are asked to implement this interface in the ```BinZZZTree``` class. You are given a skeleton of the ```BinZZTree``` class to fill in. Respect the method signatures provided. You can, of course, include any helper methods that you feel are appropriate.
Include informative comments and, in the javadoc of the various methods, the respective worst-case time complexities for a tree with n elements. Use this format in the javadoc:

![Screenshot 2022-08-08 at 12 54 36](https://user-images.githubusercontent.com/72214330/183412300-345223a9-e933-4707-b692-a5ace8319c81.png)

## About the search requirement

The search requirement will be satisfied as follows: we update the root of the tree to place the appropriate element there. Usually this will be the element under consideration. For example, if we do a **put(k,v)** or a **get(k)** where the key k exists, the pair **(k,v)** must ultimately be in the tree root.

Other times, if the element does not exist, we put the last searched element at the root. This happens when we remove/search for a key that does not exist (for example, in the **contains** or the **get** method).

An example: from the previous tree, the left figure is the result of searching for key 11 (an element that exists in the tree), and the right figure is the result of searching for key 6 (an element that does not exist, with 5 being the last element visited).

![Screenshot 2022-08-08 at 12 58 38](https://user-images.githubusercontent.com/72214330/183412914-d8996c41-9b1f-43ea-aa5a-03fa66a51e5e.png)

It is possible to think of different ways to implement this requirement. Let us attack the problem as follows. Let be a m ́method named **bubbleUp(Node node, Key key key)** that performs this task (**node** is the node that corresponds to the raız of the (sub-)tree).

Consider the root node (in the next figure, root) and the child node (**son**) that belongs to the search path of the element.

![Screenshot 2022-08-08 at 13 01 14](https://user-images.githubusercontent.com/72214330/183413366-a95f3be3-a1bb-410f-8f20-f480a9eb1906.png)

If the element has not yet been found, we recursively call **bubbleUp(ni, key)** on the node **ni** from which we continue the search. After this execution, by recursion, we know that in this node ni is either the element we want, or the last searched element. Either way, we want to pull ni to the root. For this to happen, we need to perform two rotations in the tree. For example, if we had, in the figure above, done **bubbleUp** to node **n2**, we would then have to do a left rotation on the child to pull n2 up (next left figure) and then a right rotation on the root to pull it to the root (right figure).

![Screenshot 2022-08-08 at 13 04 47](https://user-images.githubusercontent.com/72214330/183413915-8f4d6480-9244-4aea-bf18-2e6693684707.png)

In the diagram, the roots of the subtrees of **n2** are denoted **n2**left and **n2**right.

## About inserting and deleting elements

To insert an element, you first do a **bubbleUp** of that element. If it exists, it will be in the root and you just replace the value. If it doesn't exist, you create a new node that will be in the root. What remains is to redistribute the subtrees in order to maintain the invariant.

To remove an element, you first do a **bubbleUp** of that element. If it exists, it will be in the root and we have to delete that node, just redistribute the existing subtrees in order to maintain the invariant. If it doesn't exist, we leave the tree as it is.

---

## Authors

* **Ana Albuquerque** - [GitHub](https://github.com/albuana)

---

* **Grade:** 19,4/20

---

