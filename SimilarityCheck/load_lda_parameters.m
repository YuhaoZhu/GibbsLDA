% 

K_dims = 10;
start = 2;
base_str = '%f32 ';
format_str = '%s ';
for i = 1 : K_dims
    format_str = horzcat(format_str,base_str);
end

fileID = fopen('lda.result','r');
C = textscan(fileID, format_str);
fclose(fileID);


[temp, width] = size(C);
[height,temp] = size(C{start});
A = zeros(height, width - start + 1);

for i = start : width
    A(:,i-1) = C{i};
end


