rle [] = []
rle [x] = [(x,1)]
rle (x:xs) = func (xs) x [] 1 

func (x:xs) y zs l = 
	if (xs == [])
	then ret x xs y zs l
	else if (x == y)
	then func xs y zs (l+1)
	else func xs x (zs ++ [(y,(l))]) 1

ret x xs y zs l =
	if x == y
	then zs ++ [(x,l+1)]
	else ((zs ++ [(y,l)]) ++ [(x,1)])
	
	
rld [] = []
rld [(x,c)] = ret2 x c []
rld ((x,c):xs)	=  func2 x c xs []

func2 x c ((y,z):xs) zs = 
	if (length xs == 0)
	then (ret1 x c y z zs)
	else if c == 0
	then func2 y z xs zs
	else func2 x (c-1) ((y,z):xs) (zs ++ [x])
	
ret1 x c y z zs = 
	if c == 0
	then ret2 y z (zs)
	else ret1 x (c-1) y z (zs ++ [x])

ret2 x c zs = 
	if c == 0
	then (zs)
	else ret2 x (c-1) (zs ++ [x])	
	