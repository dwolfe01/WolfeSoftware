package gang.of.four.structural.design.patterns;

public class CompositePattern {

	abstract class Node{
	}
	
	class LeafNode extends Node{

		private String s;

		public LeafNode(String s) {
			this.s = s;
		}

		@Override
		public String toString() {
			return s;
		}
	}
	
	class CompositeNode extends Node{
		
		private Node leftNode;
		private Node rightNode;
		
		public CompositeNode(Node treeLeft, Node rightTree) {
			this.leftNode = treeLeft;
			this.rightNode = rightTree;
		}
		
		@Override
		public String toString() {
			return "Tree -> (Left node: " + leftNode.toString() + " Right node: " + rightNode.toString() + ")\n";
		}
	}
	
	public static void main(String[] args) {
		CompositePattern pop = new CompositePattern();
		CompositeNode tree = pop.new CompositeNode(pop.new LeafNode("Dan"), pop.new LeafNode("Wolfe"));
		System.out.println(tree.toString());
		CompositeNode tree2 = pop.new CompositeNode(tree, pop.new LeafNode("3G"));
		System.out.println(tree2.toString());
		CompositeNode tree3 = pop.new CompositeNode(tree, tree2);
		System.out.println(tree3.toString());
	}


}
