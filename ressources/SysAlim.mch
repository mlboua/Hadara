MACHINE SysAlim

SETS
	Etat_Batterie = {1ok1, 1ko1} ;
	Etat_Horloge = {1tic1, 1tac1}

VARIABLES 
	Bat, Int, H

INVARIANT
	Bat : 1..3 --> Etat_Batterie &
	Int : 1..3 & H : Etat_Horloge &
	Bat(Int) = 1ok1 &
	(Bat(1) = 1ok1 or Bat(2) = 1ok1 or Bat(3) = 1ok1)

INITIALISATION
	Bat(1) := 1ok1 || Bat(2) := 1ok1 || Bat(3) := 1ok1 || H := 1tac1 ||
	Int := 1 

/* $op* -> ( card(Bat|>{1ok1}) <= 1 ) . $op -> ( card(Bat|>{1ok1}) > 1 ) . Arrivee_Tic . Commute */
/* $op* -> ( card(Bat|>{1ok1}) <= 1 ) . Reparer_Panne */

OPERATIONS
	Arrivee_Tic = 
		SELECT 
			H = 1tac1 
		THEN 
			H := 1tic1 
		END;

	Commute =
		SELECT 
			((Bat(1) = 1ok1 & Bat(2) = 1ok1) or
			(Bat(1) = 1ok1 & Bat(3) = 1ok1) or
			(Bat(2) = 1ok1 & Bat(3) = 1ok1))
			& H=1tic1 
		THEN 
			ANY 
				ni 
			WHERE 
				ni : 1..3 & ni/=Int & Bat(ni)=1ok1 
			THEN 
				Int := ni || 
				H := 1tac1
			END
		END ;

	Panne_Batterie =
		SELECT 
			((Bat(1) = 1ok1 & Bat(2) = 1ok1) or
			(Bat(1) = 1ok1 & Bat(3) = 1ok1) or
			(Bat(2) = 1ok1 & Bat(3) = 1ok1))
		THEN
			ANY 
				nb 
			WHERE 
				nb : 1..3 & Bat(nb) =1ok1 
			THEN 
				Bat(nb) := 1ko1 ||
				IF 
					nb = Int 
				THEN
					ANY 
						ni 
					WHERE 
						ni : 1..3 & ni /= Int & Bat(ni)=1ok1
					THEN 
						Int := ni
					END
				END
			  END
		END ;

	Reparer_Panne =
		ANY 
			nb 
		WHERE 
			nb : 1..3 & Bat(nb) = 1ko1 
		THEN
			Bat(nb) := 1ok1
		END
END
