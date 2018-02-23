flags = -g -Xlint:all

.PHONY: run debug
run: Contains.class
	java Contains

debug: Contains.class
	jdb Contains

%.class: %.java
	javac $(flags) $^
