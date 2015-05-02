output_index = 2;           % set for display;
output_bar = A(output_index,:);
sum_output_bar = sum(output_bar);
output_bar = output_bar ./ sum_output_bar;
bar(output_bar);