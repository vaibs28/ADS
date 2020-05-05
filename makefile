JFLAGS = -g

JC = javac

default: hashtagcounter.class FibonacciHeap.class Node.class IFibonacciHeap.class

hashtagcounter.class: hashtagcounter.java
	$(JC) $(JFLAGS) hashtagcounter.java

FibonacciHeap.class: FibonacciHeap.java
	$(JC) $(JFLAGS) FibonacciHeap.java

Node.class: Node.java
	$(JC) $(JFLAGS) Node.java

IFibonacciHeap.class: IFibonacciHeap.java
	$(JC) $(JFlags) IFibonacciHeap.java


clean: 
	$(RM) *.class