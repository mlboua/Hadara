MACHINE
	ESSUYAGE_AV

CONSTANTS
	
	VIT1,
	VIT2,
	MAX_VITESSE

SETS
	S_DMD_COMMODO_USER = {1repos1, 1interm1, 1pv1, 1gv1, 1auto1}; 
	
	S_DMD_COMMODO = {1d_repos1, 1d_interm1, 1d_pv1, 1d_gv1, 1d_auto1}; 
	
	ETAT_COMMODO = {1defaillant1, 1fonctionnel1}; 
	
	TYPE_VITESSE = {1fascendant1, 1descendant1}; 
	
	ALIMENTATION = {1accessoire1, 1demarreur1, 1aucune1};
	
	
	ETAT_MOTEUR_ESS_AV = {1mgv1, 1mpv1, 1arretFixeAv1, 1suspendu1, 1arretMaintenance1,1mInter1,1pvcycle1, 
		1gv3cycles1, 1protectionMoteur1, 1pv3cyclesLavage1, 1gv3cyclesLavage1};
	
	CDP = {1pvCDP1, 1gvCDP1, 1arretCDP1};
	ETAT_CDP = {1c_defaillant1,1c_fonctionnel1};
	
	BOOLEAN = {false, true};
	T_INT_AV = {1T_INT_AV_TRES_LENTE1, 1T_INT_AV_LENTE1, 1T_INT_AV_MOYENNE1, 11T_INT_AV_RAPIDE11, 
		1T_INT_FIXE1, 1T_REDUC_PV_INT1}
	
	
DEFINITIONS

	VITESSE == 0..MAX_VITESSE	
	
PROPERTIES

	VIT1 = 5		&
	VIT2 = 10		&
	MAX_VITESSE = 100	
	
VARIABLES
	dmd_commodo_user,
	dmd_commodo,
	etat_commodo,
	type_vitesse,
	vitesse_vehicule,
	alimentation,
	etat_moteur_ess_av,
	cdp,
	etat_cdp,
	t_int_av,
	depassement_temps_maintenance,
	seuil_depasse_vit2,
	seuil_depasse_vit1,
	t_perte_acc_depasse,
	presence_CDP

INVARIANT
	
	dmd_commodo_user : S_DMD_COMMODO_USER		&
	dmd_commodo : S_DMD_COMMODO			&
	etat_commodo : ETAT_COMMODO			&
	type_vitesse : TYPE_VITESSE			&
	vitesse_vehicule : VITESSE			&
	alimentation : ALIMENTATION			&
	etat_moteur_ess_av : ETAT_MOTEUR_ESS_AV		&
	cdp : CDP					&
	etat_cdp : ETAT_CDP                             &
	t_int_av : T_INT_AV				&
	depassement_temps_maintenance : BOOLEAN		&
	seuil_depasse_vit2 : BOOLEAN			&
	seuil_depasse_vit1 : BOOLEAN                    &
	t_perte_acc_depasse : BOOLEAN			&
	presence_CDP : BOOLEAN                          &
	(depassement_temps_maintenance = true => alimentation = 1aucune1)	&
	
	(t_perte_acc_depasse = true => alimentation = 1aucune1)	&
	
	(alimentation = 1aucune1 => vitesse_vehicule = 0)	&

	(seuil_depasse_vit1 = true => seuil_depasse_vit2 = true) &
	
        (seuil_depasse_vit2 = false => seuil_depasse_vit1 = false) &
	
	((dmd_commodo = 1d_repos1 or dmd_commodo = 1d_interm1 or dmd_commodo = 1d_auto1) => 
		(seuil_depasse_vit1 = false & seuil_depasse_vit2 = false)) &
	(alimentation = 1aucune1 => etat_moteur_ess_av = 1arretFixeAv1 or etat_moteur_ess_av = 
		1arretMaintenance1 or etat_moteur_ess_av = 1protectionMoteur1) &
	
	(dmd_commodo = 1d_pv1 & seuil_depasse_vit2 = true & seuil_depasse_vit1 = true => 
		t_int_av = 1T_REDUC_PV_INT1) &
	
	(t_int_av = 1T_REDUC_PV_INT1 => (vitesse_vehicule <= VIT2 & dmd_commodo = 1d_pv1)) &
	
	((vitesse_vehicule < 10 & type_vitesse = 1fascendant1 & t_int_av /= 1T_REDUC_PV_INT1 &
		t_int_av /= 1T_INT_FIXE1) => t_int_av = 1T_INT_AV_TRES_LENTE1) &
	((vitesse_vehicule >= 10 & vitesse_vehicule < 50 & type_vitesse = 1fascendant1 & 
		t_int_av /= 1T_REDUC_PV_INT1 & t_int_av /= 1T_INT_FIXE1) 
		=> t_int_av = 1T_INT_AV_LENTE1) &
	((vitesse_vehicule >= 50 & vitesse_vehicule < 90 & type_vitesse = 1fascendant1 & 
		t_int_av /= 1T_REDUC_PV_INT1 & t_int_av /= 1T_INT_FIXE1) 
		=> t_int_av = 1T_INT_AV_MOYENNE1) &
	((vitesse_vehicule >= 90 & type_vitesse = 1fascendant1 & t_int_av /= 1T_REDUC_PV_INT1 &
		t_int_av /= 1T_INT_FIXE1) => t_int_av = 11T_INT_AV_RAPIDE11) &
	
	((vitesse_vehicule < 5 & type_vitesse = 1descendant1 & t_int_av /= 1T_REDUC_PV_INT1 & 
		t_int_av /= 1T_INT_FIXE1) => t_int_av = 1T_INT_AV_TRES_LENTE1) &
	((vitesse_vehicule >= 5 & vitesse_vehicule < 40 & type_vitesse = 1descendant1 & 
		t_int_av /= 1T_REDUC_PV_INT1 & t_int_av /= 1T_INT_FIXE1) 
		=> t_int_av = 1T_INT_AV_LENTE1) &
	((vitesse_vehicule >= 40 & vitesse_vehicule < 80 & type_vitesse = 1descendant1 & 
		t_int_av /= 1T_REDUC_PV_INT1 & t_int_av /= 1T_INT_FIXE1) 
		=> t_int_av = 1T_INT_AV_MOYENNE1) &
	((vitesse_vehicule >= 80 & type_vitesse = 1descendant1 & t_int_av /= 1T_REDUC_PV_INT1 &
		t_int_av /= 1T_INT_FIXE1) => t_int_av = 11T_INT_AV_RAPIDE11) &
	
	((t_int_av = 1T_INT_FIXE1 & alimentation = 1accessoire1) => ((etat_cdp = 1c_defaillant1 or
		presence_CDP = false) & (dmd_commodo = 1d_auto1 or etat_commodo = 1defaillant1) &
		(etat_moteur_ess_av = 1mInter1 or etat_moteur_ess_av = 1protectionMoteur1 or 
		etat_moteur_ess_av = 1pv3cyclesLavage1))) &
	((t_int_av = 1T_INT_FIXE1 & alimentation /= 1accessoire1) => (etat_cdp = 1c_defaillant1 or
		 presence_CDP = false) & (dmd_commodo = 1d_auto1 or etat_commodo = 1defaillant1)) &
	
	(((etat_cdp = 1c_defaillant1 & dmd_commodo = 1d_auto1) or (etat_commodo = 1defaillant1 & 
		(etat_cdp = 1c_defaillant1 or presence_CDP = false))) => t_int_av = 1T_INT_FIXE1)  &
	((etat_commodo = 1defaillant1) => (seuil_depasse_vit2 = false)) &
	
	((alimentation = 1accessoire1 & etat_moteur_ess_av = 1arretMaintenance1) => dmd_commodo = 1d_repos1)
	
