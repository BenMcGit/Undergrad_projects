fib n = func!!n
func = 0 : 1 : zipWith (+) func (tail func)

