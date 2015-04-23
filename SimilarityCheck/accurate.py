import os
import sys
import math
import random
import operator

def main(argv):
    file=open(argv[0])
    total=0
    correct=0
    line=file.readline()
    while line:
        splited=line.split('\t')
        key=splited[0]
        ans=splited[2]
        total+=1
        newAns=ans.split('_')
        newKey=key.split('_')
        #print(ans,key)
        #print(newAns[1],newKey[1])
        if newAns[1]==newKey[1]:
            correct+=1
        line=file.readline()
    print(correct/total)



if __name__ == "__main__":
    main(sys.argv[1:])
