foo = map.(*)

bar f g xs = filter f $ map g xs

--the function named 'bar' is not possible to convert to point free style.
--Since f is a boolean function and its in between filter and map there is no way to 
--direct this variable to the right side of the list. It has to remain between the filter
--and map function to be used properly.