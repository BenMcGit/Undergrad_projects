myMaxRE [x] = x
myMaxRE (x:xs) = 
		if x >= myMaxRE xs 
		then x
		else myMaxRE xs

myMaxFU (x:xs) = foldr high x xs

high x y =
		if x > y 
		then x
		else y

mySumRE [] = 0
mySumRE [x] = x
mySumRE (x:xs) = (+) x (mySumRE xs)

mySumFU xs = foldl (+) 0 xs 