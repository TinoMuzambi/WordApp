BINDIR=bin
SRCDIR=src
DOCDIR=doc

all:
	javac -d $(BINDIR)/ src/*.java

clean:
	rm -f ${BINDIR}/*.class

docs:
	javadoc  -classpath ${BINDIR} -d ${DOCDIR} ${SRCDIR}/*.java

cleandocs:
	rm -rf ${DOCDIR}/*
