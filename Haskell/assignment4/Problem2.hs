
type Word = String
type Line = [Word]
type Page = [Line]
type Doc = [Page]

numWords [] = 0
numWords xs = calcPage xs
calcPage [] = 0
calcPage (c:cp) = calcLine c + calcPage cp 
calcLine [] = 0
calcLine (c:cl) = calcWords c + calcLine cl
calcWords cw = length cw