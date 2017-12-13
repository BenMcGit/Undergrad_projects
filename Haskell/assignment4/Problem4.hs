data Coord = Position Double Double | Vector Double Double | Scalar Double | Double Double deriving (Show)
instance Num Coord where
	(+) (Vector a b) (Vector x y) = Vector (x+a) (y+b)
	(-) (Vector a b) (Position x y) = (Position (x-a) (y-b))
	(*) (Position a b) (Position x y) = Double((a*x) + (b*y))
	negate (Position a b) = (Position (negate a) (negate b))
	abs (Position a b) = Scalar (sqrt((a*a) + (b*b)))
	signum _ = error "undefined"
	fromInteger _ = error "undefined"