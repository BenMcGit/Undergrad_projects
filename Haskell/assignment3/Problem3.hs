double [] x = []
double xs y = func xs y []

func (x:xs) y zs =
	if x == y
	then (((zs ++ [x]) ++[y]) ++ xs)
	else func (xs) y (zs ++ [x])