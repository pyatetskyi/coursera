function [J grad] = nnCostFunction(nn_params, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, ...
                                   X, y, lambda)
%NNCOSTFUNCTION Implements the neural network cost function for a two layer
%neural network which performs classification
%   [J grad] = NNCOSTFUNCTON(nn_params, hidden_layer_size, num_labels, ...
%   X, y, lambda) computes the cost and gradient of the neural network. The
%   parameters for the neural network are "unrolled" into the vector
%   nn_params and need to be converted back into the weight matrices. 
% 
%   The returned parameter grad should be a "unrolled" vector of the
%   partial derivatives of the neural network.
%

% Reshape nn_params back into the parameters Theta1 and Theta2, the weight matrices
% for our 2 layer neural network
Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));

Theta2 = reshape(nn_params((1 + (hidden_layer_size * (input_layer_size + 1))):end), ...
                 num_labels, (hidden_layer_size + 1));

% Setup some useful variables
m = size(X, 1);
         
% You need to return the following variables correctly 
J = 0;
Theta1_grad = zeros(size(Theta1));
Theta2_grad = zeros(size(Theta2));

% ====================== YOUR CODE HERE ======================
% Instructions: You should complete the code by working through the
%               following parts.
%
% Part 1: Feedforward the neural network and return the cost in the
%         variable J. After implementing Part 1, you can verify that your
%         cost function computation is correct by verifying the cost
%         computed in ex4.m
%
% Part 2: Implement the backpropagation algorithm to compute the gradients
%         Theta1_grad and Theta2_grad. You should return the partial derivatives of
%         the cost function with respect to Theta1 and Theta2 in Theta1_grad and
%         Theta2_grad, respectively. After implementing Part 2, you can check
%         that your implementation is correct by running checkNNGradients
%
%         Note: The vector y passed into the function is a vector of labels
%               containing values from 1..K. You need to map this vector into a 
%               binary vector of 1's and 0's to be used with the neural network
%               cost function.
%
%         Hint: We recommend implementing backpropagation using a for-loop
%               over the training examples if you are implementing it for the 
%               first time.
%
% Part 3: Implement regularization with the cost function and gradients.
%
%         Hint: You can implement this around the code for
%               backpropagation. That is, you can compute the gradients for
%               the regularization separately and then add them to Theta1_grad
%               and Theta2_grad from Part 2.
%

% recode y to Y
%I = eye(num_labels);
%Y = zeros(m, num_labels);
%for i=1:m
%  Y(i, :)= I(y(i), :);
%end

Y = zeros(m, num_labels);
%Y(:, 1) = (y == 10);

for c = 1:num_labels
  Y(:, c) = (y == c);
end

a1 = [ones(m,1) X];
z2 = a1 * Theta1';
a2 = [ones(size(z2, 1), 1) sigmoid(z2)];
z3 = a2 * Theta2';
a3 = sigmoid(z3);
%H_matrix = a3;
h = a3;

J = sum(sum((-Y).*log(h) - (1-Y).*log(1-h), 2))/m + ...
      lambda/(2*m)*(sum(sum((Theta1(:, 2:end) .^2)')) ...
      + sum(sum((Theta2(:, 2:end) .^2)')));

%for c = 1:num_labels
%  y_k = y_matrix(:, c);
%  y_k_1 = y_1 = 1 - y_k;
%  H_k = H_matrix(:, c);
%  pt1 = (-1 .* y_k) .* log(H_k);
 % pt2 = (-1 .* y_k_1) .* (log(1 - H_k));

%  J = J + sum(pt1 .+ pt2);
%end

%J = J/m;

%J = sum(sum((-y_matrix).*log(H_matrix) - (1-y_matrix).*log(1-H_matrix), 2))/m;

%fprintf('matrix a3 size is: %dx%d\n', size(a3, 1), size(a3, 2));
%fprintf('matrix a2 size is: %dx%d\n', size(a2, 1), size(a2, 2));
%fprintf('matrix z2 size is: %dx%d\n', size(z2, 1), size(z2, 2));

delta_3 = a3 .- Y;

g_x = sigmoidGradient([ones(size(z2, 1), 1) z2]);

delta_2 = (delta_3 * Theta2) .* g_x;
delta_2 = delta_2(:, 2:end);



%fprintf('matrix g_x size is: %dx%d\n', size(g_x, 1), size(g_x, 2));
%fprintf('matrix delta_3 size is: %dx%d\n', size(delta_3, 1), size(delta_3, 2));
%fprintf('matrix delta_2 size is: %dx%d\n', size(delta_2, 1), size(delta_2, 2));
%for i=1:m

D2 = delta_3' * a2;
D1 = delta_2' * a1;

%fprintf('matrix D2 size is: %dx%d\n', size(D2, 1), size(D2, 2));
%fprintf('matrix D1 size is: %dx%d\n', size(D1, 1), size(D1, 2));

theta1_corrected = [zeros(size(D1, 1), 1) Theta1(:, 2:end)];
theta2_corrected = [zeros(size(D2, 1), 1) Theta2(:, 2:end)]; 

Theta1_grad = D1./m + (lambda/m) * theta1_corrected;
Theta2_grad = D2./m + (lambda/m) * theta2_corrected;











% -------------------------------------------------------------

% =========================================================================

% Unroll gradients
grad = [Theta1_grad(:) ; Theta2_grad(:)];


end
