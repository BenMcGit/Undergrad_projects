isperson(X) :- member(X,[tom,elliot,vic,bill]).

lefttoright(elliot,bill).
lefttoright(bill,X) :- isperson(X).
lefttoright(vic,X) :- isperson(X).

righttoleft(X) :- isperson(X).

crossings([lefttoright(_,_),righttoleft(_),lefttoright(_,_),righttoleft(_),lefttoright(_,_)]).

whopaddledtwice(W) :- 	crossings(Crossings),
						isperson(X),
						isperson(Y),
						isperson(Z),
						isperson(W),
						X \= Y,
						X \= Z,
						X \= W,
						Y \= Z,
						Y \= W,
						Z \= W,
						member(lefttoright(X, Y), Crossings),
						member(righttoleft(Y), Crossings),
						member(lefttoright(X, W), Crossings),
						member(righttoleft(W), Crossings),
						member(lefttoright(W, Z), Crossings),
						print_places(Crossings).
						
print_places([]).
print_places([A | B]) :- write(A), nl, print_places(B).


%isperson
%make sure people arent the same
%mamber with vars