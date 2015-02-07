package materials;

import booleanExpression.*;
import eventB.CMachine;
import eventB.eventBSubstitutions.*;
import eventB.eventBevents.CAnyEvent;
import eventB.eventBevents.CEvent;
import eventB.eventBevents.CGuardedEvent;
import eventB.eventBevents.CNonGuardedEvent;
import generalExpression.CNumber;
import generalExpression.CSet;
import generalExpression.CVariable;
import arithmeticExpression.*;

/**
 * User: LoW
 * Date: 09/01/13
 * Time: 23:26
 */
public class CMachineMaker
{
    public static CMachine getESSUYAGE_AVMachine()
    {
        CMachine cMachine = new CMachine("ESSUYAGE_AV");

        CNumber VIT1 = new CNumber(5);
        CNumber VIT2 = new CNumber(10);
        CNumber MAX_VITESSE = new CNumber(100);

        //S_DMD_COMMODO_USER = {repos, interm, pv, gv, auto};
        CNumber repos = new CNumber(0);
        CNumber interm = new CNumber(1);
        CNumber pv = new CNumber(2);
        CNumber gv = new CNumber(3);
        CNumber auto = new CNumber(4);
        //S_DMD_COMMODO = {d_repos, d_interm, d_pv, d_gv, d_auto};
        CNumber d_repos = new CNumber(0);
        CNumber d_interm = new CNumber(1);
        CNumber d_pv = new CNumber(2);
        CNumber d_gv = new CNumber(3);
        CNumber d_auto = new CNumber(4);
        //ETAT_COMMODO = {defaillant, fonctionnel};
        CNumber defaillant = new CNumber(0);
        CNumber fonctionnel = new CNumber(1);
        //TYPE_VITESSE = {ascendant, descendant};
        CNumber ascendant = new CNumber(0);
        CNumber descendant = new CNumber(1);
        //ALIMENTATION = {accessoire, demarreur, aucune};
        CNumber accessoire = new CNumber(0);
        CNumber demarreur = new CNumber(1);
        CNumber aucune = new CNumber(2);
        //ETAT_MOTEUR_ESS_AV = {mgv, mpv, arretFixeAv, suspendu, arretMaintenance,mInter,pv1cycle,
             //   gv3cycles, protectionMoteur, pv3cyclesLavage, gv3cyclesLavage}
        CNumber mgv = new CNumber(0);
        CNumber mpv = new CNumber(1);
        CNumber arretFixeAv = new CNumber(2);
        CNumber suspendu = new CNumber(3);
        CNumber arretMaintenance = new CNumber(4);
        CNumber mInter = new CNumber(5);
        CNumber pv1cycle = new CNumber(6);
        CNumber gv3cycles = new CNumber(7);
        CNumber protectionMoteur = new CNumber(8);
        CNumber pv3cyclesLavage = new CNumber(9);
        CNumber gv3cyclesLavage = new CNumber(10);
        //CDP = {pvCDP, gvCDP, arretCDP};
        CNumber pvCDP = new CNumber(0);
        CNumber gvCDP = new CNumber(1);
        CNumber arretCDP = new CNumber(2);
        //ETAT_CDP = {c_defaillant,c_fonctionnel};
        CNumber c_defaillant = new CNumber(0);
        CNumber c_fonctionnel = new CNumber(1);
        //BOOLEAN = {false, true};
        CNumber bfalse = new CNumber(0);
        CNumber btrue = new CNumber(1);
        //T_INT_AV = {T_INT_AV_TRES_LENTE, T_INT_AV_LENTE, T_INT_AV_MOYENNE, T_INT_AV_RAPIDE,
               // T_INT_FIXE, T_REDUC_PV_INT}
        CNumber T_INT_AV_TRES_LENTE = new CNumber(0);
        CNumber T_INT_AV_LENTE = new CNumber(1);
        CNumber T_INT_AV_MOYENNE = new CNumber(2);
        CNumber T_INT_AV_RAPIDE = new CNumber(3);
        CNumber T_INT_FIXE = new CNumber(4);
        CNumber T_REDUC_PV_INT = new CNumber(5);

        CVariable dmd_commodo_user = new CVariable("dmd_commodo_user");
        CVariable dmd_commodo = new CVariable("dmd_commodo");
        CVariable etat_commodo = new CVariable("etat_commodo");
        CVariable type_vitesse = new CVariable("type_vitesse");
        CVariable vitesse_vehicule = new CVariable("vitesse_vehicule");
        CVariable alimentation = new CVariable("alimentation");
        CVariable etat_moteur_ess_av = new CVariable("etat_moteur_ess_av");
        CVariable cdp = new CVariable("cdp");
        CVariable etat_cdp = new CVariable("etat_cdp");
        CVariable t_int_av = new CVariable("t_int_av");
        CVariable depassement_temps_maintenance = new CVariable("depassement_temps_maintenance");
        CVariable seuil_depasse_vit2 = new CVariable("seuil_depasse_vit2");
        CVariable seuil_depasse_vit1 = new CVariable("seuil_depasse_vit1");
        CVariable t_perte_acc_depasse = new CVariable("t_perte_acc_depasse");
        CVariable presence_CDP = new CVariable("presence_CDP");

        cMachine.setVariables(dmd_commodo_user,dmd_commodo,etat_commodo,type_vitesse,vitesse_vehicule,alimentation,etat_moteur_ess_av,cdp,etat_cdp,t_int_av,depassement_temps_maintenance,seuil_depasse_vit2,seuil_depasse_vit1,t_perte_acc_depasse,presence_CDP);

        //dmd_commodo_user : S_DMD_COMMODO_USER		&
        CBooleanExpression inv_dmd_commodo_user = new CAnd(new CInDomain(dmd_commodo_user,0),new CGreater(new CNumber(5),dmd_commodo_user));
        //dmd_commodo : S_DMD_COMMODO			&
        CBooleanExpression inv_dmd_commodo = new CAnd(new CInDomain(dmd_commodo,0),new CGreater(new CNumber(5),dmd_commodo));
        //etat_commodo : ETAT_COMMODO			&
        CBooleanExpression inv_etat_commodo = new CAnd(new CInDomain(etat_commodo,0),new CGreater(new CNumber(2),etat_commodo));
        //type_vitesse : TYPE_VITESSE			&
        CBooleanExpression inv_type_vitesse = new CAnd(new CInDomain(type_vitesse,0),new CGreater(new CNumber(2),type_vitesse));
        //vitesse_vehicule : VITESSE			&
        CBooleanExpression inv_vitesse_vehicule = new CAnd(new CInDomain(vitesse_vehicule,0),new CGreater(MAX_VITESSE,vitesse_vehicule));
        //alimentation : ALIMENTATION			&
        CBooleanExpression inv_alimentation = new CAnd(new CInDomain(alimentation,0),new CGreater(new CNumber(3),alimentation));
        //etat_moteur_ess_av : ETAT_MOTEUR_ESS_AV		&
        CBooleanExpression inv_etat_moteur_ess_av = new CAnd(new CInDomain(etat_moteur_ess_av,0),new CGreater(new CNumber(11),etat_moteur_ess_av));
        //cdp : CDP					&
        CBooleanExpression inv_cdp = new CAnd(new CInDomain(cdp,0),new CGreater(new CNumber(3),cdp));
        //etat_cdp : ETAT_CDP                             &
        CBooleanExpression inv_etat_cdp = new CAnd(new CInDomain(etat_cdp,0),new CGreater(new CNumber(2),etat_cdp));
        //t_int_av : T_INT_AV				&
        CBooleanExpression inv_t_int_av = new CAnd(new CInDomain(t_int_av,0),new CGreater(new CNumber(6),t_int_av));
        //depassement_temps_maintenance : BOOLEAN		&
        CBooleanExpression inv_depassement_temps_maintenance = new CAnd(new CInDomain(depassement_temps_maintenance,0),new CGreater(new CNumber(2),depassement_temps_maintenance));
        //seuil_depasse_vit2 : BOOLEAN			&
        CBooleanExpression inv_seuil_depasse_vit2 = new CAnd(new CInDomain(seuil_depasse_vit2,0),new CGreater(new CNumber(2),seuil_depasse_vit2));
        //seuil_depasse_vit1 : BOOLEAN                    &
        CBooleanExpression inv_seuil_depasse_vit1 = new CAnd(new CInDomain(seuil_depasse_vit1,0),new CGreater(new CNumber(2),seuil_depasse_vit1));
        //t_perte_acc_depasse : BOOLEAN			&
        CBooleanExpression inv_t_perte_acc_depasse = new CAnd(new CInDomain(t_perte_acc_depasse,0),new CGreater(new CNumber(2),t_perte_acc_depasse));
        //presence_CDP : BOOLEAN
        CBooleanExpression inv_presence_CDP = new CAnd(new CInDomain(presence_CDP,0),new CGreater(new CNumber(2),presence_CDP));

        CBooleanExpression typage = new CAnd(inv_dmd_commodo_user,inv_dmd_commodo, inv_etat_commodo,inv_type_vitesse,inv_vitesse_vehicule,inv_alimentation,inv_etat_moteur_ess_av,inv_cdp,inv_etat_cdp,inv_t_int_av,inv_depassement_temps_maintenance,inv_seuil_depasse_vit2,inv_seuil_depasse_vit1,inv_t_perte_acc_depasse,inv_presence_CDP);

   //     (depassement_temps_maintenance = true => alimentation = aucune)	&
        CBooleanExpression i1 = new COr(new CNot(new CEquals(depassement_temps_maintenance, btrue)), new CEquals(alimentation, aucune));
	//    (t_perte_acc_depasse = true => alimentation = aucune)	&
        CBooleanExpression i2 = new COr(new CNot(new CEquals(t_perte_acc_depasse, btrue)), new CEquals(alimentation, aucune));
        //     (alimentation = aucune => vitesse_vehicule = 0)	&
        CBooleanExpression i3 = new COr(new CNot(new CEquals(alimentation, aucune)), new CEquals(vitesse_vehicule, new CNumber(0)));
        //   (seuil_depasse_vit1 = true => seuil_depasse_vit2 = true) &
        CBooleanExpression i4 = new COr(new CNot(new CEquals(seuil_depasse_vit1, btrue)), new CEquals(seuil_depasse_vit2, btrue));
        //    (seuil_depasse_vit2 = false => seuil_depasse_vit1 = false) &
        CBooleanExpression i5 = new COr(new CNot(new CEquals(seuil_depasse_vit2, bfalse)), new CEquals(seuil_depasse_vit1, bfalse));
	//    ((dmd_commodo = d_repos or dmd_commodo = d_interm or dmd_commodo = d_auto) =>
    //    (seuil_depasse_vit1 = false & seuil_depasse_vit2 = false)) &
        CBooleanExpression i6 = new COr(new CNot(new COr(new CEquals(dmd_commodo, d_repos),new CEquals(dmd_commodo, d_interm),new CEquals(dmd_commodo, d_auto))), new CAnd(new CEquals(seuil_depasse_vit1, bfalse),new CEquals(seuil_depasse_vit2, bfalse)));
        //     (alimentation = aucune => etat_moteur_ess_av = arretFixeAv or etat_moteur_ess_av =
    //        arretMaintenance or etat_moteur_ess_av = protectionMoteur) &
        CBooleanExpression i7 = new COr(new CNot(new CEquals(alimentation, aucune)), new COr(new CEquals(etat_moteur_ess_av, arretFixeAv),new CEquals(etat_moteur_ess_av, arretMaintenance),new CEquals(etat_moteur_ess_av, protectionMoteur)));
        //  (dmd_commodo = d_pv & seuil_depasse_vit2 = true & seuil_depasse_vit1 = true =>
    //            t_int_av = T_REDUC_PV_INT) &
        CBooleanExpression i8 = new COr(new CNot(new CAnd(new CEquals(dmd_commodo, d_pv),new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue))), new CEquals(t_int_av, T_REDUC_PV_INT));
        //  (t_int_av = T_REDUC_PV_INT => (vitesse_vehicule <= VIT2 & dmd_commodo = d_pv)) &
        CBooleanExpression i9 = new COr(new CNot(new CEquals(t_int_av, T_REDUC_PV_INT)), new CAnd(new CGreater(VIT2,vitesse_vehicule),new CEquals(dmd_commodo, d_pv)));
        //   ((vitesse_vehicule < 10 & type_vitesse = ascendant & t_int_av /= T_REDUC_PV_INT &
    //            t_int_av /= T_INT_FIXE) => t_int_av = T_INT_AV_TRES_LENTE) &
        CBooleanExpression i10 = new COr(new CNot(new CAnd(new CGreater(new CNumber(10),vitesse_vehicule),new CEquals(type_vitesse, ascendant),new CNot(new CEquals(t_int_av, T_REDUC_PV_INT)),new CNot(new CEquals(t_int_av, T_INT_FIXE)))), new CEquals(t_int_av, T_INT_AV_TRES_LENTE));
   //     ((vitesse_vehicule >= 10 & vitesse_vehicule < 50 & type_vitesse = ascendant &
    //            t_int_av /= T_REDUC_PV_INT & t_int_av /= T_INT_FIXE)
    //            => t_int_av = T_INT_AV_LENTE) &
        CBooleanExpression i11 = new COr(new CNot(new CAnd(new CGreater(vitesse_vehicule,new CNumber(9)),new CGreater(new CNumber(50),vitesse_vehicule),new CEquals(type_vitesse, ascendant),new CNot(new CEquals(t_int_av, T_REDUC_PV_INT)),new CNot(new CEquals(t_int_av, T_INT_FIXE)))), new CEquals(t_int_av, T_INT_AV_LENTE));
        //  ((vitesse_vehicule >= 50 & vitesse_vehicule < 90 & type_vitesse = ascendant &
    //            t_int_av /= T_REDUC_PV_INT & t_int_av /= T_INT_FIXE)
  //              => t_int_av = T_INT_AV_MOYENNE) &
        CBooleanExpression i12 = new COr(new CNot(new CAnd(new CGreater(vitesse_vehicule,new CNumber(49)),new CGreater(new CNumber(90),vitesse_vehicule),new CEquals(type_vitesse, ascendant),new CNot(new CEquals(t_int_av, T_REDUC_PV_INT)),new CNot(new CEquals(t_int_av, T_INT_FIXE)))), new CEquals(t_int_av, T_INT_AV_MOYENNE));
//        ((vitesse_vehicule >= 90 & type_vitesse = ascendant & t_int_av /= T_REDUC_PV_INT &
 //               t_int_av /= T_INT_FIXE) => t_int_av = T_INT_AV_RAPIDE) &
        CBooleanExpression i13 = new COr(new CNot(new CAnd(new CGreater(vitesse_vehicule,new CNumber(89)),new CEquals(type_vitesse, ascendant),new CNot(new CEquals(t_int_av, T_REDUC_PV_INT)),new CNot(new CEquals(t_int_av, T_INT_FIXE)))), new CEquals(t_int_av, T_INT_AV_RAPIDE));
        //    ((vitesse_vehicule < 5 & type_vitesse = descendant & t_int_av /= T_REDUC_PV_INT &
   //             t_int_av /= T_INT_FIXE) => t_int_av = T_INT_AV_TRES_LENTE) &
        CBooleanExpression i14 = new COr(new CNot(new CAnd(new CGreater(new CNumber(5),vitesse_vehicule),new CEquals(type_vitesse, descendant),new CNot(new CEquals(t_int_av, T_REDUC_PV_INT)),new CNot(new CEquals(t_int_av, T_INT_FIXE)))), new CEquals(t_int_av, T_INT_AV_TRES_LENTE));
       //     ((vitesse_vehicule >= 5 & vitesse_vehicule < 40 & type_vitesse = descendant &
   //             t_int_av /= T_REDUC_PV_INT & t_int_av /= T_INT_FIXE)
    //            => t_int_av = T_INT_AV_LENTE) &
        CBooleanExpression i15 = new COr(new CNot(new CAnd(new CGreater(vitesse_vehicule,new CNumber(4)),new CGreater(new CNumber(40),vitesse_vehicule),new CEquals(type_vitesse, descendant),new CNot(new CEquals(t_int_av, T_REDUC_PV_INT)),new CNot(new CEquals(t_int_av, T_INT_FIXE)))), new CEquals(t_int_av, T_INT_AV_LENTE));
        //   ((vitesse_vehicule >= 40 & vitesse_vehicule < 80 & type_vitesse = descendant &
      //          t_int_av /= T_REDUC_PV_INT & t_int_av /= T_INT_FIXE)
     //           => t_int_av = T_INT_AV_MOYENNE) &
        CBooleanExpression i16 = new COr(new CNot(new CAnd(new CGreater(vitesse_vehicule,new CNumber(39)),new CGreater(new CNumber(80),vitesse_vehicule),new CEquals(type_vitesse, descendant),new CNot(new CEquals(t_int_av, T_REDUC_PV_INT)),new CNot(new CEquals(t_int_av, T_INT_FIXE)))), new CEquals(t_int_av, T_INT_AV_MOYENNE));
        //   ((vitesse_vehicule >= 80 & type_vitesse = descendant & t_int_av /= T_REDUC_PV_INT &
     //           t_int_av /= T_INT_FIXE) => t_int_av = T_INT_AV_RAPIDE) &
        CBooleanExpression i17 = new COr(new CNot(new CAnd(new CGreater(vitesse_vehicule,new CNumber(79)),new CEquals(type_vitesse, descendant),new CNot(new CEquals(t_int_av, T_REDUC_PV_INT)),new CNot(new CEquals(t_int_av, T_INT_FIXE)))), new CEquals(t_int_av, T_INT_AV_RAPIDE));
        //(
        // (t_int_av = T_INT_FIXE & alimentation = accessoire) =>
        // (
        // (etat_cdp = c_defaillant or presence_CDP = false) &
        // (dmd_commodo = d_auto or etat_commodo = defaillant) &
      //  (etat_moteur_ess_av = mInter or etat_moteur_ess_av = protectionMoteur or etat_moteur_ess_av = pv3cyclesLavage)
      // )
      // )
        CBooleanExpression sub_i18 = new CAnd(new COr(new CEquals(etat_cdp, c_defaillant),new CEquals(presence_CDP, bfalse)),new COr(new CEquals(dmd_commodo, d_auto),new CEquals(etat_commodo, defaillant)),new COr(new CEquals(etat_moteur_ess_av, mInter),new CEquals(etat_moteur_ess_av, protectionMoteur),new CEquals(etat_moteur_ess_av, pv3cyclesLavage)));
        CBooleanExpression i18 = new COr(new CNot(new CAnd(new CEquals(t_int_av, T_INT_FIXE),new CEquals(alimentation, accessoire))), sub_i18);
    //  ((t_int_av = T_INT_FIXE & alimentation /= accessoire) => (etat_cdp = c_defaillant or
      //      presence_CDP = false) & (dmd_commodo = d_auto or etat_commodo = defaillant)) &
        CBooleanExpression sub_i19 = new CAnd(new COr(new CEquals(etat_cdp, c_defaillant),new CEquals(presence_CDP, bfalse)),new COr(new CEquals(dmd_commodo, d_auto),new CEquals(etat_commodo, defaillant)));
        CBooleanExpression i19 = new COr(new CNot(new CAnd(new CEquals(t_int_av, T_INT_FIXE),new CNot(new CEquals(alimentation, accessoire)))), sub_i19);
        //  (
        // (
        // (etat_cdp = c_defaillant & dmd_commodo = d_auto)
        // or
        // (etat_commodo = defaillant & (etat_cdp = c_defaillant or presence_CDP = false))
        // )
        // =>
        // t_int_av = T_INT_FIXE)  &
        CBooleanExpression sub_i20 = new COr(new CAnd(new CEquals(etat_cdp, c_defaillant),new CEquals(dmd_commodo, d_auto)),new CAnd(new CEquals(etat_commodo, defaillant),new COr(new CEquals(etat_cdp, c_defaillant),new CEquals(presence_CDP, bfalse))));
        CBooleanExpression i20 = new COr(new CNot(sub_i20), new CEquals(t_int_av, T_INT_FIXE));
     //   (
     // (etat_commodo = defaillant) => (seuil_depasse_vit2 = false)
     // )
     // &
        CBooleanExpression i21 = new COr(new CNot(new CEquals(etat_commodo, defaillant)), new CEquals(seuil_depasse_vit2, bfalse));
	//(
	// (alimentation = accessoire & etat_moteur_ess_av = arretMaintenance) => dmd_commodo = d_repos)
        CBooleanExpression i22 = new COr(new CNot(new CAnd(new CEquals(alimentation, accessoire),new CEquals(etat_moteur_ess_av, arretMaintenance))), new CEquals(dmd_commodo, repos));

