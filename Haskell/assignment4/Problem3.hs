data BinTree a = Empty | Branch (BinTree a) a (BinTree a) deriving (Show)

quicksort [] = []
quicksort (p:xs) = (quicksort (filter (< p) xs)) ++ [p] ++ (quicksort (filter (>= p) xs))

binTreeFromList [] = Empty
binTreeFromList xs = Branch	(binTreeFromList (take half (quicksort(xs))))
			((quicksort(xs)) !! half)(binTreeFromList (drop (half + 1) (quicksort(xs))))
	where 	half = (div)(length xs) 2

listFromBinTree :: BinTree a -> [a]
listFromBinTree Empty                  = []
listFromBinTree (Branch Empty a Empty) = a :[]
listFromBinTree (Branch left a right ) = listFromBinTree left ++ [a] ++ listFromBinTree right