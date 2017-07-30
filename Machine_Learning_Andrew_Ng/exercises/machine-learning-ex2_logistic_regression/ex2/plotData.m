function plotData(X, y)
%PLOTDATA Plots the data points X and y into a new figure 
%   PLOTDATA(x,y) plots the data points with + for the positive examples
%   and o for the negative examples. X is assumed to be a Mx2 matrix.

% Create New Figure
figure; hold on;

% ====================== YOUR CODE HERE ======================
% Instructions: Plot the positive and negative examples on a
%               2D plot, using the option 'k+' for the positive
%               examples and 'ko' for the negative examples.
%

%X(1:10,:)

%y(1:10)

% Find Indices of Positive and Negative Examples
pos = find(y == 1);
neg = find(y == 0);
% Plot Examples
plot(X(pos, 1), X(pos, 2), 'k+', 'LineWidth', 2, 'MarkerSize', 7);
plot(X(neg, 1), X(neg, 2), 'ko', 'MarkerFaceColor', 'y', 'MarkerSize', 7);

%plot(X(:,1), X(:,2), 'rx', 'MarkerSize',  10);
%plot(X[:,2], y, 'rx', 'MarkerSize',  10);
%xlabel('Exam 1 score'); % Set the x..axis label
%ylabel('Exam 2 score'); % Set the y..axis label







% =========================================================================



hold off;

end
