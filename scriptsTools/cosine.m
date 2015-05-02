% This program is for cosine similarity calculation
% top_k_in_vector:  passed in parameter from outer program
% Below parameters are for configering of input files:
% K_dims:   Length of vector
% start:    Vector values start from
% base_str: Number format in vectors

top_k_in_vector = 10;

K_dims = 10;
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

fileID = fopen('lda.result','r');
C = textscan(fileID,format_str);
fclose(fileID);


[temp, width] = size(C);
[height,temp] = size(C{start});
A = zeros(height, width - start + 1);
B = zeros(height, width - start + 1);

for i = start : width
    A(:,i-1) = C{i};
end

if top_k_in_vector < K_dims
    for i = 1:height
        [xsorted, is] = sort(A(i,:),'descend');
        thredhold = (xsorted(top_k_in_vector) + xsorted(top_k_in_vector+1))/2;
        small_value_in_vector = find(A(i,:) < thredhold);
        A(i,small_value_in_vector) = 0;
    end
end

Magnitude = zeros(height ,1);
for i = 1:height
    Magnitude(i,1) = norm(A(i,:));
end
zero_Magnitude_index = find(Magnitude==0);
for i  = 1:length(zero_Magnitude_index)
    Magnitude(zero_Magnitude_index(i, 1), 1) = 1;
end

for i = 1:height
    A(i,:) = A(i,:) / Magnitude(i,1);
end

height = 1000;
N = 20;
sorted_output_index = zeros(height, N);
sorted_output_value = zeros(height, N);

%each_block = 3000;
%count = floor(height / each_block);
%if count * each_block  == height
%    count = count + 1;
%end

A_t = A';
%for i = 1:count%height
%    left = each_block * (i-1) + 1;
%    right = each_block * i;
%    if right > height
%        right = height;
%    end
%    Cosine_array_i = A(left:right,:) * A_t;
%    for j = left:right
%        [xsorted, is] = sort(Cosine_array_i(j,:), 2, 'descend');
%        sorted_output_index(j,:) = is(1:N);
%        sorted_output_value(j,:) = xsorted(1:N);
%    end
%end

for i = 1:height
    Cosine_array_i = A(i,:) * A_t;
    [xsorted, is] = sort(Cosine_array_i, 'descend');
    sorted_output_index(i,:) = is(1:N);
    sorted_output_value(i,:) = xsorted(1:N);
end

%dlmwrite('cos_index.txt',sorted_output_index);
%dlmwrite('cos_value.txt',sorted_output_value);