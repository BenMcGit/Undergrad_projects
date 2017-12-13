sentence(S,S0,sentence(NP,VP)) :- noun_phrase(S,S1,NP),verb_phrase(S1,S0,VP).
noun_phrase(NP,NP0,noun_phrase(D,N)) :- determiner(NP,NP1,D),noun(NP1,NP0,N).
verb_phrase(VP,VP0,verb_phrase(D,V)) :- verb(VP,VP1,D), noun_phrase(VP1,VP0,V).
determiner([a|Rest],Rest,determiner(a)).
determiner([at|Rest],Rest,determiner(at)).
determiner([the|Rest],Rest,determiner(the)).
noun([man|Rest],Rest,noun(man)).
noun([cake|Rest],Rest,noun(cake)).
verb([ate|Rest],Rest,verb(ate)).