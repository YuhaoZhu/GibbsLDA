import os
import sys
import math
import random
import operator

def main(argv):
    file=open(argv[0])
    line=file.readline()
    out=open("newOutput","w")
    while line:
        splited=line.split('\t')
        out.write(splited[0])
        for i in range(1,len(splited)-1):
            out.write("\t"+splited[i].split()[0]);
        out.write('\n')
        line=file.readline()
    out.close()
    file.close()


if __name__ == "__main__":
    main(sys.argv[1:])
