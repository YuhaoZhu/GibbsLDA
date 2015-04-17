# python3 topNSimilarity.py question.result answer.result N K

import os
import sys
import math
import random
import operator

def main():
    question_path = str(sys.argv[1])
    answer_path = str(sys.argv[2])
    file_name = question_path.split('.')
    output_path = file_name[0]+'.similarity'
    
    N = int(sys.argv[3])        # Num of top N similar docs to print out
    K = int(sys.argv[4])        # Num of top K values in each vector for cosine
    
    question_list = list()
    answer_list = list()
    question_vector_list = list()
    answer_vector_list = list()
    
    # 1. Read file
    with open(question_path, 'r') as f:
        for line in f:
            words = line.strip('\n')
            words = words.strip('\t')
            words = words.split('\t')
            
            question_list.append(words[0])
            
            vector = list()
            for i in range(1, len(words)):
                vector.append(float(words[i]))
            question_vector_list.append(vector)
        f.close()
    
    with open(answer_path, 'r') as f:
        for line in f:
            words = line.strip('\n')
            words = words.strip('\t')
            words = words.split('\t')
            
            answer_list.append(words[0])
            
            vector = list()
            for i in range(1, len(words)):
                vector.append(float(words[i]))
            answer_vector_list.append(vector)
        f.close()

    # 2.0 Reduce low value in vector to 0
    reduceLowValueInVector(question_vector_list, K)  # Disable this for K = numOfTopic
    reduceLowValueInVector(answer_vector_list, K)  # Disable this for K = numOfTopic
    
    # 2.1 Get magnitude of vectors
    question_length_list = list()
    for i in range(len(question_vector_list)):
        magnitude_square = 0
        for k in range(len(question_vector_list[i])):
            magnitude_square += question_vector_list[i][k] * question_vector_list[i][k]
        magnitude_square = math.sqrt(magnitude_square)
        question_length_list.append(magnitude_square)
    
    # 2.2 Get magnitude of vectors
    answer_length_list = list()
    for i in range(len(answer_vector_list)):
        magnitude_square = 0
        for k in range(len(answer_vector_list[i])):
            magnitude_square += answer_vector_list[i][k] * answer_vector_list[i][k]
        magnitude_square = math.sqrt(magnitude_square)
        answer_length_list.append(magnitude_square)
    
    # 3. Get Similarity
    string_out_list = list()
    for i in range(0,10):#(len(question_vector_list)):    # for i in range(0,10):
        print (i)
        results = dict()
        for j in range(len(answer_vector_list)):
            dot_product = getSimilarity(question_vector_list, answer_vector_list, question_length_list, answer_length_list, i, j)
            results[j] = dot_product
        #result_list.append(results)
        sorted_x = sorted(results.items(), key=operator.itemgetter(1))
        sorted_x.reverse()
            
        n = N
        string_out = question_list[i] + '\t'
        for k, v in sorted_x:
            if n <= 0:
                break
            n -= 1
                
            string_each = str(answer_list[k]) + ':' + str(v)[0:6]
            string_out += string_each + '\t'
                
        string_out += '\n'
        string_out_list.append(string_out)
    
    # 4. Write file
    word_list = list()
    with open(output_path, 'w') as f:
        for i in range(len(string_out_list)):
            f.write(string_out_list[i])
        f.close()


def getSimilarity(question_vector_list, answer_vector_list, question_length_list, answer_length_list, i, j):
    if len(question_vector_list[i]) != len(answer_vector_list[j]):
        print ("Error!!! Vector lenght inconsistency.")
        exit()
    dot_product = 0
    for k in range(len(question_vector_list[i])):
        dot_product += question_vector_list[i][k] * answer_vector_list[j][k];
    dot_product /= (question_length_list[i] * answer_length_list[j])
    return dot_product


def reduceLowValueInVector(vector_list, K):
    for i in range(len(vector_list)):
        vector_temp = dict()
        for k in range(len(vector_list[i])):
            vector_temp[k] = vector_list[i][k]
        
        sorted_x = sorted(vector_temp.items(), key=operator.itemgetter(1))
        sorted_x.reverse()
        
        n = 0
        for k, v in sorted_x:
            if n < K:
                continue;
            n += 1
            vector_list[i][k] = 0.0
        
        
if __name__ == "__main__":
    main()
