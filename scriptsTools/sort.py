import os
import sys
import math
import random
import operator

def main():
    input_path = str(sys.argv[1])
    output=open(input_path+'.sorted','w')
    inputfile=open(input_path)
    line=inputfile.readline()

    filename=[]
    filedic={}
    while line:
        splited=line.split('\t')
        filedic[splited[0]]=line
        line=inputfile.readline()
        filename.append(splited[0])
    
    filename.sort()
    for file in filename:
        output.write(filedic[file])


if __name__ == "__main__":
    main()
