import java.util.Iterator;
import java.util.List;

/**
 * 
 * Grupo 12
 * Ana Albuquerque, 53512
 * Carolina Duque, 51087
 * 
 */
public class BinZZTree<Key extends Comparable<Key>, Value> implements ZZTree <Key, Value> {

	private Node root;
	//************************************************************************

	// node data type
	private class Node {
		private Key key;            // key
		private Value value;        // associated data
		private Node left;          // left and right subtrees
		private Node right;

		public Node(Key key, Value value) {
			this.key = key;
			this.value = value;
		}

		// @ref https://stackoverflow.com/questions/4965335
		// @requires source code saved in UTF-8 encoding
		@Override
		public String toString() {
			return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
		}

		private StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
			if(right!=null) {
				right.toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
			}
			sb.append(prefix).append(isTail ? "└── " : "┌── ").append(value.toString()).append("\n");
			if(left!=null) {
				left.toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
			}
			return sb;
		}

	}

	//************************************************************************

	// default constructor builds an empty tree
	public BinZZTree() {}

	// @requires keys.length == values.length
	public BinZZTree(List<Key> keys, List<Value> values) {

		Iterator<Key>   ks = keys  .iterator();
		Iterator<Value> vs = values.iterator();

		while (ks.hasNext() && vs.hasNext())
			put(ks.next(), vs.next());
	}

	//************************************************************************
	/**
	 * Checks if the tree is empty
	 * @return true iff the tree is empty
	 * @worst-case O(1) 
	 */
	public boolean isEmpty() {
		return this.root == null;
	}

	//************************************************************************

	/** 
	 * This method checks if there is a node with a certain key
	 * @worst-case O(log(n))
	 * @param key The key to search
	 * @return true iff the given key exists
	 */
	public boolean contains(Key key) {
		return get(key) != null;
	}

	//************************************************************************

	/**
	 * Get the value associated with certain key
	 * @worst-case O(log(n))
	 * @param key The key to get the associated value
	 * @return the value associated to the key
	 * 			otherwise (if key does not exist) null
	 */
	public Value get(Key key) {
		root = bubbleUp(key, root); 
		return get(root, key);
	}

	/**
	 * Auxiliary method for get(Key key)
	 */
	private Value get(Node node, Key key) {
		if (node == null) {
			return null;
		}
		int compare = key.compareTo(node.key);

		if(compare < 0) {
			return get(node.left, key);
		}
		else if (compare > 0) {
			return get(node.right, key);
		}
		else {
			return node.value;
		}
	}

	//************************************************************************

	/**
	 * This method adds a new node to the tree if it
	 * does not already contain a node with that key.
	 * If the tree already contains a node with the key, 
	 * it replaces only the value
	 * @worst-case O(log(n))
	 * @param key - key of the new node
	 * @param value - value of new node
	 */
	public void put(Key key, Value value) {
		root = put(root, key, value);
		root = bubbleUp(key, root);
	}

	/**
	 * Auxiliary method for put(Node node, Key key, Value value)
	 */
	private Node put(Node node, Key key, Value value) {

		//se o elem nao existe:
		if(node == null) { 
			//cria-se um novo nó que ficara na raiz:
			return new Node(key, value); 
		}

		//e temos reedistribuir as sub-arvores de modo a manter a invariante:

		int compare = key.compareTo(node.key);

		if(compare < 0) {
			node.left = put(node.left, key, value);
		} else if(compare > 0) {
			node.right = put(node.right, key, value);
		} 

		//se o elem existe:
		else { 
			//basta substituir o valor:
			node.value = value; 
		}
		return node;
	}

	//************************************************************************

	/**
	 * This method removes a node with the key in the tree
	 * @worst-case O(log(n))
	 * @param key - the key of the node to remove
	 */
	public void remove(Key key) {
		root = remove(root, key);
		root = bubbleUp(key, root);
	}

	/**
	 * Auxiliary method for remove(Node node, Key key)
	 */
	private Node remove(Node node, Key key) {
		if(node == null) {
			return null;
		}

		int cmp = key.compareTo(node.key);
		if(cmp < 0) {
			node.left = remove(node.left,  key);
		}
		else if(cmp > 0) {
			node.right = remove(node.right, key);
		}
		else { 
			if(node.right == null) {
				return node.left;
			}
			if(node.left  == null) {
				return node.right;
			}
			Node t = node;
			node = min(t.right);
			node.right = removeMin(t.right);
			node.left = t.left;
		} 
		return node;
	} 

	/**
	 * Auxiliary method for remove(Node node, Key key)
	 */
	private Node removeMin(Node node) {
		if(node.left == null) return node.right;
		node.left = removeMin(node.left);
		return node;
	}

	/**
	 * Auxiliary method for remove(Node node, Key key)
	 */
	private Node min(Node node) { 
		if (node.left == null) {
			return node; 
		}
		else {
			return min(node.left); 
		}
	} 

	//************************************************************************

	/**
	 * @return The tree in a string
	 * @worst-case O(n)
	 */
	public String toString() {
		try {
			return root.toString();
		} catch (NullPointerException e) {
			return "A arvore estah vazia!";
		}
	}

	//************************************************************************

	/**
	 * This method calculates the height of the tree
	 * @worst-case O(n)
	 * @return height of the tree
	 */
	public int height() { 
		return height(this.root);
	}

	/**
	 * Auxiliary method for height()
	 */
	private int height(Node node) {
		if(size(node) == 0)
			return 0;
		return 1 + Math.max(height(node.left), height(node.right));
	}

	//************************************************************************

	/** 
	 * This method calculates the size of the tree 
	 * @worst-case O(n)
	 * @return The number of nodes in the BST
	 */
	public int size() {
		return size(root);
	}

	/**
	 * Auxiliary method for size()
	 */
	private int size(Node node) {
		if(node == null) return 0;
		else return 1 + size(node.left) + size(node.right);
	}

	//************************************************************************

	/**
	 * @worst-case O(1)
	 * @return the key at tree's root
	 */
	public Key rootKey() {
		if(!isEmpty()) {
			return this.root.key;	
		}
		return null;
	}


	//************************************************************************

	/**
	 * This method checks if the tree is a BST
	 * @worst-case O(n)
	 * @return true iff the tree is a BST
	 */
	public boolean keepsInvariant() { 
		return keepsInvariant(root, null, null); 
	} 

	/**
	 * Auxiliary method for keepsInvariant()
	 */
	private boolean keepsInvariant(Node node, Key min, Key max) {
		if (node == null) {
			return true;
		}

		if (min != null && node.key.compareTo(min) <= 0) {
			return false;
		}

		if (max != null && node.key.compareTo(max) >= 0) {
			return false;
		}

		return keepsInvariant(node.left, min, node.key) 
				&& keepsInvariant(node.right, node.key, max);
	} 


	//************************************************************************

	/**
	 * This method makes the necessary rotations so that the node to which
	 * the method is calling goes up in the tree and stays in the root, if
	 * it exists.
	 * Otherwise put the smallest node following it in the root
	 * @worst-case O(log(n))
	 * @param key
	 * @param node
	 * @return the node to put in the root
	 */
	private Node bubbleUp(Key key, Node node) {
		if(node == null) {
			return node;
		}
		if(key.compareTo(node.key) == 0) {
			return node ;
		}

		if(key.compareTo(node.key) < 0) {

			node.left = bubbleUp(key,node.left);
			if(node.left != null) {
				return rotateRight(node);
			}
			return node;
		}else {
			node.right = bubbleUp(key,node.right);
			if(node.right != null) {
				return rotateLeft(node);
			}
			return node;
		}
	}

	/**
	 * Auxiliary method for bubbleUp(Key key, Node node)
	 */
	private Node rotateRight(Node r) {
		Node l=r.left;
		r.left=l.right;
		l.right=r;
		return l;
	}

	/**
	 * Auxiliary method for bubbleUp(Key key, Node node)
	 */
	private Node rotateLeft(Node l) {
		Node r = l.right;
		l.right=r.left;
		r.left=l;
		return r;
	}
}