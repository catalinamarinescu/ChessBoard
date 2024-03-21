JAVAC = javac
JAR = jar
JAVACFLAGS = -d bin

SRCDIRS = commands logic moves
SRCS := $(wildcard $(addsuffix /*.java, $(SRCDIRS)))
CLASSES := $(patsubst %.java, bin/%.class, $(SRCS))
PRGM = Main.jar
MANIFEST = META-INF/MANIFEST.MF

.PHONY: all clean run build

build: $(PRGM)

all: $(PRGM)

$(PRGM): $(CLASSES) $(MANIFEST)
	$(JAR) cfm $(PRGM) $(MANIFEST) -C bin .

$(MANIFEST):
	mkdir -p META-INF
	echo "Main-Class: logic.Main" > $(MANIFEST)

bin/%.class: %.java
	$(JAVAC) $(JAVACFLAGS) $<

run: $(PRGM)
	java -jar $(PRGM)

clean:
	rm -rf bin
	rm -f $(PRGM)
	rm -rf META-INF