INITIALISATION
	dmd_commodo_user := 1repos1 				||
	
	dmd_commodo := 1d_repos1					||

	etat_commodo := 1fonctionnel1				||
	vitesse_vehicule := 0					||
	
	type_vitesse := 1fascendant1				||
	
	alimentation := 1aucune1					||
	etat_moteur_ess_av := 1arretFixeAv1			||
	
	cdp := 1arretCDP1						||
	etat_cdp := 1c_fonctionnel1                               ||
	t_int_av := 1T_INT_AV_TRES_LENTE1				||
	
	depassement_temps_maintenance := true			||
	
	seuil_depasse_vit1 := false  ||
	seuil_depasse_vit2 := false ||		
	t_perte_acc_depasse := true ||
	presence_CDP := true 

OPERATIONS

action_commodo_repos =
	PRE
		etat_commodo = 1fonctionnel1	&
		
		dmd_commodo_user /= 1repos1
		
	THEN
		dmd_commodo_user := 1repos1	||
		
		IF (alimentation = 1aucune1) 
		THEN
			IF depassement_temps_maintenance = false
			THEN
				
				IF(etat_moteur_ess_av = 1arretMaintenance1)
				THEN
					
					etat_moteur_ess_av := 1arretFixeAv1
				ELSE
					
					etat_moteur_ess_av := 1arretMaintenance1 ||
					dmd_commodo := 1d_repos1
				END
			END
		ELSE
			IF etat_moteur_ess_av = 1arretMaintenance1
			THEN
				etat_moteur_ess_av := 1arretFixeAv1
			ELSE
				dmd_commodo := 1d_repos1		||
				etat_moteur_ess_av := 1arretFixeAv1
			END
		END ||
		IF(t_int_av=1T_REDUC_PV_INT1 or t_int_av = 1T_INT_FIXE1)
		THEN
			IF(type_vitesse = 1fascendant1)
			THEN
				IF (vitesse_vehicule < 10)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
					IF (vitesse_vehicule < 50)
					THEN
						t_int_av := 1T_INT_AV_LENTE1
					ELSE
						IF (vitesse_vehicule < 90)
						THEN
							t_int_av := 1T_INT_AV_MOYENNE1
						ELSE
							t_int_av := 11T_INT_AV_RAPIDE11
						END 
					END 
				END
			ELSE
				IF (vitesse_vehicule < 5)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
				IF (vitesse_vehicule < 40)
				THEN
					t_int_av := 1T_INT_AV_LENTE1
				ELSE
				IF (vitesse_vehicule < 80)
				THEN
					t_int_av := 1T_INT_AV_MOYENNE1
				ELSE
					t_int_av := 11T_INT_AV_RAPIDE11
				END END END
			END
		END
	END;

