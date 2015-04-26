# python3 matlab_sorted_cos_recovery.py lda.result cos_value.txt cos_index.txt

import os
import sys
import math
import random
import operator

def main():
    input_path = str(sys.argv[1])
    file_name = input_path.split('.')
    output_path = file_name[0]+'.similarity'
    
    cosine_value_path = str(sys.argv[2])
    cosine_index_path = str(sys.argv[3])
    
    file_list = list()
    
    # 1. Read file to generate index of docs' names
    file_list.append(' ')
    with open(input_path, 'r') as f:
        for line in f:
            words = line.strip('\n')
            words = words.strip('\t')
            words = words.split('\t')
            
            file_list.append(words[0])
        f.close()

    
    # 2. Read sorted cosine value for all documents
    cosine_value_list = list()
    cosine_value_list.append(' ')
    with open(cosine_value_path, 'r') as f:
        for line in f:
            words = line.strip('\n')
            words = words.strip(',')
            words = words.split(',')
            
            vector = list()
            for i in range(0, len(words)):
                vector.append(words[i])
            cosine_value_list.append(vector)
        f.close()
    

    # 3. Read sorted cosine indexes for all documents
    cosine_index_list = list()
    cosine_index_list.append(' ')
    with open(cosine_index_path, 'r') as f:
        for line in f:
            words = line.strip('\n')
            words = words.strip(',')
            words = words.split(',')
            
            vector = list()
            for i in range(0, len(words)):
                vector.append(int(words[i]))
            cosine_index_list.append(vector)
        f.close()
    
    
    # 4. Write file with decoded file names
    word_list = list()
    with open(output_path, 'w') as f:
        for i in range(1, len(cosine_index_list)):
            string_out = file_list[i] + '\t'
            for j in range(len(cosine_index_list[i])):
                string_out += file_list[cosine_index_list[i][j]] + ':' \
                        + cosine_value_list[i][j][0:6] + '\t'
            string_out += '\n'
            f.write(string_out)
        f.close()


if __name__ == "__main__":
    main()
