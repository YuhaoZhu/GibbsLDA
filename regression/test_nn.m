u1 = 6;
u2 = 4;

a1 = horzcat(ones(1500,1),test_feature) * weight_1;
z1 = horzcat(ones(size(a1,1),1), tanh(a1/u1));

a2 = z1 * weight_2;
exp_a2 = exp(a2/u2);
sum_exp_a2 = sum(exp_a2, 2);
sum_exp_a2 = repmat(sum_exp_a2, 1, 10);
test_y_nn = exp_a2 ./ sum_exp_a2;

[A, Predict_nn] = max(test_y_nn');

e = find(Predict_nn' == test_target + 1);

fprintf('Test Set Correct Rate: %2.2f%%\n', length(e)/15);

dlmwrite('classes_nn.txt', Predict_lr'-1, 'delimiter', ' ', 'newline', 'pc');