action_commodo_pv =
	PRE
		etat_commodo = 1fonctionnel1		&
		
		dmd_commodo_user /= 1pv1
		
	THEN
		IF (alimentation = 1aucune1)
		THEN
			IF depassement_temps_maintenance = false
			THEN
				
				IF(etat_moteur_ess_av = 1arretMaintenance1)
				THEN
					
					etat_moteur_ess_av := 1arretFixeAv1
				ELSE
					
					etat_moteur_ess_av := 1arretMaintenance1 ||
					dmd_commodo := 1d_repos1
				END
			END
		ELSE
			IF etat_moteur_ess_av = 1arretMaintenance1
			THEN
				etat_moteur_ess_av := 1arretFixeAv1
			ELSE
				dmd_commodo := 1d_pv1			||
				etat_moteur_ess_av := 1mpv1 ||
				seuil_depasse_vit1 := false ||
				IF(vitesse_vehicule > VIT2)
				
				THEN
					seuil_depasse_vit2 := true
				ELSE
					seuil_depasse_vit2 := false
				END
			END
		END ||
		dmd_commodo_user := 1pv1  ||
		IF(t_int_av=1T_REDUC_PV_INT1  or t_int_av = 1T_INT_FIXE1)
		THEN
			IF(type_vitesse = 1fascendant1)
			THEN
				IF (vitesse_vehicule < 10)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
					IF (vitesse_vehicule < 50)
					THEN
						t_int_av := 1T_INT_AV_LENTE1
					ELSE
						IF (vitesse_vehicule < 90)
						THEN
							t_int_av := 1T_INT_AV_MOYENNE1
						ELSE
							t_int_av := 11T_INT_AV_RAPIDE11
						END 
					END 
				END
			ELSE
				IF (vitesse_vehicule < 5)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
				IF (vitesse_vehicule < 40)
				THEN
					t_int_av := 1T_INT_AV_LENTE1
				ELSE
				IF (vitesse_vehicule < 80)
				THEN
					t_int_av := 1T_INT_AV_MOYENNE1
				ELSE
					t_int_av := 11T_INT_AV_RAPIDE11
				END END END
			END
		END
	END;
	
action_commodo_gv =
	PRE
		etat_commodo = 1fonctionnel1 	&
		dmd_commodo_user /= 1gv1
	THEN
		IF (alimentation = 1aucune1)
		
		THEN
			IF depassement_temps_maintenance = false
			THEN
				IF(etat_moteur_ess_av = 1arretMaintenance1)
				THEN
					
					etat_moteur_ess_av := 1arretFixeAv1
				ELSE
					
					etat_moteur_ess_av := 1arretMaintenance1 ||
					dmd_commodo := 1d_repos1
				END
			END
		ELSE
			IF etat_moteur_ess_av = 1arretMaintenance1
			THEN
				etat_moteur_ess_av := 1arretFixeAv1
			ELSE
				etat_moteur_ess_av := 1mgv1		||
				dmd_commodo := 1d_gv1			||
				seuil_depasse_vit1 := false ||
				IF(vitesse_vehicule > VIT2)
				
				THEN
					seuil_depasse_vit2 := true
				ELSE
					seuil_depasse_vit2 := false
				END
			END
		END ||
		dmd_commodo_user := 1gv1
		||
		IF(t_int_av=1T_REDUC_PV_INT1  or t_int_av = 1T_INT_FIXE1)
		THEN
			IF(type_vitesse = 1fascendant1)
			THEN
				IF (vitesse_vehicule < 10)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
					IF (vitesse_vehicule < 50)
					THEN
						t_int_av := 1T_INT_AV_LENTE1
					ELSE
						IF (vitesse_vehicule < 90)
						THEN
							t_int_av := 1T_INT_AV_MOYENNE1
						ELSE
							t_int_av := 11T_INT_AV_RAPIDE11
						END 
					END 
				END
			ELSE
				IF (vitesse_vehicule < 5)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
				IF (vitesse_vehicule < 40)
				THEN
					t_int_av := 1T_INT_AV_LENTE1
				ELSE
				IF (vitesse_vehicule < 80)
				THEN
					t_int_av := 1T_INT_AV_MOYENNE1
				ELSE
					t_int_av := 11T_INT_AV_RAPIDE11
				END END END
			END
		END
	END;
	
action_commodo_interm =
	PRE
		etat_commodo = 1fonctionnel1 &
		
		dmd_commodo_user /= 1interm1 
	THEN
		
		IF (alimentation = 1aucune1)
		THEN
			IF depassement_temps_maintenance = false
			THEN
				
				IF(etat_moteur_ess_av = 1arretMaintenance1)
				THEN
					
					etat_moteur_ess_av := 1arretFixeAv1
				ELSE
					
					etat_moteur_ess_av := 1arretMaintenance1 ||
					dmd_commodo := 1d_repos1
				END
			END
		ELSE
			IF etat_moteur_ess_av = 1arretMaintenance1
			THEN
				etat_moteur_ess_av := 1arretFixeAv1
			ELSE
				dmd_commodo := 1d_interm1		||
				etat_moteur_ess_av := 1mInter1
			END
		END
		||
		dmd_commodo_user := 1interm1 ||
			
		IF(t_int_av=1T_REDUC_PV_INT1  or t_int_av = 1T_INT_FIXE1)
		THEN
			IF(type_vitesse = 1fascendant1)
			THEN
				IF (vitesse_vehicule < 10)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
					IF (vitesse_vehicule < 50)
					THEN
						t_int_av := 1T_INT_AV_LENTE1
					ELSE
						IF (vitesse_vehicule < 90)
						THEN
							t_int_av := 1T_INT_AV_MOYENNE1
						ELSE
							t_int_av := 11T_INT_AV_RAPIDE11
						END 
					END 
				END
			ELSE
				IF (vitesse_vehicule < 5)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
				IF (vitesse_vehicule < 40)
				THEN
					t_int_av := 1T_INT_AV_LENTE1
				ELSE
				IF (vitesse_vehicule < 80)
				THEN
					t_int_av := 1T_INT_AV_MOYENNE1
				ELSE
					t_int_av := 11T_INT_AV_RAPIDE11
				END END END
			END
		END
	END;
	



