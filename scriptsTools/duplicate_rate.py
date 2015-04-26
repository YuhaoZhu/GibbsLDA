import os
import sys
import math
import random
import operator

def main():
    question_path = str(sys.argv[1])
    #file_name = question_path.split('.')
    #output_path = file_name[0]+'.similarity'
    
    #answer_path = str(sys.argv[2])
    
    #cosine_value_path = str(sys.argv[3])
    #cosine_index_path = str(sys.argv[4])
    
    file_list_q = list()
    #file_list_a = list()
    
    # 1.1 Read question file to generate index of docs' names
    #file_list_q.append(' ')
    with open(question_path, 'r') as f:
        for line in f:
            words = line.strip('\n')
            words = words.strip('\t')
            words = words.split('\t')
            word_id = (words[0]).split('_')
            
            file_list_q.append(word_id[1])
        f.close()
    
    name_dict = dict()
    #print (file_list_q)
    for i in range(len(file_list_q)):
        name_dict[file_list_q[i]] = 0
    
    print (len(name_dict))


if __name__ == "__main__":
    main()
