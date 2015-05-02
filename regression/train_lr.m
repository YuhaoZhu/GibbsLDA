% Step0
train_feature_lr = horzcat(ones(train_length,1), train_feature);
train_target_lr = train_target;
para_weight_lr = rand(151, 150);

times = 0;
a = 0;
learning_rate = 10;
countdown = 2;

while a < 20 && countdown > 0
    while times < 50
        % Step 1
        train_hidden_lr = exp(train_feature_lr * para_weight_lr);
        sum_hidden_lr = sum(train_hidden_lr, 2);
        sum_hidden_lr = repmat(sum_hidden_lr, 1, 150);
        train_y_lr = train_hidden_lr ./ sum_hidden_lr;

        % Step 2
        train_error_lr = train_y_lr - train_target_lr;
        para_error_lr = train_feature_lr' * train_error_lr / train_length;
        para_weight_lr = para_weight_lr - learning_rate * para_error_lr;
        times = times + 1;
    end
    times = 0;
    a = a + 1;
    
    % each 50 iterations, we calculate error rate.
    e = find(abs(train_error_lr) > 0.5);
    fprintf('Iteration %d: <Training Error Rate: %d/total>\n', a*50, int32(length(e)/2));
    
    % if error rate is less than 100/19978,
    % just (2*50=)100 more iteration and stop
    if length(e) < 200
        countdown = countdown - 1;   % countdown:2->1->0
    end
    dlmwrite(strcat(question_name,'_lr.txt'), train_y_lr);
end
