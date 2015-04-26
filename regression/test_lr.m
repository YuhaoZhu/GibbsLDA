test_hidden_lr = exp(horzcat(ones(1500,1),test_feature) * para_weight_lr);
sum_hidden_lr = sum(test_hidden_lr, 2);
sum_hidden_lr = repmat(sum_hidden_lr, 1, 10);
test_y_lr = test_hidden_lr ./ sum_hidden_lr;

[A, Predict_lr] = max(test_y_lr');

e = find(Predict_lr' == test_target+1);

fprintf('Test Set Correct Rate: %2.2f%%\n', length(e)/15);

dlmwrite('classes_lr.txt', Predict_lr'-1, 'delimiter', ' ', 'newline', 'pc');