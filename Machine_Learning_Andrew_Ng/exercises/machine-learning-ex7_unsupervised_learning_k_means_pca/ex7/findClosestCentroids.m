function idx = findClosestCentroids(X, centroids)
%FINDCLOSESTCENTROIDS computes the centroid memberships for every example
%   idx = FINDCLOSESTCENTROIDS (X, centroids) returns the closest centroids
%   in idx for a dataset X where each row is a single example. idx = m x 1 
%   vector of centroid assignments (i.e. each entry in range [1..K])
%

% Set K
K = size(centroids, 1);

% You need to return the following variables correctly.
idx = zeros(size(X,1), 1);

% ====================== YOUR CODE HERE ======================
% Instructions: Go over every example, find its closest centroid, and store
%               the index inside idx at the appropriate location.
%               Concretely, idx(i) should contain the index of the centroid
%               closest to example i. Hence, it should be a value in the 
%               range 1..K
%
% Note: You can use a for-loop over the examples to compute this.
%

centroids

for i = 1:size(X, 1)
  % tanking only the row we are working with on this iteration
  x_tmp = X(i, 1:end);
  
  % minimizing ||x_tmp - centroid(i)||^2
  k_min = (centroids .- x_tmp) .^ 2;
  
  % aggregating (x_ymp_1 - centroid(i)_1)^2 + (x_ymp_2 - centroid(i)_2)^2 
  sm = sum(k_min')';
  
  % choosing k
  choose_centr = inf;
  for j = 1:K
    if (sm(j) < choose_centr)
      idx(i) = j;
      choose_centr = sm(j);
    end
  end
end




% =============================================================

end

