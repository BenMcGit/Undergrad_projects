perfect x = 
	if foldl (+) 0 (filter (\n -> x `mod` n == 0)([1..x])) == (+) x x
	then True
	else False
	
mullist [] = []
mullist (x:xs) = 
	if (x == 1)
	then [1] ++ (filter (\n -> (madefrom n xs)) [2..])
	else filter (\n -> (madefrom n (x:xs))) [2..]

--madefrom 1 (1:xs) = True
madefrom n [] = (n == 1)
madefrom n (x:xs) = 
		if( (n `mod` x) == 0 ) 
		then madefrom (div n x) (x:xs)
		else (madefrom n xs)