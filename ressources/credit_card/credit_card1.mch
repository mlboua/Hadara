MACHINE
	credit_card

SETS
	/* L'ensemble des messages echanges entre les composants */
	SIGNALS = {1env_connection_signal1, 1id_request1, 1card_id_response1, 1env_enter_pin_message1, 1env_user_pin1, 1env_code_error1, 1user_pin_transfer1, 
			1result_operation1, 1swallow_card1, 1card_id_transfer1, 1env_another_enter_pin_message1, 1balance_transfer1, 1env_asked_withdrawal_message1, 
			1env_asked_withdrawal1, 1asked_withdrawal_transfer1, 1operation_failed, 1operation_done1, 1operation_not_done1, 1env_operation_done1, 
			1env_operation_not_possible1, 1no_msg1};
	/* Les etats possibles pour le terminal */
	ATM_STATES = {1A01,1A11,1A21,1A31,1A41,1A51,1A61,1A71,1A81,1A91,1A101,1A111,1A121,1A131,1A141};
	/* Les etats possibles pour la carte */ 
	CARD_STATES = {1C11,1C21,1C31,1C41,1C51,1C61};
	/* Les etats possibles pour la banque (base de donnees) */
	DB_STATES = {1DB_11,1DB_21,1DB_31,1DB_41,1DB_51,1DB_61};
	MESBOOL = {1vrai1,1faux1}

VARIABLES
	/* Stocke l'etat courant du systeme (variable de controle) */
	ATM_state, CARD_state, DB_state,
	/* L'id de la carte, son pin et le pin de l'utilisateur recu par la carte */
	C_card_id, C_card_pin, C_user_pin,
	/* Le compteur de tentatives de verification du pin */
	C_counter, 
	/* Le solde du compte associe a la carte, l'id de la carte stocke par la banque, l'id de la carte recu par la banque,
		le type d'erreur rencontre par la banque, le retrait demande par l'utilisateur envoye a la banque */
	DB_balance, DB_count_id, DB_card_id, DB_error, DB_asked_withdrawal,
	/* Le resultat de l'identification du pin recu par le terminal, le message d'erreur emis par la banque recu par le terminal, le pin entre par l'utilisateur, l'id de la carte lu par l'ATM, le solde du compte recu par l'ATM, le retrait demande par l'utilisateur recu par l'ATM, le solde du compte calcule par l'ATM apres l'operation de retrait */
	A_result, A_error, A_user_pin, A_card_id, A_balance, A_asked_withdrawal, A_balance_after_operation

INVARIANT
	ATM_state : ATM_STATES & CARD_state : CARD_STATES & DB_state : DB_STATES &
	C_card_id : 0..9999 & C_card_pin : 0..9999 & C_user_pin : 0..9999 &
	C_counter : 0..2 & 
	DB_balance : 0..10000 & DB_count_id : 0..9999 & DB_card_id : 0..9999 & DB_error : 0..2 & DB_asked_withdrawal : 0..10000 &
	A_result : MESBOOL & A_error : 0..2 & A_user_pin : 0..9999 & A_card_id : 0..9999 & A_balance : 0..10000 & A_asked_withdrawal : 0..10000 & A_balance_after_operation : 0..10000 &
	((ATM_state = 1A101 or ATM_state = 1A111 or ATM_state = 1A121) => (A_balance = DB_balance))

INITIALISATION
	ATM_state := 1A01 || CARD_state := 1C11 || DB_state := 1DB_11 ||
	C_card_id := 3121 || C_card_pin := 4040 || C_user_pin := 0 || 
	C_counter := 0 || 
	DB_balance := 7400 || DB_count_id := 3121 || DB_card_id := 0 || DB_error := 0 || DB_asked_withdrawal := 0 ||
	A_result := 1faux1 || A_error := 0 || A_user_pin := 0 || A_card_id := 0 || A_balance := 0 || A_asked_withdrawal := 0 || A_balance_after_operation := 0

