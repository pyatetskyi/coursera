function g = sigmoid(z)
%SIGMOID Compute sigmoid functoon
%   J = SIGMOID(z) computes the sigmoid of z.

% You need to return the following variables correctly 
g = zeros(size(z));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the sigmoid of each value of z (z can be a matrix,
%               vector or scalar).

%size(z)
E = ones(size(z)) .* e;
%g = (E .^ -z)
%g = ones(size(z)) .+ (E .^ -z)
g = ones(size(z)) ./ (ones(size(z)) .+ (E .^ -z));



% =============================================================

end
