import os
import sys
import math
import random
import operator

def main():
    input_path = str(sys.argv[1])
    file_name = input_path.split('.')
    output_path = file_name[0]+'.similarity'
    
    N = int(sys.argv[2])
    
    file_list = list()
    vector_list = list()
    
    # 1. Read file
    with open(input_path, 'r') as f:
        for line in f:
            words = line.strip('\n')
            words = words.strip('\t')
            words = words.split('\t')
            
            file_list.append(words[0])
            
            vector = list()
            for i in range(1, len(words)):
                vector.append(int(words[i]))
            vector_list.append(vector)
        f.close()

    #print (len(file_list))
    
    # 2. Get magnitude of vectors
    length_list = list()
    for i in range(len(vector_list)):
        magnitude_square = 0
        for k in range(len(vector_list[i])):
            magnitude_square += vector_list[i][k] * vector_list[i][k]
        magnitude_square = math.sqrt(magnitude_square)
        length_list.append(magnitude_square)
    
    # 3. Get Similarity
    result_list = list()
    for i in range(len(vector_list)):
        results = dict()
        for j in range(len(vector_list)):
            dot_product = getSimilarity(vector_list, length_list, i, j) #
            results[j] = dot_product
        result_list.append(results)

    
    # 4. Write file
    word_list = list()
    with open(output_path, 'w') as f:
        for i in range(len(result_list)):
            sorted_x = sorted(result_list[i].items(), key=operator.itemgetter(1))
            sorted_x.reverse()
            
            n = N
            string_out = file_list[i] + '\t'
            for k, v in sorted_x:
                if n <= 0:
                    break
                n -= 1
                
                string_each = str(file_list[k]) + ':' + str(v)[0:6]
                string_out += string_each + '\t'
                
            string_out += '\n'
            f.write(string_out)
        f.close()


def getSimilarity(vector_list, length_list, i, j):
    if len(vector_list[i]) != len(vector_list[j]):
        print ("Error!!! Vector lenght inconsistency.")
        exit()
    dot_product = 0
    for k in range(len(vector_list[j])):
        dot_product += vector_list[i][k] * vector_list[j][k];
    dot_product /= (length_list[i] * length_list[j])
    return dot_product


if __name__ == "__main__":
    main()