action_commodo_auto =
	PRE
		etat_commodo = 1fonctionnel1	&
		
		dmd_commodo_user /= 1auto1 &
		
		presence_CDP = true
		
	THEN
		IF (alimentation  = 1aucune1)
		THEN
			IF depassement_temps_maintenance = false
			THEN
				
				IF etat_moteur_ess_av = 1arretMaintenance1
				THEN
					
					etat_moteur_ess_av := 1arretFixeAv1
				ELSE
					
					etat_moteur_ess_av := 1arretMaintenance1 ||
					dmd_commodo := 1d_repos1
				END
			END
		ELSE
			IF etat_moteur_ess_av = 1arretMaintenance1
			THEN
				etat_moteur_ess_av := 1arretFixeAv1
			ELSE
				dmd_commodo := 1d_auto1			||
				IF etat_cdp = 1c_fonctionnel1
				THEN
					etat_moteur_ess_av := 1pvcycle1
				ELSE
					etat_moteur_ess_av := 1mInter1
				END
			END
		END			||
		dmd_commodo_user := 1auto1
		||
		IF(t_int_av=1T_REDUC_PV_INT1 & etat_cdp = 1c_fonctionnel1)
		THEN
			IF(type_vitesse = 1fascendant1)
			THEN
				t_int_av := 1T_INT_AV_TRES_LENTE1
			ELSE
				IF (vitesse_vehicule < 5)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
					t_int_av := 1T_INT_AV_LENTE1
				END
			END
		ELSE
			IF etat_cdp /= 1c_fonctionnel1
			THEN
				t_int_av := 1T_INT_FIXE1
			END
		END
	END;

action_lavage_deb =
	PRE
		alimentation = 1accessoire1	&
		etat_commodo = 1fonctionnel1
		
	THEN
		
		IF etat_moteur_ess_av/=1protectionMoteur1
		THEN
			IF etat_moteur_ess_av = 1mgv1 or etat_moteur_ess_av = 1gv3cycles1
			THEN
				etat_moteur_ess_av := 1gv3cyclesLavage1
			ELSE
				etat_moteur_ess_av := 1pv3cyclesLavage1
			END
		END
	END;

action_lavage_fin =
	PRE
		alimentation = 1accessoire1	&
		(etat_moteur_ess_av = 1pv3cyclesLavage1 or etat_moteur_ess_av = 1gv3cyclesLavage1)
	THEN
		IF etat_moteur_ess_av/=1protectionMoteur1
		THEN
			IF t_int_av /= 1T_INT_FIXE1
			THEN
				IF dmd_commodo=1d_repos1
				THEN
					etat_moteur_ess_av := 1arretFixeAv1
				ELSE
					IF dmd_commodo=1d_pv1
					THEN 
						IF((seuil_depasse_vit2 = true) & 
							seuil_depasse_vit1 = true)
						THEN etat_moteur_ess_av := 1mInter1
						ELSE etat_moteur_ess_av := 1mpv1
						END
					ELSE
						IF dmd_commodo=1d_gv1
						THEN 
							IF((seuil_depasse_vit2 = true) & 
								seuil_depasse_vit1 = true)
							THEN etat_moteur_ess_av := 1mpv1
							ELSE etat_moteur_ess_av := 1mgv1
							END
						ELSE
							IF dmd_commodo=1d_interm1
							THEN 
								etat_moteur_ess_av := 1mInter1
							ELSE
								etat_moteur_ess_av := 1arretFixeAv1
							END
						END
					END
				END
			ELSE
				
				etat_moteur_ess_av := 1mInter1
			END
		END
	END;

action_pvi_deb =
	PRE
		alimentation = 1accessoire1	&
		etat_commodo = 1fonctionnel1
		
	THEN
		IF etat_moteur_ess_av = 1arretFixeAv1 
		THEN
			etat_moteur_ess_av := 1pvcycle1
		END
	END;

action_pvi_fin = 
	PRE
		alimentation = 1accessoire1	&
		etat_moteur_ess_av = 1pvcycle1 &
		dmd_commodo = 1d_repos1 &
		etat_cdp = 1c_fonctionnel1 &
		etat_commodo = 1fonctionnel1
	THEN
		IF etat_moteur_ess_av/=1protectionMoteur1
		THEN
			IF dmd_commodo=1d_repos1
			THEN
				etat_moteur_ess_av := 1arretFixeAv1
			ELSE
				IF dmd_commodo=1d_pv1
				THEN 
					IF((seuil_depasse_vit2 = true) & 
						seuil_depasse_vit1 = true)
					THEN etat_moteur_ess_av := 1mInter1
					ELSE etat_moteur_ess_av := 1mpv1
					END
				ELSE
					IF dmd_commodo=1d_gv1
					THEN 
						IF((seuil_depasse_vit2 = true) & 
							seuil_depasse_vit1 = true)
						THEN etat_moteur_ess_av := 1mpv1
						ELSE etat_moteur_ess_av := 1mgv1
						END
					ELSE
						IF dmd_commodo=1d_interm1
						THEN 
							etat_moteur_ess_av := 1mInter1
						ELSE
							etat_moteur_ess_av := 1arretFixeAv1
						END
					END
				END
			END
		END
	END;
	


