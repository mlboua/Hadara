MACHINE
	Robot

SETS
	Etat = {libre,occupe}

VARIABLES
	De, Dt

INVARIANT
	De : Etat & Dt : Etat & 1=1

INITIALISATION
	De := libre || Dt := libre

OPERATIONS
	chargement =
		SELECT
			Dt = libre
		THEN
			Dt := occupe
		END;

	dechargement =
		SELECT
			Dt = occupe & De = libre
		THEN
			Dt := libre || De := occupe
		END;

	evacuation =
		SELECT
			De = occupe
		THEN
			De := libre
		END
END
