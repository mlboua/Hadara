MACHINE 
	QuiDonc

SETS 
	Entree={1bad_num, 1ex_num, 1nonex_num, 1sp_num, 1un, 1deux, 1etoile} ;
     Sortie={1welcome, 1enter, 1bye, 1error, 1sorry, 1name, 1addr, 1spell, 1info, 1urgency, 1prohibit_entry, 1hang_up} ;
     State={1bienvenue, 1entrer_num, 1trouve_urgence, 1trouve_num, 1raccroche, 1occupe} ;
     EtCombine={1pose1, 1tenu1}



VARIABLES 
	Combine, NbEssai, Etat
INVARIANT 
	Combine : EtCombine & NbEssai : 0..2 &
	  Etat : State &
	  ((Etat=1raccroche) <=> (Combine=1pose1)) &
	  (Etat=1raccroche => NbEssai=0)

INITIALISATION 
	Combine := 1pose1 || NbEssai := 0 ||
	Etat := 1raccroche


OPERATIONS
  Appel_Qui_Donc = 
    PRE Combine=1pose1
    THEN Combine := 1tenu1 || r := 1welcome || Etat := 1bienvenue
    END ;

 Raccrocher = 
    PRE ((Combine=1tenu1) or (Etat=1occupe))
    THEN Etat := 1raccroche || r := 1hang_up ||
	 Combine := 1pose1 || NbEssai := 0
    END ;

 Delai_Ecoule = 
    SELECT Combine=1tenu1 & Etat/=1occupe THEN
      IF Etat=1trouve_urgence THEN Etat := 1occupe || r := 1bye
      ELSE IF NbEssai=2 THEN r := 1bye || Etat := 1occupe
	   ELSE NbEssai := NbEssai+1 ||
		IF Etat=1bienvenue THEN r := 1welcome
		ELSE IF Etat=1entrer_num THEN r := 1enter
		     ELSE r := 1info END
		END
	   END
      END
    END ;

Entrer(e) =
    PRE 
	Combine=1tenu1 & Etat/=1occupe & Etat/=1raccroche & e:Entree
    THEN
        NbEssai := 0 ||
	SELECT Etat=1bienvenue THEN
	  IF e=1etoile THEN Etat := 1entrer_num || r := 1enter
	  ELSE r := 1prohibit_entry
	  END
	WHEN Etat=1entrer_num THEN
	  IF e=1sp_num THEN Etat := 1trouve_urgence || r := 1urgency
	  ELSE IF e=1ex_num THEN Etat := 1trouve_num || r := 1name
	       ELSE IF e=1bad_num THEN r := 1error
		    ELSE IF e=1nonex_num THEN r := 1sorry
			 ELSE r := 1prohibit_entry
			 END
		    END
	       END
	  END
	WHEN Etat=1trouve_urgence THEN
	  IF e=1etoile THEN Etat := 1entrer_num || r := 1enter
	  ELSE r := 1prohibit_entry END
	WHEN Etat=1trouve_num THEN
	  IF e=1etoile THEN Etat := 1entrer_num || r := 1enter
	  ELSE IF e=1un THEN r := 1spell
	       ELSE IF e=1deux THEN r := 1addr
		    ELSE r := 1prohibit_entry
		    END
	       END
	  END
	END
    END
END
