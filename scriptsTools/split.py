import os
import sys
import math
import random
import operator

def main():
    input_path = str(sys.argv[1])
    output=open(input_path+'.learn','w')
    output2=open(input_path+'.test','w')
    inputfile=open(input_path)
    line=inputfile.readline()

    count=1
    while line:
        if count>30000:
            output2.write(line)
        else:
            output.write(line)
        count+=1
        line=inputfile.readline()


if __name__ == "__main__":
    main()