OPERATIONS
	/* Le terminal recoit le signal de connexion de la carte */
	ATM_signal_connexion =
		SELECT
			ATM_state = 1A01
		THEN
			out := 1env_connection_signal1 ||
			ATM_state := 1A11
		END;

	ATM_operation_vide =
		SELECT
			ATM_state = 1A11
		THEN
			ATM_state := 1A21
		END;

	/* Le terminal demande son identifiant a la carte */
	ATM_demande_id =
		SELECT
			ATM_state = 1A21 & CARD_state = 1C11
		THEN
			IF 
				C_card_id < 12345 & C_card_id <= 4823 & C_card_pin < 4545 & C_card_id <= 9114
			THEN
				out := 1id_request1 ||
				C_counter := 0 ||
				CARD_state := 1C21 || ATM_state := 1A31
			END
		END;

	/* La carte envoie son identifiant au terminal */
	CARD_reponse_id =
		SELECT
			ATM_state = 1A31 & CARD_state = 1C21
		THEN
			out := 1card_id_response1 ||
			A_card_id := C_card_id ||
			ATM_state := 1A41 || CARD_state := 1C31
		END;

	/* Le terminal demande le code pin de l'utilisateur */
	ATM_demande_pin_user =
		SELECT
			ATM_state = 1A41
		THEN
			ATM_state := 1A51 ||
			out := 1env_user_pin1
		END;

	/* L'utilisateur envoie son code pin au terminal */
	ATM_recoit_pin_user =
		SELECT
			ATM_state = 1A51
		THEN
			out := 1env_enter_pin_message1 ||
			ANY xx WHERE xx : 0..9999 & (xx = 4040 or xx = 2121) THEN A_user_pin := xx END ||
			ATM_state := 1A61
		END;

	/* Le terminal transfere le pin recu de l'utilisateur a la carte */
 	ATM_transfer_pin_user =
		SELECT
			ATM_state = 1A61 & CARD_state = 1C31
		THEN
			out := 1user_pin_transfer1 ||
			C_user_pin := A_user_pin ||
			ATM_state := 1A71 || CARD_state := 1C41
		END;

	/* La carte traite le pin recu */
	CARD_traite_pin =
		SELECT
			ATM_state = 1A71 & CARD_state = 1C41
		THEN
			IF 
				/* Le nombre d'essais maximums n'est pas atteint mais le pin donne est mauvais */
				(C_counter < 2) & not(C_user_pin = C_card_pin)
			THEN
				out := 1result_operation1 ||
				A_result := 1faux1 ||
				C_counter := C_counter + 1 ||
				CARD_state := 1C31 || ATM_state := 1A81
			ELSE
				IF
					/* Le nombre d'essais maximums n'est pas atteint et le pin donne est correct */
					(C_counter <= 2) & (C_user_pin = C_card_pin)
				THEN
					CARD_state := 1C51
				ELSE
					IF
						/* Le nombre d'essais maximums est atteint et le pin est mauvais */
						(C_counter >= 2) & not(C_user_pin = C_card_pin)
					THEN
						CARD_state := 1C61
					END
				END
			END
		END;

	/* L'authentification a reussie, la carte le transmet au terminal */
	CARD_succes_pin = 
		SELECT			
			CARD_state = 1C51 & ATM_state = 1A71
		THEN
			out := 1result_operation1 ||
			A_result := 1vrai1 ||
			CARD_state := 1C11 || ATM_state := 1A81
		END;

	/* L'authentification a echoue, la carte le transmet au terminal */
	CARD_failed_pin =
		SELECT
			CARD_state = 1C61 & ATM_state = 1A71
		THEN
			out := 1swallow_card1 ||
			CARD_state := 1C11 || ATM_state := 1A01
		END;

	/* La carte traite le resultat de l'authentification */
	ATM_traite_reponse_pin =
		SELECT
			ATM_state = 1A81 & DB_state = 1DB_11
		THEN
			IF
				/* Si l'authentification a reussi, le terminal transmet l'identifiant de la carte a la banque */
				A_result = 1vrai1 & (DB_balance >= 5000) & (DB_balance <= 10000)
			THEN
				out := 1card_id_transfer1 ||
				DB_card_id := A_card_id ||
				ATM_state := 1A91 || DB_state := 1DB_21
			ELSE
				/* Si l'authentification a echoue, le terminal demande un nouveau pin a l'utilisateur */
				out := env_another_enter_pin_message ||
				ATM_state := 1A51
			END
		END;

	/* La banque verifie l'identifiant recu */
	DB_check_id =
		SELECT
			DB_state = 1DB_21
		THEN
			IF
				DB_card_id = DB_count_id
			THEN
				DB_state := 1DB_31
			ELSE
				DB_error := 1 ||
				DB_state := 1DB_61
			END
		END;

	/* Si l'identifiant de la carte n'est pas le bon ou si le montant demande est trop grand par rapport au solde, la banque annule l'operation */
	DB_operation_not_done =
		SELECT
			DB_state = 1DB_61 & (ATM_state = 1A91 or ATM_state = 1A141)
		THEN
			out := 1operation_not_done1 ||
			A_error := DB_error ||
			ATM_state := 1A01 || DB_state := 1DB_11
		END;

	/* La banque demande au terminal le montant du retrait */
	DB_demande_retrait =
		SELECT
			DB_state = 1DB_31 & ATM_state = 1A91
		THEN
			A_balance := DB_balance ||
			out := 1balance_transfer1 ||
			ATM_state := 1A101 || DB_state := 1DB_41
		END;

	/* Le terminal demande a l'utilisateur le montant du retrait */
	ATM_demande_retrait =
		SELECT
			ATM_state = 1A101
		THEN
			ATM_state := 1A111 ||
			out := 1env_asked_withdrawal_message1
		END;

	/* Le terminal recoit le montant du retrait demande par l'utilisateur */
	ATM_obtient_montant =
		SELECT
			ATM_state = 1A111
		THEN
			out := 1env_asked_withdrawal1 ||
			ANY xx WHERE xx : 0..10000 THEN A_asked_withdrawal := xx END ||
			ATM_state := 1A121
		END;

	/* Le terminal transmet le montant du retrait a la banque si celui-ci est conforme au solde */
	ATM_traite_retrait =
		SELECT
			ATM_state = 1A121 & DB_state = 1DB_41
		THEN
			IF
				A_balance >= A_asked_withdrawal
			THEN
				A_balance_after_operation := A_balance - A_asked_withdrawal ||
				DB_balance := DB_balance - A_asked_withdrawal ||
				DB_asked_withdrawal := A_asked_withdrawal ||
				out := 1asked_withdrawal_transfer1 ||
				DB_state := 1DB_51 || ATM_state := 1A131
			ELSE
				out := 1operation_failed ||
				DB_error := 2 ||
				ATM_state := 1A141 || DB_state := 1DB_61
			END
		END;

	/* Le traitement s'est bien deroule, la banque transmet son accord au terminal */
	DB_traitement_ok =
		SELECT
			DB_state = 1DB_51 & ATM_state = 1A131
		THEN
			out := 1operation_done1 ||
			DB_state := 1DB_11 || ATM_state := 1A01
		END
END