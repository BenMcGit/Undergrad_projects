mergeSort([],[]):- !.
mergeSort([X],[X]):- !.
mergeSort(L,Sorted):- 
	div(L,W,Z), 
	mergeSort(W,Sorted1),
	mergeSort(Z,Sorted2),
	merge(Sorted1,Sorted2,Sorted),
	!.
merge([],L,L).
merge(L,[],L).
merge([X|T1],[Y|T2],[X|T]):-X=<Y,merge(T1,[Y|T2],T).
merge([X|T1],[Y|T2],[Y|T]):-X>Y,merge([X|T1],T2,T).

div(L, A, B) :-	
    append(A, B, L),
    length(A, N),
    length(B, N).
	
div(L, A, B) :-	
    append(A, B, L),
    length(A, N),
	N2 is N+1,
    length(B, N2).