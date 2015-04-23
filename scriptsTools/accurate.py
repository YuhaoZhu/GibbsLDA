import os
import sys
import math
import random
import operator

def main(argv):
    file=open(argv[0])
    total=0
    TopOneCorrect=0
    TopNCorrect=0
    TopNCorrectwithBase=0
    TopNCorrectFindOne=0
    linesize=0
    line=file.readline()
    while line:
        splited=line.split('\t')
        linesize=len(splited)-1
        key=splited[0]
        ans=splited[2]
        total+=1
        newAns=ans.split('_')
        newKey=key.split('_')
        #print(ans,key)
        #print(newAns[1],newKey[1])
        if newAns[1]==newKey[1]:
            TopOneCorrect+=1
    
        addOne=0
        for i in range(2,len(splited)-1):
            key=splited[0]
            ans=splited[i]
            newAns=ans.split('_')
            newKey=key.split('_')
            
            if newAns[1]==newKey[1]:
                TopNCorrect+=1
                addOne=1
                    #if addOne==0:
                    #print(line)
        TopNCorrectFindOne+=addOne
        for i in range(1,len(splited)-1):
            key=splited[0]
            ans=splited[i]
            newAns=ans.split('_')
            newKey=key.split('_')
            if newAns[1]==newKey[1]:
                TopNCorrectwithBase+=1
        
        line=file.readline()
    linesize-=1
    print("Top One accuracy: "+str(TopNCorrectFindOne/total))
    print("Top N findOne accuracy: "+str(TopOneCorrect/total))
    print("Top N accuracy with no base: "+str(TopNCorrect/total/(linesize-1)))
    print("Top N accuracy with base: "+str(TopNCorrectwithBase/total/linesize))




if __name__ == "__main__":
    main(sys.argv[1:])
