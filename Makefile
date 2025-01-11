.PHONY: clean all compile install test
all: install

compile:
	mvn -U clean compile
install:
	mvn -U clean install
test:
	mvn test
clean:
	mvn -U clean