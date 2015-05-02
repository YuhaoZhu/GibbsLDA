clear;
K_array = [10];%, 20, 30, 40];
%K_array = [3, 4];%, 5, 6, 7, 8, 9];
question_name = 'LDA11000.result';
answer_name = 'LDA11000.result';

for i = 1:length(K_array)
    clearvars -except K_array question_name answer_name i;
    top_k_in_vector = K_array(i);
    disp(strcat('Doing #',num2str(top_k_in_vector),' iteration:'));
    cosine_ques_to_ans;
end