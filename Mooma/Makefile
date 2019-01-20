#!/usr/bin/make -f
# -*- mode:makefile -*-

DIRBIN := bin/
DIRLIB := lib/
DIRSRC := src/

GRAMMAR_NAME := mooma.g4

JVM := java

ifeq ($(OS),Windows_NT)
    PATH_SEPARATOR := ;
else
    PATH_SEPARATOR := :
endif

BUILD_CLASSPATH := -cp "$(DIRLIB)*"

all: folders compile

install:
	pip3 install -r $(DIRSRC)requirements.txt
	
compile: 
	$(JVM) $(BUILD_CLASSPATH) org.antlr.v4.Tool -Dlanguage=Python3 $(DIRSRC)$(GRAMMAR_NAME) -o $(DIRBIN)
	cp $(DIRSRC)*.py $(DIRBIN)

folders:
	mkdir -p $(DIRBIN)

clean: 	
	rm -rf $(DIRBIN)

.PHONY: all, install, folders, compile, clean