edge(a,b).
edge(b,c).

%edge(c,a).
edge(a,c).

edge(b,d).
edge(c,d).

path(X,Y,Z) :- find(X,Y,[X],Z).

find(X,X,_,[X]).
find(X,Y,Seen,[X|Zs]) :- edge(X,P),
						not(member(P,Seen)),
						find(P,Y,[P|Seen],Zs).