action_commodo_defaillance_deb =
	PRE
		etat_commodo = 1fonctionnel1
		
	THEN
		etat_commodo := 1defaillant1   	||
		IF(alimentation=1accessoire1 & etat_cdp = 1c_fonctionnel1 & 
			etat_moteur_ess_av /= 1protectionMoteur1 & presence_CDP=true)
		THEN
			etat_moteur_ess_av := 1pvcycle1
		ELSE
			IF  etat_cdp = 1c_defaillant1 or presence_CDP = false
			THEN
				IF  etat_moteur_ess_av /= 1protectionMoteur1
					& alimentation=1accessoire1
				THEN
					etat_moteur_ess_av := 1mInter1
				END ||
				t_int_av := 1T_INT_FIXE1
			END
		END ||
		
		seuil_depasse_vit2 := false ||
		seuil_depasse_vit1 := false
	END;

action_commodo_defaillance_fin =
	PRE
		etat_commodo = 1defaillant1
	THEN
		etat_commodo := 1fonctionnel1	||
		IF alimentation = 1accessoire1 &  etat_moteur_ess_av/=1protectionMoteur1
		THEN
			
			IF dmd_commodo=1d_repos1
			THEN
				etat_moteur_ess_av := 1arretFixeAv1
			ELSE
				IF dmd_commodo=1d_pv1
				THEN 
					etat_moteur_ess_av := 1mpv1
				ELSE
					IF dmd_commodo=1d_gv1
					THEN 
						etat_moteur_ess_av := 1mgv1
					ELSE
						IF dmd_commodo=1d_interm1
						THEN 
							etat_moteur_ess_av := 1mInter1
						ELSE
							IF dmd_commodo = 1d_auto1 & 
								etat_cdp = 1c_defaillant1
							THEN
								etat_moteur_ess_av := 1mInter1
							ELSE
								etat_moteur_ess_av := 1pvcycle1
							END
						END
					END
				END
			END
		END ||
		IF((dmd_commodo=1d_gv1 or dmd_commodo=1d_pv1) & vitesse_vehicule > VIT2)
		
		THEN
			seuil_depasse_vit2 := true
		ELSE
			seuil_depasse_vit2 := false
		END
		||
		
		IF dmd_commodo = 1d_pv1 & seuil_depasse_vit2 = true &
			seuil_depasse_vit1 = true
		THEN
			t_int_av := 1T_REDUC_PV_INT1
		ELSE
			IF (dmd_commodo = 1d_auto1 & etat_cdp = 1c_defaillant1)
			THEN
				t_int_av := 1T_INT_FIXE1
			ELSE
				IF(type_vitesse = 1fascendant1)
				THEN
					IF (vitesse_vehicule < 10)
					THEN
						t_int_av := 1T_INT_AV_TRES_LENTE1
					ELSE
						IF (vitesse_vehicule < 50)
						THEN
							t_int_av := 1T_INT_AV_LENTE1
						ELSE
							IF (vitesse_vehicule < 90)
							THEN
								t_int_av := 1T_INT_AV_MOYENNE1
							ELSE
								t_int_av := 11T_INT_AV_RAPIDE11
							END 
						END 
					END
				ELSE
					IF (vitesse_vehicule < 5)
					THEN
						t_int_av := 1T_INT_AV_TRES_LENTE1
					ELSE
					IF (vitesse_vehicule < 40)
					THEN
						t_int_av := 1T_INT_AV_LENTE1
					ELSE
					IF (vitesse_vehicule < 80)
					THEN
						t_int_av := 1T_INT_AV_MOYENNE1
					ELSE
						t_int_av := 11T_INT_AV_RAPIDE11
					END END END
				END
			END
		END
	END;		

action_cdp_defaillant_deb =
	PRE
		etat_cdp = 1c_fonctionnel1 &
		presence_CDP = true
	THEN
		etat_cdp := 1c_defaillant1 ||
		IF (etat_commodo = 1defaillant1 or dmd_commodo =1d_auto1) 
		THEN
			IF etat_moteur_ess_av /=1protectionMoteur1 & alimentation = 1accessoire1
			THEN
				etat_moteur_ess_av := 1mInter1 
			END ||
			t_int_av := 1T_INT_FIXE1 
		END
	END;

