MACHINE
	robot

SETS
	Etat = {1,2}

VARIABLES
	De, Dt

INVARIANT
	De : Etat & Dt : Etat & 1=1

INITIALISATION
	De := 1 || Dt := 1
	

OPERATIONS
	chargement = 
		SELECT
			Dt = 1
		THEN
			Dt := 2
		END;

	dechargement =
		SELECT
			Dt = 2 & De = 1
		THEN
			Dt := 1 || De := 2 || Dc := 3
		END;

	evacuation =
		SELECT
			De = 2
		THEN
			De := 1
		END
END