        cMachine.setInvariant(new CAnd(typage,i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,i13,i14,i15,i16,i17,i18,i19,i20,i21));

        cMachine.setInit(new CMultipleAssignment(
                new CAssignment(dmd_commodo_user,repos),
                new CAssignment(dmd_commodo,d_repos),
                new CAssignment(etat_commodo,fonctionnel),
                new CAssignment(vitesse_vehicule,new CNumber(0)),
                new CAssignment(type_vitesse,ascendant),
                new CAssignment(alimentation,aucune),
                new CAssignment(etat_moteur_ess_av,arretFixeAv),
                new CAssignment(cdp,arretCDP),
                new CAssignment(etat_cdp,c_fonctionnel),
                new CAssignment(t_int_av,T_INT_AV_TRES_LENTE),
                new CAssignment(depassement_temps_maintenance,btrue),
                new CAssignment(seuil_depasse_vit1,bfalse),
                new CAssignment(seuil_depasse_vit2,bfalse),
                new CAssignment(t_perte_acc_depasse,btrue),
                new CAssignment(presence_CDP,btrue)
        ));

        CBooleanExpression pre_action_commodo_repos = new CAnd(new CEquals(etat_commodo, fonctionnel),new CNot(new CEquals(dmd_commodo_user, repos)));

