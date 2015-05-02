% This program is for cosine similarity calculation
% top_k_in_vector:  passed in parameter from outer program
% Below parameters are for configering of input files:
% K_dims:   Length of vector
% start:    Vector values start from
% base_str: Number format in vectors

clear;

question_name = 'AnswerLDA_35K.result';
answer_name = 'AnswerLDA_35K.result';

top_k_in_vector = 10;       % pass throught outside

str_k = num2str(top_k_in_vector);

K_dims = 150;
start = 2;
base_str = '%f32 ';

if start == 2
    format_str = '%s ';
elseif start == 1
    format_str = '';
else
    exit(0)
end
for i = 1 : K_dims
    format_str = horzcat(format_str,base_str);
end

% Question
fileID = fopen(question_name,'r');
C = textscan(fileID,format_str);
fclose(fileID);

[temp, width_q] = size(C);
[height_q,temp] = size(C{start});
Q = zeros(height_q, width_q - start + 1);

for i = start : width_q
    Q(:,i-1) = C{i};
end

if top_k_in_vector < K_dims
    for i = 1:height_q
        [xsorted, is] = sort(Q(i,:),'descend');
        thredhold = (xsorted(top_k_in_vector) + xsorted(top_k_in_vector+1))/2;
        small_value_in_vector = find(Q(i,:) < thredhold);
        Q(i,small_value_in_vector) = 0;
    end
end

Magnitude = zeros(height_q ,1);
for i = 1:height_q
    Magnitude(i,1) = norm(Q(i,:));
end
zero_Magnitude_index = find(Magnitude==0);
for i  = 1:length(zero_Magnitude_index)
    Magnitude(zero_Magnitude_index(i, 1), 1) = 1;
end

for i = 1:height_q
    Q(i,:) = Q(i,:) / Magnitude(i,1);
end

% Answer
fileID = fopen(answer_name,'r');
D = textscan(fileID,format_str);
fclose(fileID);

[temp, width_a] = size(D);
[height_a,temp] = size(D{start});
A = zeros(height_a, width_a - start + 1);

for i = start : width_a
    A(:,i-1) = D{i};
end

if top_k_in_vector < K_dims
    for i = 1:height_a
        [xsorted, is] = sort(A(i,:),'descend');
        thredhold = (xsorted(top_k_in_vector) + xsorted(top_k_in_vector+1))/2;
        small_value_in_vector = find(A(i,:) < thredhold);
        A(i,small_value_in_vector) = 0;
    end
end

Magnitude = zeros(height_a ,1);
for i = 1:height_a
    Magnitude(i,1) = norm(A(i,:));
end
zero_Magnitude_index = find(Magnitude==0);
for i  = 1:length(zero_Magnitude_index)
    Magnitude(zero_Magnitude_index(i, 1), 1) = 1;
end

for i = 1:height_a
    A(i,:) = A(i,:) / Magnitude(i,1);
end

%height_q = 1000;
N = 20;
sorted_output_index = zeros(height_q, N);
sorted_output_value = zeros(height_q, N);

each_block = 1000;
count = floor(height_q / each_block);
if count * each_block  < height_q
    count = count + 1;
end


train_feature = Q;
train_target = A;
train_length = height_q;

train_nn

%A_t = A';
%for i = 1:count%height
%    left = each_block * (i-1) + 1;
%    right = each_block * i;
%    if right > height_q
%        right = height_q;
%    end
%    Cosine_array_i = Q(left:right,:) * A_t;
    
%    [xsorted, is] = sort(Cosine_array_i(:,:), 2, 'descend');
%    sorted_output_index(left:right,:) = is(:,1:N);
%    sorted_output_value(left:right,:) = xsorted(:,1:N);
%    disp(horzcat('Processed # of ', num2str(right), ' inputs'));
%end

%for i = 1:height_q
%    Cosine_array_i = Q(i,:) * A_t;
%    [xsorted, is] = sort(Cosine_array_i, 'descend');
%    sorted_output_index(i,:) = is(1:N);
%    sorted_output_value(i,:) = xsorted(1:N);
%end

%dlmwrite(strcat(question_name,'_cos_index_',str_k,'.txt'),sorted_output_index);
%dlmwrite(strcat(question_name,'_cos_value_',str_k,'.txt'),sorted_output_value);