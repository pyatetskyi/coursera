function p = predict(Theta1, Theta2, X)
%PREDICT Predict the label of an input given a trained neural network
%   p = PREDICT(Theta1, Theta2, X) outputs the predicted label of X given the
%   trained weights of a neural network (Theta1, Theta2)

% Useful values
m = size(X, 1);
num_labels = size(Theta2, 1);

% You need to return the following variables correctly 
p = zeros(size(X, 1), 1);

% ====================== YOUR CODE HERE ======================
% Instructions: Complete the following code to make predictions using
%               your learned neural network. You should set p to a 
%               vector containing labels between 1 to num_labels.
%
% Hint: The max function might come in useful. In particular, the max
%       function can also return the index of the max element, for more
%       information see 'help max'. If your examples are in rows, then, you
%       can use max(A, [], 2) to obtain the max for each row.
%

X = [ones(m, 1) X];

fprintf('\nX is matrix: %d x %d\n', size(X, 1), size(X, 2));
fprintf('Theta1 is matrix: %d x %d\n', size(Theta1, 1), ...
    size(Theta1, 2));
fprintf('Theta2 is matrix: %d x %d\n', size(Theta2, 1), ...
    size(Theta2, 2));
    
 a2 = sigmoid(X * Theta1');
 fprintf('a2 is matrix: %d x %d\n', size(a2, 1), ...
    size(a2, 2));
    
 a2 = [ones(size(a2, 1), 1) a2];
fprintf('a2 is matrix: %d x %d\n', size(a2, 1), ...
    size(a2, 2));
    
 
 a3 = sigmoid(a2 * Theta2');
fprintf('a3 is matrix: %d x %d\n', size(a3, 1), ...
    size(a3, 2));

p = a3;

[p, k] = max(a3, [], 2);

fprintf('p is matrix: %d x %d\n', size(p, 1), ...
    size(p, 2));
fprintf('k is matrix: %d x %d\n', size(k, 1), ...
    size(k, 2));
 
p = k;

% =========================================================================


end