action_cdp_defaillant_fin =
	PRE
		etat_cdp = 1c_defaillant1 &
		presence_CDP = true
	THEN
		etat_cdp := 1c_fonctionnel1 ||
		IF etat_commodo /= 1defaillant1 & alimentation = 1accessoire1 & 
			etat_moteur_ess_av/=1protectionMoteur1
		THEN
		
			IF dmd_commodo=1d_repos1
			THEN
					etat_moteur_ess_av := 1arretFixeAv1
			ELSE
				IF dmd_commodo=1d_pv1
				THEN 
					IF((seuil_depasse_vit2 = true) & seuil_depasse_vit1 = true)
					THEN etat_moteur_ess_av := 1mInter1
					ELSE etat_moteur_ess_av := 1mpv1
					END
				ELSE
					IF dmd_commodo=1d_gv1
					THEN 
						IF((seuil_depasse_vit2 = true) &
							seuil_depasse_vit1 = true)
						THEN etat_moteur_ess_av := 1mpv1
						ELSE etat_moteur_ess_av := 1mgv1
						END
					ELSE
						IF dmd_commodo=1d_interm1
						THEN 
							etat_moteur_ess_av := 1mInter1
						ELSE
							
							etat_moteur_ess_av := 1pvcycle1
						END
					END
				END
			END
		ELSE
			IF alimentation =1accessoire1 & etat_moteur_ess_av /= 1protectionMoteur1
			THEN
				etat_moteur_ess_av := 1pvcycle1
			END
		END ||
		
		IF dmd_commodo = 1d_pv1 & seuil_depasse_vit2 = true &
			seuil_depasse_vit1 = true
		THEN
			t_int_av := 1T_REDUC_PV_INT1
		ELSE
			IF(type_vitesse = 1fascendant1)
			THEN
				IF (vitesse_vehicule < 10)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
					IF (vitesse_vehicule < 50)
					THEN
						t_int_av := 1T_INT_AV_LENTE1
					ELSE
						IF (vitesse_vehicule < 90)
						THEN
							t_int_av := 1T_INT_AV_MOYENNE1
						ELSE
							t_int_av := 11T_INT_AV_RAPIDE11
						END 
					END 
				END
			ELSE
				IF (vitesse_vehicule < 5)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
				IF (vitesse_vehicule < 40)
				THEN
					t_int_av := 1T_INT_AV_LENTE1
				ELSE
				IF (vitesse_vehicule < 80)
				THEN
					t_int_av := 1T_INT_AV_MOYENNE1
				ELSE
					t_int_av := 11T_INT_AV_RAPIDE11
				END END END
			END
		END
	END;
	
bloquer_le_moteur_ess_av =
	
	PRE
		etat_moteur_ess_av /= 1protectionMoteur1
	THEN
		etat_moteur_ess_av := 1protectionMoteur1
	END;

depasser_temps_maintenance = 
	PRE
		depassement_temps_maintenance = false 		&
		alimentation = 1aucune1
	THEN
		depassement_temps_maintenance := true
	END;
	
depasser_temps_perte_acc =
	PRE
		alimentation = 1aucune1		&
		t_perte_acc_depasse = false
	THEN
		t_perte_acc_depasse := true
	END;

alimentation_acc =
	PRE
		alimentation /= 1accessoire1	&
		vitesse_vehicule = 0
	THEN
		IF (alimentation = 1aucune1 & t_perte_acc_depasse = true)
		THEN
			
			IF etat_moteur_ess_av/=1protectionMoteur1 & 
				etat_moteur_ess_av /= 1arretMaintenance1
			THEN
				IF t_int_av = 1T_INT_FIXE1
				THEN
					IF dmd_commodo = 1d_auto1
					THEN
						etat_moteur_ess_av := 1arretFixeAv1
					ELSE
						etat_moteur_ess_av := 1mInter1
					END
				ELSE
					IF etat_commodo=1defaillant1
					THEN
						etat_moteur_ess_av := 1pvcycle1
					ELSE
						etat_moteur_ess_av := 1arretFixeAv1
					END
				END
			ELSE
				IF etat_moteur_ess_av = 1arretMaintenance1 & t_int_av = 1T_INT_FIXE1 &
						dmd_commodo /= 1d_auto1
				THEN
					etat_moteur_ess_av := 1mInter1
				END
			END	||
			IF (t_int_av = 1T_INT_FIXE1 & dmd_commodo = 1d_auto1) or 
				(t_int_av = 1T_REDUC_PV_INT1)
			THEN
				
				t_int_av := 1T_INT_AV_TRES_LENTE1
			END ||
			dmd_commodo := 1d_repos1||
			seuil_depasse_vit1 := false ||
			seuil_depasse_vit2 := false 
		ELSE
			
			IF etat_moteur_ess_av/=1protectionMoteur1 & etat_moteur_ess_av /= 
				1arretMaintenance1
			THEN
				IF t_int_av = 1T_INT_FIXE1
				THEN
					etat_moteur_ess_av := 1mInter1
				ELSE
					IF etat_commodo=1defaillant1
					THEN
						etat_moteur_ess_av := 1pvcycle1
					ELSE
						IF dmd_commodo=1d_repos1
						THEN
							etat_moteur_ess_av := 1arretFixeAv1
						ELSE
							IF dmd_commodo=1d_pv1
							THEN 
							IF((seuil_depasse_vit2 = true) & 
								seuil_depasse_vit1 =true )
							THEN etat_moteur_ess_av := 1mInter1
							ELSE etat_moteur_ess_av := 1mpv1
							END
							ELSE
							IF dmd_commodo=1d_gv1
							THEN 
							IF((seuil_depasse_vit2 = true) & 
								seuil_depasse_vit1 = true)
							THEN etat_moteur_ess_av := 1mpv1
							ELSE etat_moteur_ess_av := 1mgv1
							END
							ELSE
							IF dmd_commodo=1d_interm1
							THEN etat_moteur_ess_av := 1mInter1
							ELSE etat_moteur_ess_av := 1pvcycle1
							END
							END
							END
						END
					END
				END
			ELSE
				IF etat_moteur_ess_av = 1arretMaintenance1 & t_int_av = 1T_INT_FIXE1 &
						dmd_commodo /= 1d_auto1
				THEN
					etat_moteur_ess_av := 1mInter1
				END
			END
		END 	||
		alimentation := 1accessoire1		||
		t_perte_acc_depasse := false		||
		depassement_temps_maintenance := false 
	END;			