        CSubstitution sub_action_commodo_repos1 =
                new CIf(new CEquals(alimentation, aucune),
                        new CIf(new CEquals(depassement_temps_maintenance, bfalse),
                                new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                        new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                        new CMultipleAssignment(new CAssignment(etat_moteur_ess_av,arretMaintenance),new CAssignment(dmd_commodo,d_repos))),
                                new CSkip()),
                        new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                new CMultipleAssignment(new CAssignment(dmd_commodo,d_repos),new CAssignment(etat_moteur_ess_av,arretFixeAv))));


        CSubstitution sub_action_commodo_repos2 = new CIf(
                new COr(new CEquals(t_int_av,T_REDUC_PV_INT),new CEquals(t_int_av,T_INT_FIXE)),
                new CIf(
                            new CEquals(type_vitesse,ascendant),
                            new CIf(
                                    new CGreater(new CNumber(10),vitesse_vehicule),
                                    new CAssignment(t_int_av,T_INT_AV_TRES_LENTE),
                                    new CIf(
                                            new CGreater(new CNumber(50),vitesse_vehicule),
                                            new CAssignment(t_int_av,T_INT_AV_LENTE),
                                            new CIf(
                                                    new CGreater(new CNumber(90),vitesse_vehicule),
                                                    new CAssignment(t_int_av,T_INT_AV_MOYENNE),
                                                    new CAssignment(t_int_av,T_INT_AV_RAPIDE)
                                                    )
                                    )
                            ),
                            new CIf(
                                    new CGreater(new CNumber(5),vitesse_vehicule),
                                    new CAssignment(t_int_av,T_INT_AV_TRES_LENTE),
                                    new CIf(
                                            new CGreater(new CNumber(40),vitesse_vehicule),
                                            new CAssignment(t_int_av,T_INT_AV_LENTE),
                                            new CIf(
                                                    new CGreater(new CNumber(80),vitesse_vehicule),
                                                    new CAssignment(t_int_av,T_INT_AV_MOYENNE),
                                                    new CAssignment(t_int_av,T_INT_AV_RAPIDE)
                                            )
                                    )
                            )
                ),
                new CSkip());


        //CSubstitution test = new CParallel(new CAssignment(alimentation,repos),new CIf( new CEquals(alimentation, aucune),new CAssignment(dmd_commodo_user,repos),new CSkip()));

        CEvent action_commodo_repos = new CGuardedEvent("action_commodo_repos",pre_action_commodo_repos,new CParallel(new CAssignment(dmd_commodo_user,repos),sub_action_commodo_repos1,sub_action_commodo_repos2));


        CBooleanExpression pre_action_commodo_pv = new CAnd(new CEquals(etat_commodo, fonctionnel),new CNot(new CEquals(dmd_commodo_user, pv)));
        CSubstitution sub_action_commodo_pv1 =
                new CIf(new CEquals(alimentation, aucune),
                        new CIf(new CEquals(depassement_temps_maintenance, bfalse),
                                new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                        new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                        new CMultipleAssignment(new CAssignment(etat_moteur_ess_av,arretMaintenance),new CAssignment(dmd_commodo,d_repos))),
                                new CSkip()),
                        new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                new CParallel(new CAssignment(dmd_commodo,d_pv), new CAssignment(etat_moteur_ess_av,mpv), new CAssignment(seuil_depasse_vit1,bfalse),  new CIf(new CGreater(vitesse_vehicule,VIT2),
                                                                                                                                                                               new CAssignment(seuil_depasse_vit2,btrue),
                                                                                                                                                                               new CAssignment(seuil_depasse_vit2,bfalse)) ) ));
        CEvent action_commodo_pv = new CGuardedEvent("action_commodo_pv",pre_action_commodo_pv,new CParallel(new CAssignment(dmd_commodo_user,pv),sub_action_commodo_pv1,sub_action_commodo_repos2));

        CBooleanExpression pre_action_commodo_gv = new CAnd(new CEquals(etat_commodo, fonctionnel),new CNot(new CEquals(dmd_commodo_user, gv)));
        CSubstitution sub_action_commodo_gv1 =
                new CIf(new CEquals(alimentation, aucune),
                        new CIf(new CEquals(depassement_temps_maintenance, bfalse),
                                new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                        new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                        new CMultipleAssignment(new CAssignment(etat_moteur_ess_av,arretMaintenance),new CAssignment(dmd_commodo,d_repos))),
                                new CSkip()),
                        new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                new CParallel(new CAssignment(dmd_commodo,d_gv), new CAssignment(etat_moteur_ess_av,mgv), new CAssignment(seuil_depasse_vit1,bfalse),  new CIf(new CGreater(vitesse_vehicule,VIT2),
                                        new CAssignment(seuil_depasse_vit2,btrue),
                                        new CAssignment(seuil_depasse_vit2,bfalse)) ) ));
        CEvent action_commodo_gv = new CGuardedEvent("action_commodo_gv",pre_action_commodo_gv,new CParallel(new CAssignment(dmd_commodo_user,gv),sub_action_commodo_gv1,sub_action_commodo_repos2));

        CBooleanExpression pre_action_commodo_interm = new CAnd(new CEquals(etat_commodo, fonctionnel),new CNot(new CEquals(dmd_commodo_user, interm)));
        CSubstitution sub_action_commodo_interm1 =
                new CIf(new CEquals(alimentation, aucune),
                        new CIf(new CEquals(depassement_temps_maintenance, bfalse),
                                new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                        new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                        new CMultipleAssignment(new CAssignment(etat_moteur_ess_av,arretMaintenance),new CAssignment(dmd_commodo,d_repos))),
                                new CSkip()),
                        new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                new CParallel(new CAssignment(dmd_commodo,d_interm), new CAssignment(etat_moteur_ess_av,mInter) ) ));
        CEvent action_commodo_interm = new CGuardedEvent("action_commodo_interm",pre_action_commodo_interm,new CParallel(new CAssignment(dmd_commodo_user,interm),sub_action_commodo_interm1,sub_action_commodo_repos2));

        CBooleanExpression pre_action_commodo_auto = new CAnd(new CEquals(etat_commodo, fonctionnel),new CNot(new CEquals(dmd_commodo_user, auto)),new CEquals(presence_CDP, btrue));
        CSubstitution sub_action_commodo_auto =
                new CIf(new CEquals(alimentation, aucune),
                        new CIf(new CEquals(depassement_temps_maintenance, bfalse),
                                new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                        new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                        new CMultipleAssignment(new CAssignment(etat_moteur_ess_av,arretMaintenance),new CAssignment(dmd_commodo,d_repos))),
                                new CSkip()),
                        new CIf(new CEquals(etat_moteur_ess_av, arretMaintenance),
                                new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                new CParallel(new CAssignment(dmd_commodo,d_auto), new CIf(new CEquals(etat_cdp,c_fonctionnel),
                                        new CAssignment(etat_moteur_ess_av,pv1cycle),
                                        new CAssignment(etat_moteur_ess_av,mInter)) ) ));
        CEvent action_commodo_auto = new CGuardedEvent("action_commodo_auto",pre_action_commodo_auto,new CParallel(new CAssignment(dmd_commodo_user,auto),sub_action_commodo_auto,sub_action_commodo_repos2));

        CBooleanExpression pre_action_lavage_deb = new CAnd(new CEquals(alimentation, fonctionnel), new CEquals(alimentation, accessoire));
        CSubstitution sub_action_lavage_deb =
                new CIf(new CNot(new CEquals(etat_moteur_ess_av, protectionMoteur)),
                        new CIf(new COr(new CEquals(etat_moteur_ess_av, mgv),new CEquals(etat_moteur_ess_av, gv3cycles)),
                                new CAssignment(etat_moteur_ess_av, gv3cyclesLavage),
                                new CAssignment(etat_moteur_ess_av, pv3cyclesLavage)),
                        new CSkip());
        CEvent action_lavage_deb = new CGuardedEvent("action_lavage_deb",pre_action_lavage_deb,sub_action_lavage_deb);

        CBooleanExpression pre_action_lavage_fin = new CAnd(new COr(new CEquals(etat_moteur_ess_av, pv3cyclesLavage),new CEquals(etat_moteur_ess_av, gv3cyclesLavage)), new CEquals(alimentation, accessoire));
        CSubstitution sub_action_lavage_fin =
                new CIf(new CNot(new CEquals(etat_moteur_ess_av, protectionMoteur)),
                        new CIf(new CNot(new CEquals(t_int_av, T_INT_FIXE)),
                                new CIf(new CEquals(dmd_commodo, d_repos),
                                        new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                        new CIf(new CEquals(dmd_commodo, d_pv),
                                                new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue)),
                                                    new CAssignment(etat_moteur_ess_av, mInter),
                                                    new CAssignment(etat_moteur_ess_av, mpv)),
                                                new CIf(new CEquals(dmd_commodo, d_gv),
                                                        new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue)),
                                                                new CAssignment(etat_moteur_ess_av, mpv),
                                                                new CAssignment(etat_moteur_ess_av, mgv)),
                                                        new CIf(new CEquals(dmd_commodo, d_interm),
                                                                new CAssignment(etat_moteur_ess_av, mInter),
                                                                new CAssignment(etat_moteur_ess_av, arretFixeAv))))),
                                new CAssignment(etat_moteur_ess_av, mInter)),
                        new CSkip());
        CEvent action_lavage_fin = new CGuardedEvent("action_lavage_fin",pre_action_lavage_fin,sub_action_lavage_fin);


        CEvent action_pvi_deb = new CGuardedEvent("action_pvi_deb",pre_action_lavage_deb,new CIf(new CEquals(etat_moteur_ess_av, arretFixeAv),
                                                                                                new CAssignment(etat_moteur_ess_av, pv1cycle),
                                                                                                new CSkip()));

        CBooleanExpression pre_action_pvi_fin = new CAnd(new CEquals(etat_moteur_ess_av, pv1cycle), new CEquals(alimentation, accessoire), new CEquals(dmd_commodo, d_repos), new CEquals(etat_cdp, c_fonctionnel), new CEquals(etat_commodo, fonctionnel));

        CSubstitution sub_action_pvi_fin =
                new CIf(new CNot(new CEquals(etat_moteur_ess_av, protectionMoteur)),
                        new CIf(new CEquals(dmd_commodo, d_repos),
                                new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                new CIf(new CEquals(dmd_commodo, d_pv),
                                        new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue)),
                                                new CAssignment(etat_moteur_ess_av, mInter),
                                                new CAssignment(etat_moteur_ess_av, mpv)),
                                        new CIf(new CEquals(dmd_commodo, d_gv),
                                                new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue)),
                                                        new CAssignment(etat_moteur_ess_av, mpv),
                                                        new CAssignment(etat_moteur_ess_av, mgv)),
                                                new CIf(new CEquals(dmd_commodo, d_interm),
                                                        new CAssignment(etat_moteur_ess_av, mInter),
                                                        new CAssignment(etat_moteur_ess_av, arretFixeAv))))),
                        new CSkip());

        CEvent action_pvi_fin = new CGuardedEvent("action_pvi_fin",pre_action_pvi_fin,sub_action_pvi_fin);

        CSubstitution sub_action_commodo_defaillance_deb  =
                new CIf(new CAnd(new CEquals(alimentation, accessoire),new CEquals(etat_cdp, c_fonctionnel),new CNot(new CEquals(etat_moteur_ess_av, protectionMoteur)),new CEquals(presence_CDP, btrue)),
                        new CAssignment(etat_moteur_ess_av, pv1cycle),
                        new CIf(new COr(new CEquals(etat_cdp, c_defaillant),new CEquals(presence_CDP, bfalse)),
                                new CIf(new CAnd(new CEquals(alimentation, accessoire),new CNot(new CEquals(etat_moteur_ess_av, protectionMoteur))),
                                        new CAssignment(etat_moteur_ess_av, mInter),
                                        new CSkip()),
                                new CSkip())
                                );
        CEvent action_commodo_defaillance_deb = new CGuardedEvent("action_commodo_defaillance_deb",new CEquals(etat_commodo, fonctionnel),new CParallel(new CAssignment(etat_commodo,defaillant),new CAssignment(seuil_depasse_vit1,bfalse),new CAssignment(seuil_depasse_vit2,bfalse),sub_action_commodo_defaillance_deb));

        CSubstitution sub_action_commodo_defaillance_fin1  =
                new CIf(new CAnd(new CEquals(alimentation, accessoire),new CNot(new CEquals(etat_moteur_ess_av, protectionMoteur))),
                        new CIf(new CEquals(dmd_commodo, d_repos),
                                new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                new CIf(new CEquals(dmd_commodo, d_pv),
                                        new CAssignment(etat_moteur_ess_av, mpv),
                                        new CIf(new CEquals(dmd_commodo, d_gv),
                                                new CAssignment(etat_moteur_ess_av, mgv),
                                                new CIf(new CEquals(dmd_commodo, d_interm),
                                                        new CAssignment(etat_moteur_ess_av, mInter),
                                                        new CIf(new CAnd(new CEquals(dmd_commodo, d_auto),new CEquals(etat_cdp, c_defaillant)),
                                                                new CAssignment(etat_moteur_ess_av, mInter),
                                                                new CAssignment(etat_moteur_ess_av, pv1cycle))))))
                        ,new CSkip());
        CSubstitution sub_action_commodo_defaillance_fin2  =
                new CIf(new CAnd(new COr(new CEquals(dmd_commodo, d_gv),new CEquals(dmd_commodo, d_pv)),new CGreater(vitesse_vehicule, VIT2)),
                        new CAssignment(seuil_depasse_vit2, btrue),
                        new CAssignment(seuil_depasse_vit2, bfalse)
                        );
        CSubstitution sub_action_commodo_defaillance_fin3  =
                new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(dmd_commodo, d_pv),new CEquals(seuil_depasse_vit1, btrue)),
                        new CAssignment(t_int_av, T_REDUC_PV_INT),
                        new CIf(new CAnd(new CEquals(etat_cdp, c_defaillant),new CEquals(dmd_commodo, d_auto)),
                                new CAssignment(t_int_av, T_INT_FIXE),
                                new CIf(
                                        new CEquals(type_vitesse,ascendant),
                                        new CIf(
                                                new CGreater(new CNumber(10),vitesse_vehicule),
                                                new CAssignment(t_int_av,T_INT_AV_TRES_LENTE),
                                                new CIf(
                                                        new CGreater(new CNumber(50),vitesse_vehicule),
                                                        new CAssignment(t_int_av,T_INT_AV_LENTE),
                                                        new CIf(
                                                                new CGreater(new CNumber(90),vitesse_vehicule),
                                                                new CAssignment(t_int_av,T_INT_AV_MOYENNE),
                                                                new CAssignment(t_int_av,T_INT_AV_RAPIDE)
                                                        )
                                                )
                                        ),
                                        new CIf(
                                                new CGreater(new CNumber(5),vitesse_vehicule),
                                                new CAssignment(t_int_av,T_INT_AV_TRES_LENTE),
                                                new CIf(
                                                        new CGreater(new CNumber(40),vitesse_vehicule),
                                                        new CAssignment(t_int_av,T_INT_AV_LENTE),
                                                        new CIf(
                                                                new CGreater(new CNumber(80),vitesse_vehicule),
                                                                new CAssignment(t_int_av,T_INT_AV_MOYENNE),
                                                                new CAssignment(t_int_av,T_INT_AV_RAPIDE)
                                                        )
                                                )
                                        )
                                )
                                ));

        CEvent action_commodo_defaillance_fin = new CGuardedEvent("action_commodo_defaillance_fin",new CEquals(etat_commodo, defaillant),new CParallel(new CAssignment(etat_commodo,fonctionnel),sub_action_commodo_defaillance_fin1,sub_action_commodo_defaillance_fin2,sub_action_commodo_defaillance_fin3));

        CSubstitution sub_action_cdp_defaillant_deb =
                new CIf(new COr(new CEquals(etat_commodo,defaillant),new CEquals(dmd_commodo,d_auto)),
                        new CParallel(new CAssignment(t_int_av, T_INT_FIXE),new CIf(new CAnd(new CEquals(alimentation, accessoire),new CNot(new CEquals(etat_moteur_ess_av, protectionMoteur))),
                                new CAssignment(etat_moteur_ess_av, mInter),
                                new CSkip())),
                        new CSkip());

        CEvent action_cdp_defaillant_deb = new CGuardedEvent("action_cdp_defaillant_deb",new CAnd(new CEquals(etat_cdp, c_fonctionnel),new CEquals(presence_CDP, btrue)),new CParallel(new CAssignment(etat_cdp,c_defaillant),sub_action_cdp_defaillant_deb));


        CBooleanExpression pre_action_cdp_defaillant_fin = new CAnd(new CEquals(etat_cdp, c_defaillant), new CEquals(presence_CDP, btrue));

        CSubstitution sub_action_cdp_defaillant_fin =
                new CIf(new CAnd(new CEquals(alimentation,accessoire),new CNot(new CEquals(etat_commodo,defaillant)),new CNot(new CEquals(etat_moteur_ess_av,protectionMoteur))),
                        new CIf(new CEquals(dmd_commodo,d_repos),
                                new CAssignment(etat_moteur_ess_av,arretFixeAv),
                                new CIf(new CEquals(dmd_commodo, d_pv),
                                        new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue)),
                                                new CAssignment(etat_moteur_ess_av, mInter),
                                                new CAssignment(etat_moteur_ess_av, mpv)),
                                        new CIf(new CEquals(dmd_commodo, d_gv),
                                                new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue)),
                                                        new CAssignment(etat_moteur_ess_av, mpv),
                                                        new CAssignment(etat_moteur_ess_av, mgv)),
                                                new CIf(new CEquals(dmd_commodo, d_interm),
                                                        new CAssignment(etat_moteur_ess_av, mInter),
                                                        new CAssignment(etat_moteur_ess_av, arretFixeAv))))) ,
                        new CIf(new CAnd(new CEquals(alimentation,accessoire),new CNot(new CEquals(etat_moteur_ess_av,protectionMoteur))),
                                new CAssignment(etat_moteur_ess_av, pv1cycle),
                                new CSkip())
                              );
        CEvent action_cdp_defaillant_fin = new CGuardedEvent("action_cdp_defaillant_fin",pre_action_cdp_defaillant_fin,new CParallel(new CAssignment(etat_cdp,c_fonctionnel),sub_action_cdp_defaillant_fin,sub_action_commodo_defaillance_fin3));

        CEvent bloquer_le_moteur_ess_av = new CGuardedEvent("bloquer_le_moteur_ess_av",new CNot(new CEquals(etat_moteur_ess_av,protectionMoteur)),new CAssignment(etat_moteur_ess_av,protectionMoteur));

        CEvent depasser_temps_maintenance = new CGuardedEvent("depasser_temps_maintenance",new CAnd(new CEquals(depassement_temps_maintenance,bfalse),new CEquals(alimentation,aucune)),new CAssignment(depassement_temps_maintenance,btrue));

        CEvent depasser_temps_perte_acc = new CGuardedEvent("depasser_temps_perte_acc",new CAnd(new CEquals(t_perte_acc_depasse,bfalse),new CEquals(alimentation,aucune)),new CAssignment(t_perte_acc_depasse,btrue));

        CSubstitution sub_alimentation_acc =
                new CIf(new CAnd(new CEquals(alimentation,aucune),new CEquals(t_perte_acc_depasse,btrue)),
                        new CParallel(
                            new CIf(new CAnd(new CNot(new CEquals(etat_moteur_ess_av,protectionMoteur)),new CNot(new CEquals(etat_moteur_ess_av,arretMaintenance))),
                                    new CIf(new CEquals(t_int_av, T_INT_FIXE),
                                            new CIf(new CEquals(dmd_commodo, d_auto),
                                                    new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                                    new CAssignment(etat_moteur_ess_av, mInter)),
                                            new CIf(new CEquals(etat_commodo, defaillant),
                                                    new CAssignment(etat_moteur_ess_av, pv1cycle),
                                                    new CAssignment(etat_moteur_ess_av, arretFixeAv))),
                                    new CIf(new CAnd(new CEquals(t_int_av, T_INT_FIXE),new CEquals(etat_moteur_ess_av, arretMaintenance),new CNot(new CEquals(dmd_commodo,d_auto))),
                                            new CAssignment(etat_moteur_ess_av, mInter),
                                            new CSkip())),
                            new CIf(new COr(new CAnd(new CEquals(t_int_av,T_INT_FIXE),new CEquals(dmd_commodo,d_auto)),new CEquals(t_int_av,T_REDUC_PV_INT)),
                                    new CAssignment(t_int_av,T_INT_AV_TRES_LENTE),
                                    new CSkip()),
                            new CAssignment(dmd_commodo,d_repos),
                            new CAssignment(seuil_depasse_vit1,bfalse),
                            new CAssignment(seuil_depasse_vit2,bfalse)
                        ),
                        new CIf(new CAnd(new CNot(new CEquals(etat_moteur_ess_av,protectionMoteur)),new CNot(new CEquals(etat_moteur_ess_av,arretMaintenance))),
                                new CIf(new CEquals(t_int_av, T_INT_FIXE),
                                        new CAssignment(etat_moteur_ess_av, mInter),
                                        new CIf(new CEquals(etat_commodo, defaillant),
                                                new CAssignment(etat_moteur_ess_av, pv1cycle),
                                                new CIf(new CEquals(dmd_commodo, d_repos),
                                                        new CAssignment(etat_moteur_ess_av, arretFixeAv),
                                                        new CIf(new CEquals(dmd_commodo, d_pv),
                                                                new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue)),
                                                                        new CAssignment(etat_moteur_ess_av, mInter),
                                                                        new CAssignment(etat_moteur_ess_av, mpv)),
                                                                new CIf(new CEquals(dmd_commodo, d_gv),
                                                                        new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue)),
                                                                                new CAssignment(etat_moteur_ess_av, mpv),
                                                                                new CAssignment(etat_moteur_ess_av, mgv)),
                                                                        new CIf(new CEquals(dmd_commodo, d_interm),
                                                                                new CIf(new CAnd(new CEquals(seuil_depasse_vit2, btrue),new CEquals(seuil_depasse_vit1, btrue)),
                                                                                        new CAssignment(etat_moteur_ess_av, mInter),
                                                                                        new CAssignment(etat_moteur_ess_av, pv1cycle)),
                                                                                new CSkip())))))),
                                new CIf(new CAnd(new CEquals(etat_moteur_ess_av, arretMaintenance),new CEquals(t_int_av, T_INT_FIXE),new CNot(new CEquals(dmd_commodo, d_auto))),
                                        new CAssignment(etat_moteur_ess_av, mInter),
                                        new CSkip()))


                );
        CEvent alimentation_acc = new CGuardedEvent("alimentation_acc",new CAnd(new CNot(new CEquals(alimentation,accessoire)),new CEquals(vitesse_vehicule,new CNumber(0))),new CParallel(new CAssignment(alimentation,accessoire),new CAssignment(t_perte_acc_depasse,bfalse),new CAssignment(depassement_temps_maintenance,bfalse),sub_alimentation_acc));

        CEvent alimentation_dem = new CGuardedEvent("alimentation_dem",new CAnd(new CEquals(alimentation,accessoire),new CEquals(vitesse_vehicule,new CNumber(0))),new CParallel(new CAssignment(alimentation,demarreur),new CAssignment(t_perte_acc_depasse,bfalse),new CAssignment(depassement_temps_maintenance,bfalse),new CIf(new CNot(new CEquals(etat_moteur_ess_av
        ,protectionMoteur)),new CAssignment(etat_moteur_ess_av,suspendu),new CSkip())));

        CEvent alimentation_coupe = new CGuardedEvent("alimentation_dem",new CAnd(new CEquals(alimentation,accessoire),new CEquals(vitesse_vehicule,new CNumber(0))),new CParallel(new CAssignment(alimentation,aucune),new CAssignment(t_perte_acc_depasse,bfalse),new CAssignment(depassement_temps_maintenance,bfalse),new CIf(new CNot(new CEquals(etat_moteur_ess_av
                ,protectionMoteur)),new CAssignment(etat_moteur_ess_av,arretFixeAv),new CSkip())));

        cMachine.setEvents(action_commodo_repos,action_commodo_pv,action_commodo_gv,action_commodo_interm,
                action_lavage_deb,action_lavage_fin,
                action_pvi_deb,action_pvi_fin,
                action_commodo_defaillance_deb,action_commodo_defaillance_fin,
                action_cdp_defaillant_deb,action_cdp_defaillant_fin,
                bloquer_le_moteur_ess_av,depasser_temps_maintenance,depasser_temps_perte_acc,
                alimentation_acc,alimentation_dem);



        return cMachine;
    }

    public static CMachine getSmallIllustrativeMachine()
    {
        CMachine cMachine = new CMachine("Small Illustrative Model");

        CVariable var_pc = new CVariable("pc");
        CVariable var_x = new CVariable("x");
        cMachine.setVariables(var_pc,var_x);

        cMachine.setInvariant(new CAnd(new CInDomain(var_pc, CSet.NATURAL),new CGreater(new CNumber(4), var_pc)));

        CVariable local_z = new CVariable("z");
        cMachine.setInit(new CAny(new CEquals(local_z,local_z),new CMultipleAssignment(new CAssignment(var_pc,new CNumber(0)),new CAssignment(var_x,local_z))));

        CEvent e1 = new CGuardedEvent("e1",new CAnd(new CEquals(var_pc,new CNumber(0)),new COr(new CGreater(var_x,new CNumber(1)),new CEquals(var_x,new CNumber(2)))),new CMultipleAssignment(new CAssignment(var_pc,new CNumber(1)),new CAssignment(var_x,new CPlus(var_x,new CNumber(1)))));

        CEvent e2 = new CGuardedEvent("e2",new CAnd(new CEquals(var_pc,new CNumber(0)),new COr(new CGreater(var_x,new CNumber(3)),new CEquals(var_x,new CNumber(3)))),new CMultipleAssignment(new CAssignment(var_pc,new CNumber(1)),new CAssignment(var_x,new CMinus(var_x,new CNumber(1)))));

        CEvent e3 = new CGuardedEvent("e3",new CAnd(new CEquals(var_pc,new CNumber(1)),new CGreater(var_x,new CNumber(3))), new CMultipleAssignment(new CAssignment(var_pc,new CNumber(2)),new CAssignment(var_x,new CPlus(var_x,new CNumber(1)))));

        CEvent e4 = new CGuardedEvent("e4",new CAnd(new CEquals(var_pc,new CNumber(1)),new COr(new CGreater(var_x,new CNumber(3)),new CEquals(var_x,new CNumber(3)))),new CMultipleAssignment(new CAssignment(var_pc,new CNumber(2)),new CAssignment(var_x,new CMinus(var_x,new CNumber(1)))));

        CEvent e5 = new CGuardedEvent("e5",new CEquals(var_pc,new CNumber(2)), new CAssignment(var_pc,new CNumber(3)));


        cMachine.setEvents(e1,e2,e3,e4,e5);

        return cMachine;
    }

    public static CMachine getSASMachine()
    {
        CMachine cMachine = new CMachine("SasDecompression");

        CVariable var_pression_sas = new CVariable("pression_sas"); /* pression dans le sas */
        CVariable var_pression_int = new CVariable("pression_int"); /* pression à l'intérieur */
        CVariable var_porte_sas = new CVariable("porte_sas"); /* porte sas <-> extérieur */
        CVariable var_porte_int = new CVariable("porte_int"); /* porte sas <-> intérieur */

        cMachine.setVariables(var_pression_sas,var_pression_int,var_porte_sas,var_porte_int);

        CNumber nFermee = new CNumber(0);
        CNumber nOuvert = new CNumber(1);
       /* INVARIANT
        pression_sas : NATURAL
                & pression_int : NATURAL
            & porte_sas : PORTE
            & porte_int : PORTE
            & (porte_int = Ouverte => porte_sas = Fermee)
        & (porte_int = Ouverte => pression_sas = 10)
        & (porte_int = Ouverte => pression_int = 10)
        & (pression_int = 10)*/
        CBooleanExpression i1 =  new CInDomain(var_pression_sas, CSet.NATURAL);
        CBooleanExpression i2 =  new CInDomain(var_pression_int, CSet.NATURAL);
        CBooleanExpression i3 =  new CGreater(new CNumber(2), var_porte_sas);
        CBooleanExpression i4 =  new CGreater(new CNumber(2), var_porte_int);
        CBooleanExpression i5 =  new COr(new CNot(new CEquals(var_porte_int, nOuvert)), new CEquals(var_porte_sas, nFermee));
        CBooleanExpression i6 =  new COr(new CNot(new CEquals(var_porte_int, nOuvert)), new CEquals(var_pression_sas, new CNumber(10)));
        CBooleanExpression i7 =  new COr(new CNot(new CEquals(var_porte_int, nOuvert)), new CEquals(var_pression_int, new CNumber(10)));
        CBooleanExpression i8 =  new CEquals(var_pression_int, new CNumber(10));
        cMachine.setInvariant(new CAnd(i1,i2,i3,i4,i5,i6,i7,i8));

        cMachine.setInit(new CMultipleAssignment(new CAssignment(var_pression_sas,new CNumber(0)),new CAssignment(var_pression_int,new CNumber(10)),new CAssignment(var_porte_sas,nFermee),new CAssignment(var_porte_int,nFermee)));


        CEvent modif_pression_sas = new CGuardedEvent("modif_pression_sas",new CEquals(var_porte_sas,nOuvert),new CAssignment(var_pression_sas,new CNumber(0)));

        CEvent modif_pression_int = new CGuardedEvent("modif_pression_int",new CEquals(var_porte_int,nOuvert),new CAssignment(var_pression_int,var_pression_sas));

        CEvent augmentation_pression_sas = new CGuardedEvent("augmentation_pression_sas",new CAnd(new CEquals(var_porte_int,nFermee),new CEquals(var_porte_sas,nFermee),new CGreater(new CNumber(10),var_pression_sas)),new CAssignment(var_pression_sas,new CPlus(var_pression_sas,new CNumber(1))));

        CEvent ouverture_sas = new CGuardedEvent("ouverture_sas",new CEquals(var_porte_int,nFermee),new CAssignment(var_porte_sas,nOuvert));
        CEvent fermeture_sas = new CNonGuardedEvent("fermeture_sas",new CAssignment(var_porte_sas,nFermee));
        CEvent ouverture_int = new CGuardedEvent("ouverture_int",new CAnd(new CEquals(var_pression_sas,var_pression_int),new CEquals(var_porte_sas,nFermee)),new CAssignment(var_porte_int,nOuvert));
        CEvent fermeture_int = new CNonGuardedEvent("fermeture_int",new CAssignment(var_porte_int,nFermee));

        cMachine.setEvents(modif_pression_sas,modif_pression_int,augmentation_pression_sas,ouverture_sas,fermeture_sas,ouverture_int,fermeture_int);

        return cMachine;
    }

    public static CMachine getxOPyMachine()
    {
        CMachine cMachine = new CMachine("xOPy");

        CVariable var_x = new CVariable("x");
        CVariable var_y = new CVariable("y");

        cMachine.setVariables(var_x,var_y);

        CBooleanExpression Invariant = new CAnd(new CInDomain(var_x, CSet.NATURAL),new CInDomain(var_y,CSet.NATURAL));
        cMachine.setInvariant(Invariant);

        //init event
        CVariable var_local_x = new CVariable("local_x");
        CVariable var_local_y = new CVariable("local_y");
        CSubstitution InitSubstitution = new CMultipleAssignment(new CAssignment(var_x,var_local_x),new CAssignment(var_y,var_local_y));
        CEvent init = new CAnyEvent("init",Invariant,InitSubstitution,var_local_x,var_local_y);

        //xPLUSy
        CEvent xEQx = new CGuardedEvent("xEQx",new CInDomain(var_x,CSet.NATURAL),new CAssignment(var_x,var_x)) ;
        CEvent xPLUSy = new CNonGuardedEvent("xPLUSy",new CAssignment(var_x,new CPlus(var_x,var_y))) ;
        CEvent xMINUSy = new CGuardedEvent("xMINUSy",new CGreater(var_x,var_y),new CAssignment(var_x,new CMinus(var_x,var_y))) ;

        cMachine.setEvents(init,xEQx,xPLUSy,xMINUSy);

        /*CAbstraction cAbstraction = new CPredicateAbstraction(cMachine,new CInDomain(var_x,CSet.NATURAL));
        cMachine.setAbstraction(cAbstraction); */

        return cMachine;
    }
    public static CMachine getExample1Machine()
    {
        CMachine cMachine = new CMachine("ExempleSys");  //Déclaration du nouveau système

        CVariable var_x = new CVariable("x");
        CVariable var_y = new CVariable("y");

        cMachine.setVariables(var_x,var_y);    //Définition des variables du système

        CBooleanExpression Invariant = new CAnd(new CInDomain(var_x, CSet.NATURAL),new CInDomain(var_y,CSet.NATURAL));
        cMachine.setInvariant(Invariant);      //Définition de l'invariant du système

        CVariable var_local_x = new CVariable("local_x");
        CVariable var_local_y = new CVariable("local_y");
        CSubstitution InitSubstitution = new CMultipleAssignment(
                                                            new CAssignment(var_x,var_local_x),
                                                            new CAssignment(var_y,var_local_y)
                                                      );
        //Définition de la substutution d'initialisation du système
        cMachine.setInit(new CAny(Invariant,InitSubstitution,var_local_x,var_local_y));

        CSubstitution subAsubstitution = new CMultipleAssignment(
                                                            new CAssignment(var_x,var_local_x),
                                                            new CAssignment(var_y,var_local_y)
                                                      );
        //Définition des événements du système
        CAny subA = new CAny(new COr(new CGreater(var_local_y,var_local_x),new CEquals(var_local_y,var_local_x)),
                              subAsubstitution,
                              var_local_x,var_local_y
                            );

        CEvent A = new CGuardedEvent("A",new CGreater(var_x,var_y),subA) ;
        CEvent B = new CGuardedEvent("B",new COr(new CGreater(var_y,var_x),new CEquals(var_y,var_x)),
                                         new CAssignment(var_x,new CPlus(var_y,new CNumber(1)))
                                     ) ;
        CEvent C = new CGuardedEvent("C",new CAnd(new CEquals(var_x,new CNumber(7)),new CEquals(var_y,new CNumber(11))),
                                         new CAssignment(var_x,new CNumber(17))
                                    );
        cMachine.setEvents(A,B,C);
        //Définition et application de l'abstraction
        /*CAbstraction cAbstraction = new CPredicateTriModalsAbstraction(cMachine,new CGreater(var_x,var_y));
        cMachine.setAbstraction(cAbstraction);    */

        return cMachine;
    }
    public static CMachine getExample2Machine()
    {
        CMachine cMachine = new CMachine("ExempleSys2");  //Déclaration du nouveau système

        CVariable var_x = new CVariable("x");
        CVariable var_y = new CVariable("y");

        cMachine.setVariables(var_x,var_y);    //Définition des variables du système

        CBooleanExpression Invariant = new CAnd(new CInDomain(var_x, CSet.NATURAL),new CInDomain(var_y,CSet.NATURAL));
        cMachine.setInvariant(Invariant);      //Définition de l'invariant du système

        CVariable var_local_x = new CVariable("local_x");
        CVariable var_local_y = new CVariable("local_y");
        CSubstitution InitSubstitution = new CMultipleAssignment(
                new CAssignment(var_x,var_local_x),
                new CAssignment(var_y,var_local_y)
        );
        //Définition de la substutution d'initialisation du système
        cMachine.setInit(new CAny(Invariant,InitSubstitution,var_local_x,var_local_y));

        CSubstitution subAsubstitution = new CMultipleAssignment(
                new CAssignment(var_x,var_local_x),
                new CAssignment(var_y,var_local_y)
        );
        //Définition des événements du système
        CSubstitution subA = new CMultipleAssignment(
                new CAssignment(var_x,var_y),
                new CAssignment(var_y,new CMult(var_x,var_y))
        );

        CEvent A = new CGuardedEvent("A",new CGreater(var_x,var_y),subA) ;
        CEvent B = new CGuardedEvent("B",new COr(new CGreater(var_y,var_x),new CEquals(var_y,var_x)),
                new CAssignment(var_x,new CPlus(var_y,new CNumber(1)))
        ) ;
        CEvent C = new CGuardedEvent("C",new CAnd(new CEquals(var_x,new CNumber(7)),new CEquals(var_y,new CNumber(11))),
                new CAssignment(var_x,new CNumber(17))
        );
        cMachine.setEvents(A,B,C);
        //Définition et application de l'abstraction
      /*  CAbstraction cAbstraction = new CPredicateTriModalsAbstraction(cMachine,new CGreater(var_x,var_y));
        cMachine.setAbstraction(cAbstraction);   */

        return cMachine;
    }
    public static CMachine getSimpleRobotMachine()
    {
        CMachine cMachine = new CMachine("RobotSimple");

        CVariable var_De = new CVariable("De"); /* 1 == lib , 2 == occ */
        CVariable var_Dt = new CVariable("Dt");

        cMachine.setVariables(var_De,var_Dt);

        CBooleanExpression Invariant = new CAnd(
                new CInDomain(var_De, CSet.NATURAL),
                new CInDomain(var_Dt,CSet.NATURAL),
                new CGreater(var_De,new CNumber(0)),
                new CGreater(new CNumber(3),var_De),
                new CGreater(var_Dt,new CNumber(0)),
                new CGreater(new CNumber(3),var_Dt)
        );
        cMachine.setInvariant(Invariant);

        CSubstitution subInit = new CMultipleAssignment(new CAssignment(var_Dt,new CNumber(1)),new CAssignment(var_De,new CNumber(1)));
        cMachine.setInit(subInit);

        CEvent Charger = new CGuardedEvent("Charger",new CEquals(var_Dt,new CNumber(1)),new CAssignment(var_Dt,new CNumber(2)));
        CEvent Evacuer = new CGuardedEvent("Evacuer",new CEquals(var_De,new CNumber(2)),new CAssignment(var_De,new CNumber(1)));

        CEvent DeCharger = new CGuardedEvent("DeCharger",new CAnd(new CEquals(var_Dt,new CNumber(2)),new CEquals(var_De,new CNumber(1))),new CMultipleAssignment(new CAssignment(var_Dt,new CNumber(1)),new CAssignment(var_De,new CNumber(2))));

        cMachine.setEvents(Charger,Evacuer,DeCharger);

       /* CAbstraction cAbstraction = new CPredicateTriModalsAbstraction(cMachine,new CEquals(var_Dt,new CNumber(1)));
        cMachine.setAbstraction(cAbstraction);                   */

        //System.out.println(cMachine.toOutputString());

        return cMachine;
    }
    public static CMachine getArticle3batMachine()
    {
        CMachine cMachine = new CMachine("3 Batries");

        CVariable var_BatInUse = new CVariable("BatInUse");
        CVariable var_h = new CVariable("h"); /* 0 == tic , 1 == tac */
        CNumber tic = new CNumber(0);
        CNumber tac = new CNumber(1);
        CVariable var_Bat1 = new CVariable("Bat1"); /* 0 == ok , 1 == ko */
        CVariable var_Bat2 = new CVariable("Bat2");
        CVariable var_Bat3 = new CVariable("Bat3");
        CNumber ok = new CNumber(0);
        CNumber ko = new CNumber(1);
        CNumber num_1 = new CNumber(1);
        CNumber num_2 = new CNumber(2);
        CNumber num_3 = new CNumber(3);

        cMachine.setVariables(var_BatInUse,var_h,var_Bat1,var_Bat2,var_Bat3);

        //bat(sw)=ok
        CBooleanExpression i1 = new COr(new CAnd(new CEquals(var_BatInUse,num_1),new CEquals(var_Bat1,ok)),new CAnd(new CEquals(var_BatInUse,num_2),new CEquals(var_Bat2,ok)),new CAnd(new CEquals(var_BatInUse,num_3),new CEquals(var_Bat3,ok)));

        CBooleanExpression Invariant = new CAnd(
                new CInDomain(var_h, CSet.NATURAL),
                new CInDomain(var_Bat1,CSet.NATURAL),
                new CInDomain(var_Bat2, CSet.NATURAL),
                new CInDomain(var_Bat3,CSet.NATURAL),
                new CGreater(var_BatInUse,new CNumber(0)),
                new CGreater(new CNumber(4),var_BatInUse),
                new CGreater(new CNumber(2),var_h),
                new CGreater(new CNumber(2),var_Bat1),
                new CGreater(new CNumber(2),var_Bat2),
                new CGreater(new CNumber(2),var_Bat3),
                i1,
                new CNot(new CAnd(new CEquals(var_BatInUse,num_1),new CEquals(var_Bat1,ko))),
                new CNot(new CAnd(new CEquals(var_BatInUse,num_2),new CEquals(var_Bat2,ko))),
                new CNot(new CAnd(new CEquals(var_BatInUse,num_3),new CEquals(var_Bat3,ko)))
        );
        cMachine.setInvariant(Invariant);





        CSubstitution Init = new CMultipleAssignment(new CAssignment(var_BatInUse,num_1),new CAssignment(var_h,tac),new CAssignment(var_Bat1,ok),new CAssignment(var_Bat2,ok),new CAssignment(var_Bat3,ok));
        cMachine.setInit(Init);


        CEvent Tic = new CGuardedEvent("Tic",new CEquals(var_h,tac),new CAssignment(var_h,tic));

        CBooleanExpression deuxBatOK = new COr(new CAnd(new CEquals(var_Bat1,ok),new CEquals(var_Bat2,ok)),
                new CAnd(new CEquals(var_Bat1,ok),new CEquals(var_Bat3,ok)),
                new CAnd(new CEquals(var_Bat2,ok),new CEquals(var_Bat3,ok)));

        CEvent Fail =  new CNonGuardedEvent("Fail", new CGuarded(deuxBatOK,new CNDChoice(new CGuarded(new CAnd(new CEquals(var_Bat1,ok),new CNot(new CEquals(var_BatInUse,num_1))),new CAssignment(var_Bat1,ko)),new CGuarded(new CAnd(new CEquals(var_Bat3,ok),new CNot(new CEquals(var_BatInUse,num_3))),new CAssignment(var_Bat3,ko)),new CGuarded(new CAnd(new CEquals(var_Bat2,ok),new CNot(new CEquals(var_BatInUse,num_2))),new CAssignment(var_Bat2,ko)))));

        CSubstitution c1 = new CGuarded(new CAnd(new CEquals(var_Bat1,ok), new CNot(new CEquals(var_BatInUse,num_1))) ,new CMultipleAssignment(new CAssignment(var_BatInUse,num_1),new CAssignment(var_h,tac)));
        CSubstitution c2 = new CGuarded(new CAnd(new CEquals(var_Bat2,ok), new CNot(new CEquals(var_BatInUse,num_2))) ,new CMultipleAssignment(new CAssignment(var_BatInUse,num_2),new CAssignment(var_h,tac)));
        CSubstitution c3 = new CGuarded(new CAnd(new CEquals(var_Bat3,ok), new CNot(new CEquals(var_BatInUse,num_3))) ,new CMultipleAssignment(new CAssignment(var_BatInUse,num_3),new CAssignment(var_h,tac)));
        CEvent Commute =  new CGuardedEvent("Commute",new CAnd(deuxBatOK,new CEquals(var_h,tic)), new CNDChoice(c1,c2,c3));

        CEvent Rep =  new CNonGuardedEvent("Repair", new CNDChoice(new CGuarded(new CEquals(var_Bat1,ko),new CAssignment(var_Bat1,ok)),new CGuarded(new CEquals(var_Bat2,ko),new CAssignment(var_Bat2,ok)),new CGuarded(new CEquals(var_Bat3,ko),new CAssignment(var_Bat3,ok))));



        cMachine.setEvents(Tic,Fail,Commute,Rep);

        return cMachine;
    }
    public static CMachine get3batMachine()
    {
        CMachine cMachine = new CMachine("3 Batries");

        CVariable var_BatInUse = new CVariable("BatInUse");
        CVariable var_h = new CVariable("h"); /* 1 == tic , 2 == tac */
        CVariable var_Bat1 = new CVariable("Bat1"); /* 1 == ok , 2 == ko */
        CVariable var_Bat2 = new CVariable("Bat2");
        CVariable var_Bat3 = new CVariable("Bat3");

        cMachine.setVariables(var_BatInUse,var_h,var_Bat1,var_Bat2,var_Bat3);


        CBooleanExpression allKo = new CAnd(new CEquals(var_Bat1,new CNumber(2)),new CEquals(var_Bat2,new CNumber(2)),new CEquals(var_Bat3,new CNumber(2)));
        CBooleanExpression Invariant = new CAnd(
                new CInDomain(var_h, CSet.NATURAL),
                new CInDomain(var_Bat1,CSet.NATURAL),
                new CInDomain(var_Bat2, CSet.NATURAL),
                new CInDomain(var_Bat3,CSet.NATURAL),
                new CGreater(var_BatInUse,new CNumber(0)),
                new CGreater(new CNumber(4),var_BatInUse),
                new CGreater(var_h,new CNumber(0)),
                new CGreater(new CNumber(3),var_h),
                new CGreater(var_Bat1,new CNumber(0)),
                new CGreater(new CNumber(3),var_Bat1),
                new CGreater(var_Bat2,new CNumber(0)),
                new CGreater(new CNumber(3),var_Bat2),
                new CGreater(var_Bat3,new CNumber(0)),
                new CGreater(new CNumber(3),var_Bat3)
        );
        cMachine.setInvariant(Invariant);

        CNumber num_1 = new CNumber(1);
        CNumber num_2 = new CNumber(2);
        CNumber num_3 = new CNumber(3);

        CVariable var_local_x = new CVariable("local_x");


        CSubstitution subInit = new CMultipleAssignment(new CAssignment(var_BatInUse,var_local_x),new CAssignment(var_h,new CNumber(1)),new CAssignment(var_Bat1,num_1),new CAssignment(var_Bat2,num_1),new CAssignment(var_Bat3,num_1));
        CSubstitution Init = new CAny(new CAnd(new CGreater(new CNumber(4),var_local_x),new CGreater(var_local_x,new CNumber(0))),subInit,var_local_x);
        cMachine.setInit(Init);


        CEvent Tic = new CGuardedEvent("Tic",new CEquals(var_h,new CNumber(2)),new CAssignment(var_h,new CNumber(1)));

        CEvent Fail =  new CNonGuardedEvent("Fail", new CNDChoice(new CGuarded(new CEquals(var_Bat1,num_1),new CAssignment(var_Bat1,num_2)),new CGuarded(new CEquals(var_Bat3,num_1),new CAssignment(var_Bat3,num_2)),new CGuarded(new CEquals(var_Bat3,num_1),new CAssignment(var_Bat3,num_2))));

        CSubstitution c1 = new CGuarded(new CAnd(new CEquals(var_Bat1,num_1), new CNot(new CEquals(var_BatInUse,num_1))) ,new CMultipleAssignment(new CAssignment(var_BatInUse,num_1),new CAssignment(var_h,num_2)));
        CSubstitution c2 = new CGuarded(new CAnd(new CEquals(var_Bat2,num_1), new CNot(new CEquals(var_BatInUse,num_2))) ,new CMultipleAssignment(new CAssignment(var_BatInUse,num_2),new CAssignment(var_h,num_2)));
        CSubstitution c3 = new CGuarded(new CAnd(new CEquals(var_Bat3,num_1), new CNot(new CEquals(var_BatInUse,num_3))) ,new CMultipleAssignment(new CAssignment(var_BatInUse,num_3),new CAssignment(var_h,num_2)));
        CEvent Commute =  new CGuardedEvent("Commute",new CEquals(var_h,num_1), new CNDChoice(c1,c2,c3));

        CEvent Rep =  new CNonGuardedEvent("Repair", new CNDChoice(new CGuarded(new CEquals(var_Bat1,num_2),new CAssignment(var_Bat1,num_1)),new CGuarded(new CEquals(var_Bat3,num_2),new CAssignment(var_Bat3,num_1)),new CGuarded(new CEquals(var_Bat3,num_2),new CAssignment(var_Bat3,num_1))));



        cMachine.setEvents(Tic,Fail,Commute,Rep);

        /*CAbstraction cAbstraction = new CPredicateTriModalsAbstraction(cMachine,new CEquals(var_h,new CNumber(1)),new COr(new CEquals(var_Bat1,new CNumber(1)),new CEquals(var_Bat2,new CNumber(1)),new CEquals(var_Bat3,new CNumber(1))));
        cMachine.setAbstraction(cAbstraction); */

      //  System.out.println(cMachine.toOutputString());
        return cMachine;
    }
    public static CMachine getFermatTestMachine()
    {
        CMachine cMachine = new CMachine("FermatTest3");

        CVariable var_test = new CVariable("test"); /* 1 == lib , 2 == occ */

        cMachine.setVariables(var_test);

        CBooleanExpression Invariant = new CAnd(
                new CInDomain(var_test, CSet.NATURAL),
                new CEquals(var_test,new CNumber(0)),
                new CGreater(new CNumber(3),var_test)
        );
        cMachine.setInvariant(Invariant);

        cMachine.setInit(new CAssignment(var_test,new CNumber(1)));

        CVariable var_a = new CVariable("a");
        CVariable var_b = new CVariable("b");
        CVariable var_c = new CVariable("c");
        CEvent TryTest = new CAnyEvent("TryTest",new CAnd(new CGreater(var_a,new CNumber(0)),new CGreater(var_b,new CNumber(0)),new CGreater(var_c,new CNumber(0))),new CGuarded(new CNot(new CEquals(new CMult(var_a,var_a,var_a), new CPlus(new CMult(var_b,var_b,var_b),new CMult(var_c,var_c,var_c)))),new CAssignment(var_test, new CNumber(2))),var_a,var_b,var_c);

        cMachine.setEvents(TryTest);


        /*CAbstraction cAbstraction = new CPredicateTriModalsAbstraction(cMachine,new CEquals(var_test,new CNumber(1)));
        cMachine.setAbstraction(cAbstraction);  */

        //System.out.println(cMachine.toOutputString());

        return cMachine;
    }

    public static CMachine getCofeeMachine()
    {
        CMachine cMachine = new CMachine("CofeeMachine");

        CVariable var_Mago = new CVariable("Mago");
        CVariable var_Balance = new CVariable("Balance");
        CVariable var_CofeeLeft = new CVariable("CofeeLeft");
        CVariable var_Statut = new CVariable("Statut"); /* 0 == OFF , 1 == ON, 2 = OUT_OF_ORDER */

        cMachine.setVariables(var_Mago, var_Balance, var_CofeeLeft, var_Statut);

        CBooleanExpression Invariant = new CAnd(
                new CInDomain(var_Mago, CSet.NATURAL),
                new CInDomain(var_Balance, CSet.NATURAL),
                new CInDomain(var_CofeeLeft, CSet.NATURAL),
                new CInDomain(var_Statut, CSet.NATURAL),
                new CGreater(new CNumber(3),var_Statut)
        );
        cMachine.setInvariant(Invariant);



        CSubstitution subInit = new CMultipleAssignment(new CAssignment(var_Statut,new CNumber(0)),new CAssignment(var_Mago,new CNumber(0)),new CAssignment(var_Balance,new CNumber(0)),new CAssignment(var_CofeeLeft,new CNumber(100)));
        cMachine.setInit(subInit);

        CEvent insert100 = new CGuardedEvent("insert100",new CEquals(var_Statut,new CNumber(1)),new CAssignment(var_Balance,new CPlus(var_Balance,new CNumber(100))));
        CEvent insert200 = new CGuardedEvent("insert200",new CEquals(var_Statut,new CNumber(1)),new CAssignment(var_Balance,new CPlus(var_Balance,new CNumber(200))));
        CEvent insert10 = new CGuardedEvent("insert10",new CEquals(var_Statut,new CNumber(1)),new CAssignment(var_Balance,new CPlus(var_Balance,new CNumber(10))));
        CEvent insert20 = new CGuardedEvent("insert20",new CEquals(var_Statut,new CNumber(1)),new CAssignment(var_Balance,new CPlus(var_Balance,new CNumber(20))));
        CEvent insert50 = new CGuardedEvent("insert50",new CEquals(var_Statut,new CNumber(1)),new CAssignment(var_Balance,new CPlus(var_Balance,new CNumber(50))));

        CEvent powerUp = new CGuardedEvent("powerUp",new CEquals(var_Statut,new CNumber(0)),new CAssignment(var_Statut,new CNumber(1)));
        CEvent powerDown = new CGuardedEvent("powerDown",new CNot(new CEquals(var_Statut,new CNumber(0))),new CAssignment(var_Statut,new CNumber(0)));

        CEvent AutoOut = new CGuardedEvent("AutoOut",new CAnd(new COr(new CEquals(var_CofeeLeft,new CNumber(0)),new CEquals(var_Mago,new CNumber(6120))),new CEquals(var_Statut,new CNumber(1))),new CMultipleAssignment(new CAssignment(var_Statut,new CNumber(2)),new CAssignment(var_Balance,new CNumber(0))));

        CEvent takeMago = new CGuardedEvent("takeMago",new CEquals(var_Statut,new CNumber(0)),new CAssignment(var_Mago,new CNumber(0)));

        CVariable var_x = new CVariable("x");
        CEvent addCofee = new CAnyEvent("addCofee",new CGreater(var_x,new CNumber(0)),new CGuarded(new CEquals(var_Statut,new CNumber(0)),new CAssignment(var_CofeeLeft,new CPlus(var_CofeeLeft,var_x))),var_x);

        CEvent serveCofee = new CGuardedEvent("serveCofee",new CAnd(new CEquals(var_Statut,new CNumber(1)),new COr(new CGreater(var_Balance,new CNumber(60)),new CEquals(var_Balance,new CNumber(60)))),new CMultipleAssignment(new CAssignment(var_CofeeLeft,new CMinus(var_CofeeLeft,new CNumber(1))),new CAssignment(var_Mago,new CPlus(var_Mago,new CNumber(60)))));

        CEvent backBalance = new CGuardedEvent("backBalance",new CAnd(new CEquals(var_Statut,new CNumber(1)),new CGreater(var_Balance,new CNumber(0))),new CAssignment(var_Balance,new CNumber(0)));


        cMachine.setEvents(insert100,insert200,insert10,insert20,insert50,powerUp,powerDown,AutoOut,takeMago,addCofee,serveCofee,backBalance);

        CBooleanExpression Pinit  = new CAnd(new CEquals(var_Statut,new CNumber(0)),new CEquals(var_Mago,new CNumber(0)),new CEquals(var_Balance,new CNumber(0)),new CEquals(var_CofeeLeft,new CNumber(100)));

        CBooleanExpression P  = new COr(new CGreater(var_Balance,new CNumber(60)),new CEquals(var_Balance,new CNumber(60)));
        CBooleanExpression Q  = new CGreater(var_Balance,new CNumber(0));

        /*CAbstraction cAbstraction = new CPred3ModExtended(cMachine,new CGreater(var_Balance,new CNumber(0)),new CEquals(var_Statut,new CNumber(1)));
        cMachine.setAbstraction(cAbstraction);   */

        //System.out.println(cMachine.toOutputString());

        return cMachine;
    }
}
