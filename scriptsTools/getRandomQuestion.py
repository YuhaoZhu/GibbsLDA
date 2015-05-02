import os
import sys
import math
import random,shutil
import operator

def main(argv):
    documentPath=argv[0]
    targetPath=argv[1]
    numFiles=int(argv[2])
    fileList=os.listdir(documentPath)
    random.shuffle(fileList)
    
    for fileName in fileList[:numFiles]:
        shutil.copy(documentPath+fileName,targetPath)

if __name__ == "__main__":
    main(sys.argv[1:])
