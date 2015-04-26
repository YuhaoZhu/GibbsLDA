% This example code is a Neural Network for Machine Learning course.
% Written by Chunyang Xia @ UB in Nov 2013.
% 

% Step 0
% Set up the training matrix with feature 0 = 1 for bias.
% Set up parameters (weight_1 & weight_2) for two layers.

train_feature_nn = horzcat(ones(train_length,1), train_feature);
train_target_nn = train_target;
hidden_layer_number = 151;

weight_1 = 2 * rand(151, hidden_layer_number-1) - 1;
weight_2 = 2 * rand(hidden_layer_number, 150) - 1;

% Variable for loop control
times = 0;
a = 0;
countdown = 5;

iter_num = 500;
first = 1;
last = iter_num;
learning_rate = 1;
u1 = 1;
u2 = 4;

a1 = train_feature_nn * weight_1;
z1 = horzcat(ones(size(a1,1),1), tanh(a1/u1));
a2 = z1 * weight_2;
exp_a2 = exp(a2/u2);
sum_exp_a2 = sum(exp_a2, 2);
sum_exp_a2 = repmat(sum_exp_a2, 1, 150);
z2 = exp_a2 ./ sum_exp_a2;
e2 = (train_target_nn - z2);
    
e=find(abs(e2)>0.5);
fprintf('Iteration %d: <Training Error Rate: %d/total>\n',a, int32(length(e)/2));

while a < 100 && countdown > 0
    while times < 1
        while last <= train_length && first <= train_length
            % step 1
            a1 = train_feature_nn(first:last,:) * weight_1;
            z1 = horzcat(ones(size(a1,1),1), tanh(a1/u1));
            
            a2 = z1 * weight_2;
            exp_a2 = exp(a2/u2);
            sum_exp_a2 = sum(exp_a2, 2);
            sum_exp_a2 = repmat(sum_exp_a2, 1, 150);
            z2 = exp_a2 ./ sum_exp_a2;
            
            % step 2
            e2 = (train_target_nn(first:last,:) - z2)/u2;
            delta_weight_2 = z1' * e2 / iter_num;
            
            e1 = (1 - z1.*z1) .* (e2 * weight_2')/u1;
            delta_weight_1 = train_feature_nn(first:last,:)'*e1(:,2:hidden_layer_number)/iter_num;
            
            weight_2 = weight_2 + learning_rate * delta_weight_2;
            weight_1 = weight_1 + learning_rate * delta_weight_1;
            
            first = last + 1;
            last = last + iter_num;
            if last > train_length
                last = train_length;
            end
        end
        first = 1;
        last = iter_num;
        times = times + 1;
    end
    times = 0;
    a = a + 1;
    
    % calculate error as e
    a1 = train_feature_nn * weight_1;
    z1 = horzcat(ones(size(a1,1),1), tanh(a1/u1));
    a2 = z1 * weight_2;
    exp_a2 = exp(a2/u2);
    sum_exp_a2 = sum(exp_a2, 2);
    sum_exp_a2 = repmat(sum_exp_a2, 1, 150);
    z2 = exp_a2 ./ sum_exp_a2;
    e2 = (train_target_nn - z2);
    
    e=find(abs(e2)>0.5);
    fprintf('Iteration %d: <Training Error Rate: %d/total>\n',a, int32(length(e)/2));
    
    if length(e) < 200
        countdown = countdown - 1;
    end
    dlmwrite(strcat(question_name,'_nn.txt'), z2);
end