alimentation_dem =
	PRE
		alimentation = 1accessoire1	&
		vitesse_vehicule = 0
	THEN
		alimentation := 1demarreur1		||
		depassement_temps_maintenance := false ||
		t_perte_acc_depasse := false ||
		IF etat_moteur_ess_av /= 1protectionMoteur1
		THEN
			etat_moteur_ess_av := 1suspendu1
		END
	END;
	
alimentation_coupe =
	PRE
		alimentation = 1accessoire1 &
		vitesse_vehicule = 0
	THEN
		alimentation := 1aucune1		||
		depassement_temps_maintenance := false	||
		t_perte_acc_depasse := false ||
		IF etat_moteur_ess_av /= 1protectionMoteur1
		THEN
			etat_moteur_ess_av := 1arretFixeAv1
		END
	END;
	


action_CDP_pv =
	SELECT
		alimentation  = 1accessoire1	&
		(dmd_commodo = 1d_auto1 or etat_commodo = 1defaillant1) &
		etat_cdp = 1c_fonctionnel1 &
		presence_CDP = true
	THEN
		cdp := 1pvCDP1			||
		IF(etat_moteur_ess_av /= 1protectionMoteur1)
		THEN
			etat_moteur_ess_av := 1mpv1
		END
	END;

action_CDP_gv =
	SELECT
		alimentation = 1accessoire1	&
		(dmd_commodo = 1d_auto1 or etat_commodo = 1defaillant1) &
		etat_cdp = 1c_fonctionnel1 &
		presence_CDP = true
	THEN
		cdp := 1gvCDP1			||
		IF(etat_moteur_ess_av /= 1protectionMoteur1)
		THEN
			etat_moteur_ess_av := 1gv3cycles1
		END
	END;

action_CDP_arret =
	SELECT
		alimentation = 1accessoire1	&
		(dmd_commodo = 1d_auto1 or etat_commodo = 1defaillant1) &
		etat_cdp = 1c_fonctionnel1 &
		presence_CDP = true
	THEN
		
		IF(etat_moteur_ess_av /= 1protectionMoteur1)
		THEN
			etat_moteur_ess_av := 1arretFixeAv1
		END			||
		cdp := 1arretCDP1
	END;



deceleration(varvit) =
	PRE
		varvit : VITESSE		&
		alimentation = 1accessoire1	&
		(varvit <= vitesse_vehicule) 
	THEN
		IF((dmd_commodo = 1d_pv1  or dmd_commodo = 1d_gv1) & seuil_depasse_vit2 = true & 
			(vitesse_vehicule - varvit < VIT1 or seuil_depasse_vit1 = true))
		
		THEN
			seuil_depasse_vit1 := true ||
			IF(dmd_commodo = 1d_pv1 & t_int_av /= 1T_INT_FIXE1)
			THEN
				IF(etat_moteur_ess_av/=1protectionMoteur1)
				THEN
					etat_moteur_ess_av := 1mInter1		
				END ||
				t_int_av := 1T_REDUC_PV_INT1
			ELSE
				IF(dmd_commodo_user = 1gv1 & t_int_av /= 1T_INT_FIXE1)
				THEN
					etat_moteur_ess_av := 1mpv1	||
					IF (vitesse_vehicule - varvit < 5)
					THEN
						t_int_av := 1T_INT_AV_TRES_LENTE1
					ELSE
					IF (vitesse_vehicule - varvit < 40)
					THEN
						t_int_av := 1T_INT_AV_LENTE1
					ELSE
					IF (vitesse_vehicule - varvit < 80)
					THEN
						t_int_av := 1T_INT_AV_MOYENNE1
					ELSE
						t_int_av := 11T_INT_AV_RAPIDE11
					END END	END
				END
			END
		ELSE
			IF t_int_av /= 1T_INT_FIXE1
			THEN
				IF ((vitesse_vehicule - varvit) < 5)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
				IF (vitesse_vehicule - varvit < 40)
				THEN
					t_int_av := 1T_INT_AV_LENTE1
				ELSE
				IF (vitesse_vehicule - varvit < 80)
				THEN
					t_int_av := 1T_INT_AV_MOYENNE1
				ELSE
					t_int_av := 11T_INT_AV_RAPIDE11
				END END END
			END
		END ||
		vitesse_vehicule := vitesse_vehicule - varvit	||
		type_vitesse := 1descendant1
	END;

