pair [] [] = []
pair xs [] = []
pair [] ys = []
pair (x:xs) [y] = [(x,y)]
pair (xs) (ys) = func xs ys []

func (x:xs) (y:ys)  zs =
		if (length xs) == 0 || (length ys) == 0 
		then zs ++ [(x,y)]
		else  func xs ys (zs ++ [(x,y)] )