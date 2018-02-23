%.class: %.java
	javac $^

.PHONY: run
run: all index.html
	appletviewer file:index.html

.PHONY: all
all: Fractal.class

.PHONY: clean
clean:
	rm *.class