acceleration(varvit) =
	PRE
		alimentation = 1accessoire1	&
		varvit : VITESSE			&
		varvit + vitesse_vehicule < MAX_VITESSE
	THEN
		IF ((dmd_commodo = 1d_pv1  or dmd_commodo = 1d_gv1) & 
			seuil_depasse_vit2 = false & vitesse_vehicule + varvit > VIT2  )
		
		THEN
			seuil_depasse_vit2 := true
		END		||
		IF (not(dmd_commodo = 1d_pv1 & etat_moteur_ess_av = 1mInter1 & 
			(vitesse_vehicule+varvit) <= VIT2)) & t_int_av /= 1T_INT_FIXE1
		THEN
			IF ((vitesse_vehicule+varvit) < 10)
			THEN
				t_int_av := 1T_INT_AV_TRES_LENTE1
			ELSE
			IF (vitesse_vehicule+varvit < 50)
			THEN
				t_int_av := 1T_INT_AV_LENTE1
			ELSE
			IF (vitesse_vehicule+varvit < 90)
			THEN
				t_int_av := 1T_INT_AV_MOYENNE1
			ELSE
				t_int_av := 11T_INT_AV_RAPIDE11
			END END END 
		END ||
		IF ((dmd_commodo = 1d_pv1) & (etat_moteur_ess_av = 1mInter1) & 
			(vitesse_vehicule+varvit) > VIT2)
		
		THEN
			IF(etat_moteur_ess_av /= 1protectionMoteur1 & t_int_av /= 1T_INT_FIXE1)
			THEN
				etat_moteur_ess_av := 1mpv1 
			END ||
			seuil_depasse_vit1 := false
		ELSE
			IF(dmd_commodo = 1d_gv1 & etat_moteur_ess_av =1mpv1 & 
				(vitesse_vehicule+varvit) >VIT2)
			THEN
				IF(etat_moteur_ess_av /= 1protectionMoteur1 &
					t_int_av /= 1T_INT_FIXE1)
				THEN
					etat_moteur_ess_av := 1mgv1 
				END ||
				seuil_depasse_vit1 := false
			END
		END ||
		vitesse_vehicule := vitesse_vehicule + varvit	||
		type_vitesse := 1fascendant1
	END;
	
vitesse(vit) =
	PRE
		alimentation = 1accessoire1	&
		vit : VITESSE			
	THEN
		IF ((dmd_commodo = 1d_pv1  or dmd_commodo = 1d_gv1) & seuil_depasse_vit2 = false &
			vit > VIT2  )
		
		THEN
			seuil_depasse_vit2 := true
		END				||
		IF (vitesse_vehicule < vit)
		THEN
			type_vitesse := 1fascendant1 ||
			IF(dmd_commodo = 1d_pv1 & etat_moteur_ess_av = 1mInter1 & 
				vit > VIT2)
			
			THEN
				IF etat_moteur_ess_av /= 1protectionMoteur1 & t_int_av /= 1T_INT_FIXE1
				THEN
					etat_moteur_ess_av := 1mpv1
				END ||
				seuil_depasse_vit1 := false
			ELSE
				IF(dmd_commodo = 1d_gv1 & etat_moteur_ess_av =1mpv1 &
					vit > VIT2)
				THEN
					IF etat_moteur_ess_av /= 1protectionMoteur1 & 
						t_int_av /= 1T_INT_FIXE1
					THEN
						etat_moteur_ess_av := 1mgv1 
					END ||
					seuil_depasse_vit1 := false
				END
			END
			||
			IF (not (dmd_commodo = 1d_pv1 & etat_moteur_ess_av = 1mInter1 &
				vit <= VIT2)) & t_int_av /= 1T_INT_FIXE1
			THEN
				IF (vit < 10)
				THEN
					t_int_av := 1T_INT_AV_TRES_LENTE1
				ELSE
					IF (vit < 50)
					THEN
						t_int_av := 1T_INT_AV_LENTE1
					ELSE
						IF (vit < 90)
						THEN
							t_int_av := 1T_INT_AV_MOYENNE1
						ELSE
							t_int_av := 11T_INT_AV_RAPIDE11
						END 
					END 
				END
			END
		ELSE
			type_vitesse := 1descendant1 ||
			IF((dmd_commodo = 1d_pv1  or dmd_commodo = 1d_gv1) & 
				seuil_depasse_vit2 = true & (vit < VIT1 or 
				seuil_depasse_vit1 = true))
		       
		        THEN
				seuil_depasse_vit1 := true ||
				IF(dmd_commodo = 1d_pv1 & t_int_av /= 1T_INT_FIXE1)
			        THEN
					IF(etat_moteur_ess_av /= 1protectionMoteur1)
					THEN
					       etat_moteur_ess_av := 1mInter1
					END ||
				       t_int_av := 1T_REDUC_PV_INT1
				ELSE
					IF t_int_av /= 1T_INT_FIXE1
					THEN
						IF dmd_commodo = 1d_gv1
						THEN
							etat_moteur_ess_av := 1mpv1 ||
							IF (vit < 5)
							
							THEN
								t_int_av := 1T_INT_AV_TRES_LENTE1
							ELSE
							IF (vit < 40)
							
							THEN t_int_av := 1T_INT_AV_LENTE1
							ELSE
							IF (vit < 80)
							
							THEN t_int_av := 1T_INT_AV_MOYENNE1
							ELSE t_int_av := 11T_INT_AV_RAPIDE11
							END
							END
							END
						END
					END
				END
			ELSE
				IF t_int_av /= 1T_INT_FIXE1
				THEN
					IF (vit < 5)
					
					THEN
						t_int_av := 1T_INT_AV_TRES_LENTE1
					ELSE
						IF (vit < 40)
						
						THEN
							t_int_av := 1T_INT_AV_LENTE1
						ELSE
						IF (vit < 80)
						
						THEN
							t_int_av := 1T_INT_AV_MOYENNE1
						ELSE
							t_int_av := 11T_INT_AV_RAPIDE11
						END
						END
					END
				END
			END
		END ||
		vitesse_vehicule := vit	
	END

END
